function checkWrite() {
   var frm = document.form1;
   
   
   
   if(!frm.feed_content.value.trim()) {
      alert("내용을 입력하세요.");
      frm.feed_content.focus();
      return false;
   }
   
   if (!frm.outdoor[0].checked && !frm.outdoor[1].checked && !frm.outdoor[2].checked && !frm.outdoor[3].checked) {
   		alert("아웃도어을 선택해 주세요.");
   		return false;
   	}
   
	if (!frm.feed_subject[0].checked && !frm.feed_subject[1].checked && !frm.feed_subject[2].checked) {
	   		alert("글 종류를 선택해 주세요.");
	   		return false;
	}
	
   frm.submit();
}

function checkinput() {
   var frm = document.form1;
   
   
   
   if(!frm.feed_content.value.trim()) {
      alert("내용을 입력하세요.");
      frm.feed_content.focus();
      return false;
   }
   
   if (!frm.outdoor[0].checked && !frm.outdoor[1].checked && !frm.outdoor[2].checked && !frm.outdoor[3].checked) {
   		alert("아웃도어을 선택해 주세요.");
   		return false;
   	}
   
	if (!frm.feed_subject[0].checked && !frm.feed_subject[1].checked && !frm.feed_subject[2].checked) {
	   		alert("글 종류를 선택해 주세요.");
	   		return false;
	}
	
   frm.submit();
}