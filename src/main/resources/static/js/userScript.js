function inputCheck() {
	var frm = document.joinForm;

	if (!frm.name.value.trim()) {
		alert("이름을 입력하세요");
		frm.name.focus();
		return false;
	}
	if (!frm.id.value) {
		alert("아이디를 입력하세요");
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
			//'Authorization': 'Bearer ' + localStorage.getItem('token'),
		},
		body: JSON.stringify(userDTO)
	})
		.then(response => response.json())
		.then(data => {
			console.log(data);
			if (data.token) {
				// 로그인 성공 시 리다이렉션
				//localStorage.setItem('token', data.token);
				window.location.href = '/index'; // index로 이동
			} else {
				alert('Login failed js: ' + data.error);
			}
		})
		.catch(error => console.error('Error:', error));
}

function checkId() {
	var sId = document.joinForm.id.value;
	if (sId == "") {
		alert("먼저 아이디를 입력하세요.");
		document.joinForm.id.focus();
	} else {
		window.open("/user/checkId?id=" + sId, "", "width=450 height=200")
	}
}



function checkModify() {
	var frm = document.form1;

	if (!frm.pwd.value.trim()) {
		alert("비밀번호를 입력해주세요.");
		frm.pwd.value = "";
		frm.pwd.focus();
		return false;
	}
	if (!frm.repwd.value.trim()) {
		alert("재확인 비밀번호를 입력해주세요.");
		frm.repwd.value = "";
		frm.repwd.focus();
		return false;
	}
	if (frm.pwd.value != frm.repwd.value) {
		alert("비밀번호가 틀렸습니다. 다시 입력해주세요.");
		frm.repwd.value = "";
		frm.repwd.focus();
		return false;
	}

	if (!frm.gender[0].checked && !frm.gender[1].checked) {
		alert("성별을 선택해 주세요.");
		return false;
	}

	if (!frm.email1.value.trim()) {
		alert("이메일을 입력해주세요.");
		frm.email1.value = "";
		frm.email1.focus();
		return false;
	}
	if (!frm.email2.value.trim()) {
		alert("이메일을 입력해주세요.");
		frm.email2.value = "";
		frm.email2.focus();
		return false;
	}
	if (!frm.tel1.value.trim()) {
		alert("전화번호를 입력해주세요.");
		frm.tel1.value = "";
		frm.tel1.focus();
		return false;
	}
	if (!frm.tel2.value.trim()) {
		alert("전화번호를 입력해주세요.");
		frm.tel2.value = "";
		frm.tel2.focus();
		return false;
	}
	if (!frm.tel3.value.trim()) {
		alert("전화번호를 입력해주세요.");
		frm.tel3.value = "";
		frm.tel3.focus();
		return false;
	}
	if (!frm.addr.value.trim()) {
		alert("주소를 입력해주세요.");
		frm.addr.value = "";
		frm.addr.focus();
		return false;
	}
	frm.submit();
}






