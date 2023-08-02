package com.example.finalexamplespring.vodBoard.repository;

import com.example.finalexamplespring.vodBoard.entity.VodBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VodBoardFileRepository extends JpaRepository<VodBoardFile, Integer> {
    //@Query: repository에 원한 쿼리를 작성하게 해주는 어노테이션
    //value 속성: 쿼리 작성부
    //nativeQuery: JPA에서 지정한 규칙을 모두 무시할 수 있는 속성
    @Query(value="SELECT IFNULL(MAX(F.VOD_FILE_NO), 0) + 1 " +
            "           FROM T_VOD_BOARD_FILE F" +
            "           WHERE F.VOD_NO = :boardNo", nativeQuery = true)
    int findMaxFileNo(@Param("boardNo") int boardNo);
}
