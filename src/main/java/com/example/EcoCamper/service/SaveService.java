package com.example.EcoCamper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.SaveDAO;
import com.example.EcoCamper.dto.SaveDTO;


@Service
public class SaveService {

	@Autowired
	SaveDAO dao;
	
	// 좋아요 추가 로직
	public int addSave(SaveDTO dto) {
        return dao.addSave(dto);
    }
    
 // 좋아요 삭제 로직
	 public void deleteBySaveSeqAndSaveId(int save_seq, String save_id) {
        dao.deleteBySaveSeqAndSaveId(save_seq, save_id);
    }

    // 특정 사용자가 피드를 좋아요 했는지 확인하는 로직
	 public boolean isSaveByUser(int save_seq, String save_id) {
        return dao.existsBySaveSeqAndSaveId(save_seq, save_id);
    }
}
