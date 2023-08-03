package com.example.finalexamplespring.vodBoard.repository;

import com.example.finalexamplespring.vodBoard.entity.VodBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VodBoardFileRepository extends JpaRepository<VodBoardFile, Integer> {
}
