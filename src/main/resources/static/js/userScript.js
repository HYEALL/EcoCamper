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
	if (!frm.zipcode.value) {
		alert("우편번호를 입력하세요");
		frm.zipcode.focus();
		return false;
	}
	if (!frm.addr1.value) {
		alert("주소를 입력하세요");
		frm.addr1.focus();
		return false;
	}
	if (!frm.addr2.value) {
		alert("상세 주소를 입력하세요");
		frm.addr2.focus();
		return false;
	}
	frm.submit();
}
function editCheck() {
	var frm = document.inputForm;
	if (!frm.name.value) {
		alert("이름을 입력하세요.");
		frm.name.focus();
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
	if (!frm.gender.value) {
		alert("성별을 입력하세요");
		frm.gender.focus();
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
	if (!frm.zipcode.value) {
		alert("우편번호를 입력하세요");
		frm.zipcode.focus();
		return false;
	}
	if (!frm.addr1.value) {
		alert("주소를 입력하세요");
		frm.addr1.focus();
		return false;
	}
	if (!frm.addr2.value) {
		alert("상세 주소를 입력하세요");
		frm.addr2.focus();
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
function enterKeyLogin() {
	if (window.event.keyCode == 13) {
		login();
	}
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
function sendNumber() { // 인증 메일 발송 클릭 시
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

	document.querySelector("#mail_number").style.display = "";

	// 서버에 POST 요청을 보내 이메일로 인증번호를 전송
	fetch('/mail', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		body: 'mail=' + encodeURIComponent(frm.email.value) // 이메일 주소를 URL 인코딩하여 전송
	})
		.then(response => response.text()) // 응답을 텍스트 형식으로 처리
		.then(data => {
			alert(data); // 서버 응답 메시지를 알림창으로 표시
		})
		.catch(error => console.error('Error:', error)); // 에러가 발생할 경우 콘솔에 로그 출력
}

function confirmNumber() { // 인증하기 클릭 시
	var frm = document.inputForm;
	var validNumber = document.querySelector("#valid_number").value;
	if (frm.checkEmailOk.value == "true") {
		alert("이미 이메일 인증을 완료했습니다.");
		frm.email.focus();
		return false;
	}
	// 서버에 POST 요청을 보내 인증번호를 확인
	fetch('/confirm', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		body: 'valid_number=' + encodeURIComponent(validNumber)  // 인증번호를 URL 인코딩하여 전송
	})
		.then(response => response.text())
		.then(data => {
			alert(data); // 서버 응답 메시지를 알림창으로 표시
			if (data === "이메일 인증이 완료되었습니다.") {
				frm.checkEmailOk.value = "true";  // 이메일 인증 완료 상태로 설정
				frm.email.readOnly = true;
			}
		})
		.catch(error => console.error('Error:', error));
}



function DaumZipcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				document.getElementById("addr1").value = extraAddr;

			} else {
				document.getElementById("addr2").value = '';
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('zipcode').value = data.zonecode;
			document.getElementById("addr1").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("addr2").focus();
		}
	}).open();
}

