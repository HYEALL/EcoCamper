// 문자열이 영어, 숫자인지 확인하는 메서드 
function checkEngNumber(value) {
	var count = 0;

	for (var i = 0; i < value.length; i++) {
		if ((value.charCodeAt(i) >= 65 && value.charCodeAt(i) <= 90) || (value.charCodeAt(i) >= 97 && value.charCodeAt(i) <= 122) || (value.charCodeAt(i) >= 48 && value.charCodeAt(i) <= 57)) {
			count += 1;
		}
	}

	//카운트 수와 문자의 길이가 같다면 true
	if (count === (value.length)) {
		return true;
	} else {
		return false;
	}
}

//영어, 한글인지(가~힣) 확인
function checkKorEng(value) {
	var count = 0;

	for (var i = 0; i < value.length; i++) {
		console.log(value.charCodeAt(i));
		if ((value.charCodeAt(i) >= 65 && value.charCodeAt(i) <= 90) || (value.charCodeAt(i) >= 97 && value.charCodeAt(i) <= 122) || (value.charCodeAt(i) >= 44032 && value.charCodeAt(i) <= 55203)) {
			count += 1;
		}
	}

	if (count === (value.length)) {
		return true;
	} else {
		return false;
	}
}

function inputCheck() {
	var frm = document.inputForm;
	if (!frm.name.value) {
		alert("이름을 입력하세요.");
		frm.name.focus();
		return false;
	}
	if (frm.id.value.length < 4) {
		alert("아이디를 4글자 이상 입력하세요.");
		frm.id.focus();
		return false;
	}
	if (!checkEngNumber(frm.id.value)) {
		alert("아이디를 영문 대소문자, 숫자만 입력하세요.");
		frm.id.focus();
		return false;
	}
	if (frm.checkIdOk.value != "true") {
		alert("아이디 중복체크를 진행해주세요.");
		frm.id.focus();
		return false;
	}
	if (!frm.pwd.value) {
		alert("비밀번호를 입력하세요");
		frm.pwd.focus();
		return false;
	}
	if (!frm.repwd.value) {
		alert("재확인을 입력하세요");
		frm.repwd.focus();
		return false;
	}
	if (frm.pwd.value != frm.repwd.value) {
		alert("비밀번호가 틀림니다.");
		frm.repwd.value = "";
		frm.repwd.focus();
		return false;
	}
	if (!frm.birthYear.value || !frm.birthMonth.value || !frm.birthDay.value) {
		alert("생년월일을 입력하세요");
		frm.birthYear.focus();
		return false;
	}
	if (!frm.gender.value) {
		alert("성별을 입력하세요");
		frm.gender.focus();
		return false;
	}
	if (!frm.email.value) {
		alert("이메일을 입력하세요");
		frm.email.focus();
		return false;
	}
	var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

	if (!emailPattern.test(frm.email.value)) {
		alert("유효한 이메일 주소를 입력하세요");
		frm.email.focus();
		return false;
	}
	if (frm.checkEmailOk.value != "true") {
		alert("이메일 인증을 진행해주세요.");
		frm.email.focus();
		return false;
	}
	if (!frm.tel.value) {
		alert("핸드폰 번호를 입력하세요");
		frm.tel.focus();
		return false;
	}
	var phonePattern = /^010-\d{4}-\d{4}$/;

	// 폰번호 검사
	if (!phonePattern.test(frm.tel.value)) {
		alert("올바른 폰번호 형식 (010-0000-0000)을 입력하세요.");
		frm.phone.focus();
		return false;
	}
	if (!frm.addr.value) {
		alert("주소를 입력하세요");
		frm.addr.focus();
		return false;
	}
	frm.submit();
}

function login() {
	var frm = document.form1;

	if (!frm.id.value) {
		alert("아이디를 입력해주세요");
		frm.id.value = "";
		frm.id.focus();
		return false;
	}
	if (!frm.pwd.value) {
		alert("비밀번호를 입력해주세요");
		frm.pwd.value = "";
		frm.pwd.focus();
		return false;
	}

	const userDTO = {
		id: frm.id.value.trim(),  // 공백 제거 및 문자열 검증
		pwd: frm.pwd.value.trim()  // 공백 제거 및 문자열 검증
	}
	fetch('/login', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(userDTO)
	})
		.then(response => response.text())
		.then(data => {
			if (data === "Login Success") {
				window.location.href = '/index'; // index로 이동
			} else {
				alert('회원 정보가 틀렸습니다. 다시 입력하세요');
			}
		})
		.catch(error => console.error('Error:', error));
}

function checkId() {
	var sId = document.inputForm.id.value;
	if (sId == "") {
		alert("먼저 아이디를 입력하세요.");
		document.joinForm.id.focus();
		return false;
	} else if (sId.length < 4) {
		alert("아이디를 4글자 이상 입력하세요.");
		frm.id.focus();
		return false;
	} else if (!checkEngNumber(sId)) {
		alert("아이디를 영문 대소문자, 숫자만 입력하세요.");
		frm.id.focus();
		return false;
	}
	else {
		window.open("/user/checkId?id=" + sId, "", "width=450 height=200")
	}
}
function checkIdClose(id) {
	opener.inputForm.id.value = id;
	opener.inputForm.checkIdOk.value = "true";
	opener.inputForm.id.readOnly = true;
	window.close();
	opener.inputForm.pwd.focus();
}

function incheckId() { // checkId form 입력 체크
	var sId = document.writeForm.id.value.trim();
	if (sId == "") {
		alert("먼저 아이디를 입력하세요.");
		document.writeForm.id.focus();
		return false;
	} else if (sId.length < 4) {
		alert("아이디를 4글자 이상 입력하세요.");
		frm.id.focus();
		return false;
	} else if (!checkEngNumber(sId)) {
		alert("아이디를 영문 대소문자, 숫자만 입력하세요.");
		frm.id.focus();
		return false;
	}
	document.writeForm.submit();
}
function sendNumber() { // 인증메일 발송 클릭시
	var frm = document.inputForm;
	if (frm.checkEmailOk.value == "true") {
		alert("이미 이메일 인증을 완료했습니다.");
		frm.email.focus();
		return false;
	}
	if (!frm.email.value) {
		alert("이메일을 먼저 입력하세요");
		frm.email.focus();
		return false;
	}
	var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

	if (!emailPattern.test(frm.email.value)) {
		alert("유효한 이메일 주소를 입력하세요");
		frm.email.focus();
		return false;
	}

	$("#mail_number").css("display", "");
	$.ajax({
		url: "/mail",
		type: "post",
		dataType: "text",
		data: {
			"mail": $("#email").val() // email 값 param으로 전달
		},
		success: function(response) {
			alert(response);
		},
	});
}
function confirmNumber() { // 인증하기 클릭시
	var frm = document.inputForm;
	var validNumber = $("#valid_number").val();
	if (frm.checkEmailOk.value == "true") {
		alert("이미 이메일 인증을 완료했습니다.");
		frm.email.focus();
		return false;
	}
	$.ajax({
		url: "/confirm",
		type: "post",
		dataType: "text",
		data: {
			"valid_number": validNumber // 입력한 인증번호 param으로 전달
		},
		success: function(response) {
			alert(response);
			if (response === "이메일 인증이 완료되었습니다.") {
				frm.checkEmailOk.value = "true";
				frm.email.readOnly = true;
			}
		},
	});
}


