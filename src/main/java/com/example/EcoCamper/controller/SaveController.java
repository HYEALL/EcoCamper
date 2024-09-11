package com.example.EcoCamper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.EcoCamper.dto.SaveDTO;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.SaveService;

@Controller
public class SaveController {
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	SaveService service;
	
	@PostMapping("/pulse")
    public ResponseEntity<?> addSave(@RequestBody SaveDTO dto) {
    	System.out.println(dto.toString());
        int save_num = service.addSave(dto);
        return ResponseEntity.ok(save_num);
    }

 @DeleteMapping("/mius")
    public ResponseEntity<?> removeLike(@RequestParam("save_seq") int save_seq, @RequestParam("save_id") String save_id) {
        System.out.println("save_seq = " + save_seq + ", save_id = " + save_id);
        service.deleteBySaveSeqAndSaveId(save_seq, save_id);
        return ResponseEntity.ok("저장이 삭제되었습니다.");
    }
 
 @GetMapping("/isSave/{save_seq}")
 public ResponseEntity<Boolean> isSave(
     @PathVariable("save_seq") int save_seq,
     @RequestParam("save_id") String save_id) {
     boolean isSave = service.isSaveByUser(save_seq, save_id);
     return ResponseEntity.ok(isSave);
 }
}
