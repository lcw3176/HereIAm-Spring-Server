function roomNullCheck() {
	if (form.name.value.trim() == "") {
		alert("방 제목을 입력해 주세요");

		return false;
	}

	else if (form.pw.value.trim() == "") {
		alert("비밀번호를 입력해 주세요");

		return false;
	}

	return true;
}