package com.example.finalexamplespring.vodBoard.service;

import com.example.finalexamplespring.vodBoard.entity.VodBoard;
import com.example.finalexamplespring.vodBoard.entity.VodBoardFile;
import com.example.finalexamplespring.vodBoard.repository.VodBoardFileRepository;
import com.example.finalexamplespring.vodBoard.repository.VodBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VodBoardService {
    private final VodBoardRepository vodBoardRepository;
    private final VodBoardFileRepository vodBoardFileRepository;

    public List<VodBoard> getBoardList() {
        return vodBoardRepository.findAll();
    }

    public void insertBoard(VodBoard board, List<VodBoardFile> fileList) {
        vodBoardRepository.save(board);
        vodBoardRepository.flush();

        for (VodBoardFile boardFile : fileList) {
            boardFile.setVodBoard(board);

            int boardFilNo = vodBoardFileRepository.findMaxFileNo(board.getId());
            boardFile.setVodFileNo(boardFilNo);

            vodBoardFileRepository.save(boardFile);
        }
    }
}
