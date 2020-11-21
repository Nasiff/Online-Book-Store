function validate() {
	var ok = true;
	var namePrefix = document.getElementById("namePrefix").value;
	var creditsTaken = document.getElementById("creditsTaken").value;

	if (namePrefix.length == 0) {
		ok = false;
		var nameAsterisk = document.getElementById('name-error');
		nameAsterisk.style.display = "inline";
		nameAsterisk.style.color = "red";
		alert("Name Prefix field can't be empty!");	
	} 
	
	if (creditsTaken.length == 0) {
		ok = false;
		var creditsTakenAsterisk = document.getElementById('credits-taken-error');
		creditsTakenAsterisk.style.display = "inline";
		creditsTakenAsterisk.style.color = "red";
		alert("Credits Taken field can't be empty!");	
	} else if (isNaN(creditsTaken)) {
		ok = false;
		var creditsTakenAsterisk = document.getElementById('credits-taken-error');
		creditsTakenAsterisk.style.display = "inline";
		creditsTakenAsterisk.style.color = "red";
		alert("Credits Taken field should be a number!");	
	}

	return ok;
}

function doSimpleAjax(url) {
	var request = new XMLHttpRequest();
	var data='';

	data += "namePrefix=" + document.getElementById("namePrefix").value + "&";
	data += "creditsTaken=" + document.getElementById("creditsTaken").value + "&";
	data += "generateJSON=true" + "&";

	request.open("GET", (url + "?" + data), true);
	request.onreadystatechange = function() {
		handler(request);
	};
	 
	request.send(null);
}


function handler(xhr){
	  if ((xhr.readyState == 4) && (xhr.status == 200)){
		  var target = document.querySelector("#ajaxTarget");
		  target.innerHTML = "<p>" + xhr.responseText + "</p>";
		  }
}
