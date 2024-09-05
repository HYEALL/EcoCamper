package com.example.EcoCamper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.EcoCamper.service.MailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping(value = "/mail")
    public String MailSend(@RequestParam(value = "mail") String mail, HttpServletRequest request){ 
    	// 메일 보내기
        int confirm = mailService.sendMail(mail);
        request.getSession().setAttribute("confirm", confirm); // 인증번호 세션에 저장

        return "인증 메일을 전송했습니다.";
    }
    
    @ResponseBody
    @PostMapping(value = "/confirm")
    public String confirmNumber(@RequestParam(value = "valid_number") String valid_number, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionAuthCode = session.getAttribute("confirm").toString();  // 세션에서 인증번호 가져오기

        if (sessionAuthCode.equals(valid_number)) {
            session.removeAttribute("confirm");  // 인증이 완료되면 세션에서 인증번호 삭제
            return "이메일 인증이 완료되었습니다."; // 응답으로 보내기
        } else {
            return "인증번호가 다릅니다."; // 응답으로 보내기
        }
    }


}