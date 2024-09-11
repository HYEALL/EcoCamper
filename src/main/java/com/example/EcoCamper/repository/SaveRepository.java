package com.example.EcoCamper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.entity.Save;

import jakarta.transaction.Transactional;

public interface SaveRepository extends JpaRepository<Save, Integer> {
	 // 특정 사용자가 피드를 좋아요 했는지 확인하는 쿼리
    @Query("SELECT COUNT(s) > 0 FROM Save s WHERE s.save_seq = :save_seq AND s.save_id = :save_id")
    boolean existsBySaveSeqAndSaveId(@Param("save_seq") int save_seq, @Param("save_id") String save_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Save s WHERE s.save_seq = :save_seq AND s.save_id = :save_id")
    void deleteBySaveSeqAndSaveId(@Param("save_seq") int save_seq, @Param("save_id") String save_id);
}