package com.example.finalexamplespring.vodBoard.repository;

import com.example.finalexamplespring.vodBoard.entity.VodBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VodBoardRepository extends JpaRepository<VodBoard, Integer> {
}
