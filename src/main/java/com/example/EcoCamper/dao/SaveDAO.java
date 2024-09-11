package com.example.EcoCamper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.SaveDTO;
import com.example.EcoCamper.entity.Save;
import com.example.EcoCamper.repository.SaveRepository;

@Repository
public class SaveDAO {
	
	@Autowired
	SaveRepository repository;
	
    public int addSave(SaveDTO dto) {
    	System.out.println("dto : " + dto.toString());
    	Save save = dto.toEntity();
    	repository.save(save);
        return save.getSave_num(); // like num 가져오기
    }
    
    // 좋아요 삭제 메소드 (likes_num 사용)
    public void deleteBySaveSeqAndSaveId(int save_seq, String save_id) {
        repository.deleteBySaveSeqAndSaveId(save_seq, save_id);
    }
	
 // 특정 사용자가 피드를 좋아요 했는지 확인
    public boolean existsBySaveSeqAndSaveId(int save_seq, String save_id) {
        return repository.existsBySaveSeqAndSaveId(save_seq, save_id);
    }

}
