// function used in insert forms

// usato per inserire i prof
function addRow(rowID, controlName1, controlName2) 
{
    var father = document.getElementById(rowID);
    
    var elem1 = document.createElement("input");
    elem1.type = "text";
    elem1.name = controlName1;
    elem1.className ="form-control insert_new_multiple_input";
    elem1.setAttribute("placeholder", "Insert the teacher's name");
    
    var elem2 = document.createElement("input");
    elem2.type = "text";
    elem2.name = controlName2;
    elem2.className ="form-control insert_new_multiple_input";
    elem2.setAttribute("placeholder", "Insert the teacher's surname");

    father.appendChild(elem1);
    father.appendChild(elem2);
}
