function checkAccount() {
	var loginId = document.getElementById('loginId').value;
	var password = document.getElementById('password').value;
	var error = "";
	if (loginId == "") {
		error += "Tai khoan trong<br>";
	}
	if (password == "") {
		error += "Mat khau trong";
	}
	if (error != "") {
		document.getElementById("errorAccount").innerHTML = '';
		document.getElementById("errMsg").innerHTML = error;
		return false;
	} else {
		return true;
	}
}