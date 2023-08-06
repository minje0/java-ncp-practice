package com.example.finalexamplespring.vodBoard.repository;

import com.example.finalexamplespring.vodBoard.entity.VodBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VodBoardFileRepository extends JpaRepository<VodBoardFile, Integer> {
    List<VodBoardFile> findAllByVodBoardNo(int boardNo);
}
