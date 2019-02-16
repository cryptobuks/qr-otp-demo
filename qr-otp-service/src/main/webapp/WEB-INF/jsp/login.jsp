<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>QR OTP Login</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
  </head>

  <body class="text-center">
    <form class="form-signin">
      <h1 class="h3 mb-3 font-weight-normal">Please login in</h1>
      <label for="inputEmail" class="sr-only">Email address</label>
      <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
      
      <div id="otp_div" class="d-none">
      	<img id="code_image" src="" alt="Loading QR Code.."/>
      	<input id="inputOTP" type="text" class="form-control" placeholder="Enter OTP" required>
      </div>
      <button id="continue_btn" class="btn btn-lg btn-primary btn-block" type="button">Continue</button>
      
      <span id="final_output" class="d-none form-control"></span>
      
      <p class="mt-5 mb-3 text-muted">&copy; 2018-2019</p>
    </form>

    <script>
		var g_state="WAITING_FOR_EMAIL";

		$(function(){
			$("#continue_btn").on("click",function(){
				if(g_state=="WAITING_FOR_EMAIL"){
					var email=$("#inputEmail").val();
					$("#code_image").attr("src","generatephotootp/"+encodeURIComponent(email)+"/true");
					$("#otp_div").removeClass("d-none");
					g_state="WAITING_FOR_OTP"
				}
				else{

					var email=$("#inputEmail").val();
					var otp=$("#inputOTP").val();

					$.ajax({
						url:"validateotp",
						data:{email:email, otp:otp},
						method:"POST",
						success:function(data){
							$("#final_output").removeClass("d-none");
							if(data.status=="VALID"){
								$("#final_output").removeClass("text-danger");
								$("#final_output").html("OTP valid");
							}
							else{
								$("#final_output").addClass("text-danger");
								$("#final_output").html("OTP invalid");
							}
						},
						error:function($xhr){
							$("#final_output").addClass("text-danger");
							var unknownErrorMessage="Oops! Something went wrong";
							try{
								var data=$xhr.responseJSON;
								if(data.status=="INVALID"){
									$("#final_output").html("OTP invalid");
								}
								else{
									$("#final_output").html(unknownErrorMessage);
								}
							}
							catch(e){
								$("#final_output").html(unknownErrorMessage);
							}
						}
					});	
				}
			});
		});

    </script>
    
  </body>
</html>

