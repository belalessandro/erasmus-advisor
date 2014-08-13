// contains functions used for the validation of forms through the website

function signInFormValidation()  
{  
	var minUserIDLength = 4
	var maxUserIDLength = 50
	var minPassLength = 6
	var maxPassLength = 20
	var uid = document.registration.user;   
	var uemail = document.registration.email;
	var pwd = document.registration.password; 
	var pwd2 = document.registration.password2; 
	var regterm = document.registration.regTermRadioYes;
	
	if(userid_validation(uid,minUserIDLength,maxUserIDLength))  
	{  
		if(pwd_validation(pwd, pwd2,minPassLength,maxPassLength))  
		{  
			if(email_validation(uemail))  
			{  
				if(regterm_validation(regterm))
				{
					return true;
				}
			}   
		}  
	}  
	return false;  
} 

function userProfileFormValidation()
{
	var minUserIDLength = 4
	var maxUserIDLength = 50
	var minPassLength = 6
	var maxPassLength = 20
	var uid = document.registration.user;   
	var uemail = document.registration.email;
	var pwd = document.registration.password; 
	var pwd2 = document.registration.password2; 
	
	if(userid_validation(uid,minUserIDLength,maxUserIDLength))  
	{  
		if(pwd_validation(pwd, pwd2,minPassLength,maxPassLength))  
		{  
			if(email_validation(uemail))  
			{  
				return true;
			}   
		}  
	}  
	return false;  
}

function regterm_validation(regterm)
{
	if (regterm.checked)
	{
		return true;
	}
	alert("You have to accept the registration terms.");  
	regterm.focus();  
	return false;  
}

function userid_validation(uid,mx,my)  
{  
	var uid_len = uid.value.length;  
	var unameformat = /^[A-Za-z][A-Za-z0-9._-]+$/;
	if(uid.value.match(unameformat))  
	{  
		if (uid_len == 0 || uid_len >= my || uid_len < mx)  
		{  
			alert("Username length length should be between "+mx+" to "+my+ ".");  
			uid.focus();  
			return false;  
		}   
	}  
	else  
	{  
		alert("You have entered an invalid user name!");  
		uid.focus();  
		return false;  
	}  
	return true;  
}  

function pwd_validation(pwd,pwd2,mx,my)  
{  
	var pwd_len = pwd.value.length;  
	if (pwd_len == 0 || pwd_len >= my || pwd_len < mx)  
	{  
		alert("Password length should be between "+mx+" to "+my+ ".");  
		pwd.focus();  
		return false;  
	}  
	if (pwd.value === pwd2.value)  
	{  
		return true;  
	}  
	alert("You do not confirm your password correctly.");  
	pwd.focus();  
	return false;  
}  

function email_validation(uemail)  
{  
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;  
	if(uemail.value.match(mailformat))  
	{  
		return true;  
	}  
	else  
	{  
		alert("You have entered an invalid email address!");  
		uemail.focus();  
		return false;  
	}  
} 