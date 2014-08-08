// function used for the validation of the elements in the sign-in page

function formValidation()  
{  
	var minUserIDLength = 4
	var maxUserIDLength = 100
	var minPassLength = 8
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
function userid_validation(uid,mx,my)  
{  
	var uid_len = uid.value.length;  
	if (uid_len == 0 || uid_len >= my || uid_len < mx)  
	{  
		alert("Username length length should be between "+mx+" to "+my+ ".");  
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
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
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