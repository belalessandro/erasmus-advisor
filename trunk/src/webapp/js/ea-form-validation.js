// contains functions used for the validation of forms through the website
// 
// note that functions depend on html elements' name, so they have to be consistent

// function called from the forms
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

function insertFlowFormValidation()
{
	var name = document.insert_flow.name;
	var destination = document.insert_flow.university;
	var origins = document.insert_flow.origin;
	var certificates = document.insert_flow.certificate;
	var seats = document.insert_flow.seats;
	var length = document.insert_flow.length;
	
	if (natural_validation(seats) && natural_validation(length))
	{
		if (string_validation(name) && string_validation(destination))
		{
			if (multiselect_validate(origins) && multiselect_validate(certificates))
			{
				return true;
			}
		}
	}
	return false;	
}

function insertCityFormValidation()
{
	var city = document.insert_city.name;
	var country = document.insert_city.country;
	var languages = document.insert_city.language;
	if (string_validation(city) && string_validation(country))
	{
		if (multiselect_validate(languages))
		{
			return true;
		}
	}
	return false;
}

function insertClassFormValidation()
{
	var name = document.insert_class.name;
	var uni = document.insert_class.university;
	var cfu = document.insert_class.credits;
	var year = document.insert_class.year;
	var semester = document.insert_class.semester;
	var lang = document.insert_class.language;
	var area = document.insert_class.area;
	
	var profName = document.getElementsByName("professorName");
	var profSur = document.getElementsByName("professorSurname");
	
	if (select_validate(lang) && select_validate(area))
	{
		if(string_validation(name) && professor_validation(profName, profSur))
		{
			if (natural_validation(cfu) && string_validation(uni))
			{
				if (semester_validation(semester) && year_validation(year))
				{
				}
			}
		}
	}
	return false;
}

function insertCourseFormValidation()
{
	var name = document.insert_course.name;
	var uni = document.insert_course.university;
	var level = document.insert_course.level;
	var area = document.insert_course.area;
	
	if (select_validate(level) && multiselect_validate(area))
	{
		if(string_validation(name) && string_validation(uni))
		{
			return true;
		}
	}
	return false;
}

function insertThesisFormValidation()
{
	var name = document.insert_thesis.name;
	var uni = document.insert_thesis.university;
	var lang = document.insert_thesis.language;
	var area = document.insert_thesis.area;
	var undergraduate = document.insert_thesis.undergraduate;
	var graduate = document.insert_thesis.graduate;
	
	var profName = document.getElementsByName("professorName");
	var profSur = document.getElementsByName("professorSurname");
	
	if (multiselect_validate(lang) && multiselect_validate(area))
	{
		if(string_validation(name) && professor_validation(profName, profSur))
		{
			if (checkbox_one_or_both_validation(undergraduate, graduate) && string_validation(uni))
			{
				return true;
			}
		}
	}
	return false;
}

function insertUniversityFormValidation()
{
	var name = document.insert_university.name;
	var country = document.insert_university.country;
	var city = document.insert_university.city;
	var link = document.insert_university.link;
	var ranking = document.insert_university.ranking;

	if (string_validation(name) && string_validation(country))
	{
		if(string_validation(city) && string_validation(link))
		{
			if (natural_validation(ranking))
			{
				return true;
			}
		}
	}
	return false;
}

// non va e non serve
/*function xEvaluationFormValidation()
{
	var rat1 = parseInt(document.evalutationForm.rating1.value);
	var rat2 = parseInt(document.evalutationForm.rating2.value);
	var rat3 = parseInt(document.evalutationForm.rating3.value);
	var rat4 = parseInt(document.evalutationForm.rating4.value);
	
	return true;
}*/

// mauro: Questo va e potrebbe servire per la validazione della valutazione 
/*$(document).ready(function() {
	$("#cityEvaluationForm").submit(function(event) {
		  alert( "" );
		  event.preventDefault();
		});

});*/


// validate the single elements

//profName e profSur sono array
function professor_validation(profName, profSur)
{
	var flag = true;
	for (var i = 0; i < profName.length; i++) 
	{
		if (string_validation(profName[i]) == false)
		{
			flag = true;
			break;
		}
	}
	for (var j = 0; j < profSur.length; j++)
	{
		if (string_validation(profSur[j]) == false)
		{
			flag = true;
			break;
		}
	}
	if (flag == false)
		return true;
	else
		return false;
}

function checkbox_one_or_both_validation(opt1, opt2)
{
	if(!(opt1.checked || opt2.checked))
	{
		alert("You have to selected at least an option.");  
		opt1.focus();
	    return false;  
	}
	return true;
}

function semester_validation(sem)
{
	var value = parseInt(sem.value);
	if (value == 1 || value == 2)
	{
		return true;
	}
	alert("Semester accept only 1 or 2 as value.");  
	sem.focus();
    return false;  
}

function year_validation(year)
{
	var value = parseInt(year.value);
	if (value <0 || value > 6)
	{
		alert("Year accept only values in the range from 1 to 6.");  
		year.focus();
	    return false;  
	}
	return true;
}

function select_validate(select)
{
	if (select.selectedIndex == 0)
	{
		alert("You have to select an option.");  
		select.focus();
		return false;  
	}
	return true;
}

function multiselect_validate(select)
{  
    for(var i = 0; i < select.options.length; i++) 
    {  
        if(select.options[i].selected) 
        {  
        	return true; 
        }  
    }  
	alert("You have to select at least an option.");  
	select.focus();
    return false;  
}  

function string_validation(s)
{
	var s_len = s.value.length
	if (s_len == 0)
	{
		alert("You have to complete all the required fields.");  
		s.focus();  
		return false;
	}
	return true;
}

function natural_validation(n)
{
	var value = parseInt(n.value);
	var n_length = n.length;
	if (n_length == 0 || value <=0)
	{
		alert("You have to insert a positive number.");  
		n.focus();  
		return false;
	}
	return true;
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