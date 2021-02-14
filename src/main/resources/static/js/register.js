function registerNullCheck() {

	console.log(form);
	
	if (form.name.value.trim() == "") {
		alert("이름을 입력해 주세요");

		return false;
	}

	else if (form.email.value.trim() == "") {
		alert("이메일을 입력해 주세요");

		return false;
	}

	else if (form.password.value.trim() == "") {
		alert("비밀번호를 입력해 주세요");

		return false;
	}


	else if (form.repeatpw.value.trim() == "") {
		alert("비밀번호 확인란을 입력해 주세요");

		return false;
	}

	return true;
}