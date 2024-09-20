package com.example.EcoCamper.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.dto.FeedDTO;
import com.example.EcoCamper.entity.Feed;

public interface FeedRepository extends JpaRepository<Feed, Integer> {
   
   // 작성순서
   List<Feed> findAllByOrderByLogtimeDesc();

   // 아이디별 작성순서
   List<Feed> findByIdOrderByLogtimeDesc(String id);

   // 좋아요한 피드찾지
   @Query("SELECT f FROM Feed f JOIN Likes l ON f.seq = l.reviewId WHERE l.userId = :userId")
   List<Feed> findFeedsByUserIdAndReviewId(@Param("userId") String id);
   
   // 저장한 피드 찾기
   @Query("SELECT f FROM Feed f JOIN Save l ON f.seq = l.save_seq WHERE l.save_id = :save_id")
   List<Feed> findFeedsBySeqIdAndSaveSeq(@Param("save_id") String id);
   
   // 태그가 포함된 피드를 검색합니다.
    @Query("SELECT f FROM Feed f WHERE f.tags LIKE %:tagName%")
    List<Feed> findByTagName(@Param("tagName") String tagName);

    @Query(value = "SELECT f.seq, f.id, f.outdoor, f.feed_subject, f.feed_content, f.place, f.tags, f.goods, f.feed_file, f.feed_type, f.logtime, COUNT(l.likes_num) AS like_count " +
            "FROM Feed f " +
            "LEFT JOIN Likes l ON f.seq = l.review_id " +
            "GROUP BY f.seq, f.id, f.outdoor, f.feed_subject, f.feed_content, f.place, f.tags, f.goods, f.feed_file, f.feed_type, f.logtime " +
            "ORDER BY like_count DESC " +
            "FETCH FIRST 5 ROWS ONLY", nativeQuery = true)
    List<Feed> findTop5FeedsByLikes();
}
