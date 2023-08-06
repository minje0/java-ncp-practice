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

    public VodBoard getBoard(int boardNo) {
        if (vodBoardRepository.findById(boardNo).isEmpty()) {
            return null;
        }
        VodBoard returnBoard = vodBoardRepository.findById(boardNo).get();
        returnBoard.setHits(returnBoard.getHits()+1);
        vodBoardRepository.save(returnBoard);
        return returnBoard;
    }

    public List<VodBoardFile> getBoardFileList(int boardNo) {
        return vodBoardFileRepository.findAllByVodBoardNo(boardNo);
    }

    public void insertBoard(VodBoard board, List<VodBoardFile> fileList) {
        vodBoardRepository.save(board);
        vodBoardRepository.flush();

        for (VodBoardFile boardFile : fileList) {
            boardFile.setVodBoardNo(board.getId());

            int boardFilNo = vodBoardFileRepository.save(boardFile).getVodFileNo();
            boardFile.setVodFileNo(boardFilNo);

            vodBoardFileRepository.save(boardFile);
        }
    }
}
