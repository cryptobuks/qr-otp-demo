package com.qrotp;

import android.util.Base64;
import android.util.Log;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CryptoUtil {

    private  static final int numBits=1024;
    private  static final String  keyAlgorithm="RSA";

    public static   KeyPairStore    generateKeyPair(String email) throws Exception{



        KeyPairStore keyPairStore=new   KeyPairStore();

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
        keyGen.initialize(numBits);
        KeyPair keyPair = keyGen.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        byte[] privateKeyBytes = privateKey.getEncoded();
        byte[] publicKeyBytes = publicKey.getEncoded();

        // Get the formats of the encoded bytes
        String formatPrivate = privateKey.getFormat(); // PKCS#8
        String formatPublic = publicKey.getFormat(); // X.509

        keyPairStore.setPrivateKey(Base64.encodeToString(privateKeyBytes,Base64.DEFAULT));
        keyPairStore.setPublicKey(Base64.encodeToString(publicKeyBytes,Base64.DEFAULT));
        keyPairStore.setPublicKeyBytes(publicKeyBytes);

        Log.d("photootp",keyPairStore.getPublicKey());

        return  keyPairStore;
    }

    public byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }


    public static   String  decryptText(String encryptedText,String privateKeyText) throws Exception{

        byte[] encryptedTextBytes=Base64.decode(encryptedText,Base64.DEFAULT);


        byte[] privateKeyBytes=Base64.decode(privateKeyText,Base64.DEFAULT);
        // The bytes can be converted back to public and private key objects
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        byte[] decryptedData=decrypt(privateKey,encryptedTextBytes);
        return  new String(decryptedData);
    }

}
