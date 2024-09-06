package com.example.EcoCamper.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.EcoCamper.dto.FeedDTO;
import com.example.EcoCamper.entity.Feed;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.FeedService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class FeedController {
	@Autowired
	FeedService service;

	@Value("${project.upload.path}")
	private String uploadpath;
	
	@Autowired
	private TokenProvider tokenProvider;

	@GetMapping("feed/feedWriteFormPhoto")
	public String feedWriteFormPhoto(HttpServletRequest request, FeedDTO dto, Model model) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		String userId = tokenProvider.validateAndGetUserId(token);
		dto.setId(userId);
		model.addAttribute("dto", dto);
		return "feed/feedWriteFormPhoto";
	}

	@GetMapping("feed/feedWriteFormVideo")
	public String feedWriteFormVideo(HttpServletRequest request, FeedDTO dto, Model model) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		String userId = tokenProvider.validateAndGetUserId(token);
		dto.setId(userId);
		model.addAttribute("dto", dto);
		return "feed/feedWriteFormVideo";
	}

	@PostMapping("feed/feedWritePh")
	public String feedWritePh(FeedDTO dto, Model model, HttpServletRequest request,
	        @RequestParam("feed_file1[]") List<MultipartFile> uploadFiles) {
	    File uploadDir = new File(uploadpath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs();
	    }
	    
	    // 파일 이름 저장을 위한 리스트 초기화
	    List<String> fileNames = new ArrayList<>();

	    // 이미지 파일 처리
	    for (MultipartFile uploadFile : uploadFiles) {
	        if (!uploadFile.isEmpty()) {
	            String fileName = uploadFile.getOriginalFilename();
	            File file = new File(uploadpath, fileName);

	            // 중복된 파일 확인: 동일한 이름과 크기를 가진 파일이 이미 존재하는지 체크
	            boolean check = file.exists() && file.length() == uploadFile.getSize();
	            
	            if (!check) {
	                try {
	                    uploadFile.transferTo(file); // 파일을 지정된 경로로 저장
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    model.addAttribute("result", false); // 실패 시 결과를 모델에 추가
	                    return "feed/feedWritePh";
	                }
	            } else {
	                // 중복된 파일 처리 (선택 사항): 이미 존재하는 경우 처리 로직 추가
	                System.out.println("already: " + fileName);
	            }
	            
	            fileNames.add(fileName); // 저장된 파일 이름을 리스트에 추가
	        }
	    }

	    String token = tokenProvider.resolveTokenFromCookie(request);
	    String userId = tokenProvider.validateAndGetUserId(token);
	    dto.setId(userId);
	    dto.setFeed_file(String.join(",", fileNames)); // 파일 이름들을 콤마로 구분하여 설정
	    dto.setLogtime(new Date());
	    dto.setFeed_type("img");

	    boolean result = service.feedWritePhoto(dto);
	    model.addAttribute("result", result);

	    return "feed/feedWritePh";
	}
	
	@PostMapping("feed/feedWriteVoD")
	public String feedWriteVoD(FeedDTO dto, Model model, HttpServletRequest request,
	        @RequestParam("feed_file1") MultipartFile uploadFile) {
	    // 데이터 처리
	    System.out.println("dto = " + dto);

	    // 파일 이름 처리
	    String fileName = uploadFile.getOriginalFilename();
	    File file = new File(uploadpath, fileName);
	    dto.setFeed_file(fileName); // 파일 이름을 DTO의 video 필드에 설정
	    dto.setLogtime(new Date());
	    dto.setFeed_type("video");

	    // 중복된 파일 확인: 동일한 이름과 크기를 가진 파일이 이미 존재하는지 체크
	    boolean check = file.exists() && file.length() == uploadFile.getSize();

	    if (!check) {
	        if (!fileName.isEmpty()) {
	            // 파일 저장
	            try {
	                uploadFile.transferTo(file);
	            } catch (IllegalStateException | IOException e) {
	                e.printStackTrace();
	                model.addAttribute("result", false); // 실패 시 결과를 모델에 추가
	                return "feed/feedWriteVoD";
	            }
	        }
	    } else {
	        // 중복된 파일 처리 (선택 사항): 이미 존재하는 경우 처리 로직 추가
	        System.out.println("already: " + fileName);
	    }

	    // 세션에서 사용자 ID 가져오기
	    String token = tokenProvider.resolveTokenFromCookie(request);
		String userId = tokenProvider.validateAndGetUserId(token);
		dto.setId(userId);

	    // DB 저장
	    boolean result = service.feedWriteVideo(dto);

	    // 데이터 공유
	    model.addAttribute("result", result);

	    // 뷰 파일 선택
	    return "feed/feedWriteVoD";
	}
	
	@GetMapping("feed/feedList")
	   public String feedList(Model model, HttpServletRequest request) {
		
	      String token = tokenProvider.resolveTokenFromCookie(request);
	      String userId = tokenProvider.validateAndGetUserId(token);
	      List<FeedDTO> list =service.getAllFeeds();
	      
	      System.out.println("list = "+list);
	      model.addAttribute("list", list);
	      model.addAttribute("userId", userId);
	      return "feed/feedList";
	   }
	
	@GetMapping("feed/feedReply")
	public String messageWriteForm(Model model, HttpServletRequest request) {
		String token = tokenProvider.resolveTokenFromCookie(request);
	    String userId = tokenProvider.validateAndGetUserId(token);
	     
	    int seq = Integer.parseInt(request.getParameter("seq"));
	    
	    model.addAttribute("userId", userId);
	    model.addAttribute("seq", seq);
		return "feed/feedReply";
		
	}
	
	@GetMapping("feed/feedView")
	public String feedView(Model model, HttpServletRequest request) {
		 String token = tokenProvider.resolveTokenFromCookie(request);
	     String userId = tokenProvider.validateAndGetUserId(token);
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		Feed feed = service.feedView(seq);
		System.out.println("feed =" + feed);
		model.addAttribute("userId", userId);
		model.addAttribute("feed", feed);
	    model.addAttribute("seq", seq);
	
		return "feed/feedView";
	}
	
	@GetMapping("feed/feedDelete")
	public String feedDelete(Model model, HttpServletRequest request) {
		String token = tokenProvider.resolveTokenFromCookie(request);
	    String userId = tokenProvider.validateAndGetUserId(token);
		int seq = Integer.parseInt(request.getParameter("seq"));
		boolean result = service.feedDelete(seq);
		
		model.addAttribute("result", result);
		model.addAttribute("userId", userId);
		
		return "feed/feedDelete";
	}
	
	
}
