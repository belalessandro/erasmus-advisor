// contains functions used for the validation of forms through the website
// 
// note that functions depend on html elements' ID and, in some cases, 
// on HTML elements' name, so they have to be consistent

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
	
	var name = document.registration.name;
	var surname = document.registration.surname;
	var uni = document.registration.universityNames;
	
	var course = document.registration.corsoNames;
	var startDate = document.registration.datepicker;
	var endDate = document.registration.datepicker2;
	
	if (document.getElementById("typeStudent").checked)
	{	
		if(userid_validation(uid,minUserIDLength,maxUserIDLength))  
		{  
			if(pwd_validation(pwd, pwd2,minPassLength,maxPassLength))  
			{  
				if(email_validation(uemail) && regterm_validation(regterm))  
				{  
					if ((uni.value.length > 0 || course.value.length > 0) 
							|| (startDate.value.length > 0 || endDate.value.length > 0))
					{
						if(string_validation(uni) && string_validation(course))
						{
							if (date_interval_validation(startDate, endDate))
							{
								return true;
							}
						}
					}
					else
					{
						return true;
					}
				}   
			}  
		}  
	}
	else if (document.getElementById("typeManager").checked)
	{
		if(userid_validation(uid,minUserIDLength,maxUserIDLength))  
		{  
			if(pwd_validation(pwd, pwd2,minPassLength,maxPassLength))  
			{  
				if(email_validation(uemail) && regterm_validation(regterm))  
				{  
					if(string_validation(name) && (string_validation(surname) && string_validation(uni)))
					{
						return true;
					}
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

function insertClassFormValidation()
{
	var name = document.insert_class.name;
	var uni = document.insert_class.universityNames;
	var cfu = document.insert_class.credits;
	var year = document.insert_class.year;
	var semester = document.insert_class.semester;
	var lang = document.insert_class.language;
	var area = document.insert_class.area;

	var profName = document.getElementsByName("professorName");
	var profSur = document.getElementsByName("professorSurname");
	
	
	if(string_validation(name))
	{
		if (professor_validation(profName) && professor_validation(profSur))
		{
			if (select_validate(lang) && select_validate(area))
			{
				if (natural_validation(cfu) && string_validation(uni))
				{
					if (semester_validation(semester) && year_validation(year))
					{
						return true;
					}
				}
			}
		}
	}
	return false;
}

function insertThesisFormValidation()
{
	var name = document.insert_thesis.name;
	var uni = document.insert_thesis.universityNames;
	var lang = document.insert_thesis.language;
	var area = document.insert_thesis.area;
	var undergraduate = document.insert_thesis.undergraduate;
	var graduate = document.insert_thesis.graduate;
	
	var profName = document.getElementsByName("professorName");
	var profSur = document.getElementsByName("professorSurname");
	
	if(string_validation(name))
	{
		if (professor_validation(profName) && professor_validation(profSur))
		{
			if (multiselect_validate(lang) && multiselect_validate(area))
			{
				if (checkbox_one_or_both_validation(undergraduate, graduate) && string_validation(uni))
				{
					return true;
				}
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

// mauro: Questo va e potrebbe servire per la validazione della valutazione 
//$(function() {
//	var values = [];
//	impostare <form id="evaluations" ...>
//	$("#evaluations .rating").on('rating.change', function(event, value, caption) {
//	    console.log(value);
//	    console.log(caption);
//	    
//	    
//	});
//	
//	
//	$("#evaluations").submit(function(event) {
////		alert("ciao stronzo!"); 
//		event.preventDefault();
//		
//		ratings = $("#evaluations .rating");
//		
//		
//		
//	});
//	
//	
//	
//	$('#teachingsQuality').on('rating.change', function(event, value, caption) {
//	    console.log(value);
//	    console.log(caption);
//	});
//
//});
	
	


// validate the single elements

//profName è array
function professor_validation(profName)
{
	var flag = true;
	for (var i = 0; i < profName.length; i++) 
	{
		if (string_validation(profName[i]) == false)
		{
			flag = false;
			break;
		}
	}
	if (flag == true)
		return true;
	else
		return false;
}

function date_interval_validation(startDate, endDate)
{
//	try
//	{
//		var fromAr = startDate.value.split("/");
//		var toAr = endDate.value.split("/");
//		var from = new Date();
//		from.setFullYear(fromAr[2], fromAr[0], fromAr[1]);
//		var to = new Date();
//		to.setFullYear(toAr[2], toAr[0], toAr[1]);
//		
//		if (from < to)
//		{
//			return true;
//		}
//		else
//		{
//			alert("You have to insert a valid interval.");  
//			startDate.focus();
//			return false;
//		}
//	}
//	catch(err)
//	{
//		alert("You have to insert a valid interval.");  
//		startDate.focus();
//		return false;
//	}
	
	/*
	 * New version
	 */
	
	try
	{
		var fromAr = startDate.value.split("-");
		var toAr = endDate.value.split("-");
		var from = new Date();
		from.setFullYear(fromAr[0], fromAr[1], fromAr[2]);
		var to = new Date();
		to.setFullYear(toAr[0], toAr[1], toAr[2]);
		console.log("from: " + from);
		console.log("To: " + to);
		
		if (from < to)
		{
			return true;
		}
		else
		{
			alert("You have to insert a valid interval.");  
			startDate.focus();
			return false;
		}
	}
	catch(err)
	{
		alert("You have to insert a valid interval.");  
		startDate.focus();
		return false;
	}
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
	var value = parseInt(year.value) || 0;
	if (value <= 0 || value > 6)
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
	if (s === undefined)
	{
		alert("You have to complete all the required fields.");  
		return false;
	}
	else if (s.value.length == 0)
	{
		alert("You have to complete all the required fields.");  
		s.focus();  
		return false;
	}
	return true;
}

function natural_validation(n)
{
	var value = parseInt(n.value) || 0;
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