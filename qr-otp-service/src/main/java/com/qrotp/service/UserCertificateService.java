package com.qrotp.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qrotp.dao.UserCertificateRepository;
import com.qrotp.entities.UserCertificate;


@Service
@Transactional
public class UserCertificateService {

	@Autowired
	private	UserCertificateRepository	userCertificateRepository;
	
	public	void		saveUserCertificate(String emailId,String publicKey) {

		UserCertificate existingRecord=userCertificateRepository.findById(emailId).orElse(null);
		
		if(existingRecord==null) {
			UserCertificate newRecord=new UserCertificate();
			newRecord.setEmail(emailId);
			newRecord.setPublicKey(publicKey);
			userCertificateRepository.save(newRecord);
		}
		else {
			existingRecord.setPublicKey(publicKey);
			userCertificateRepository.save(existingRecord);
		}
	}

	public	String	getPublicKey(String emailId) {
		UserCertificate rec=userCertificateRepository.findById(emailId).orElse(null);
		if(rec!=null) {
			return	rec.getPublicKey();
		}
		else {
			return	null;
		}
	}

	public byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
	    cipher.init(Cipher.ENCRYPT_MODE, key);  
	    return cipher.doFinal(plaintext);
	}

	public byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
	    cipher.init(Cipher.DECRYPT_MODE, key);  
	    return cipher.doFinal(ciphertext);
	}

	public	byte[]	generateQRCodeImage(String emailId,boolean bEncrypt,String text) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, WriterException, IOException {
		byte[] data=null;
		String finalText=text;
		
		if(bEncrypt) {
			String publicKeyText=getPublicKey(emailId);
			if(publicKeyText!=null) {
				
				Decoder decoder=	Base64.getDecoder();
				
				byte[] publicKeyBytes=decoder.decode(publicKeyText);
	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

	            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
	            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
	
	            byte[] encryptedData=encrypt(publicKey,text.getBytes("UTF-8"));
	            
	            Encoder encoder=Base64.getEncoder();
	            String encryptedData_64=encoder.encodeToString(encryptedData);
	            
				finalText=encryptedData_64;
			}
		}

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(finalText, BarcodeFormat.QR_CODE, 250, 250);

        	BufferedImage	bufferedImage=MatrixToImageWriter.toBufferedImage(bitMatrix);
        	
        	ByteArrayOutputStream baos=new	ByteArrayOutputStream();
        	
        	ImageIO.write(bufferedImage, "png", baos);   	
        	data=baos.toByteArray();

		
		return	data;
	}
}
