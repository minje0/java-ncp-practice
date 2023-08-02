package com.example.finalexamplespring.vodBoard.controller;

import com.example.finalexamplespring.dto.ResponseDTO;
import com.example.finalexamplespring.objectStorage.controller.ObjectStorageController;
import com.example.finalexamplespring.objectStorage.service.ObjectStorageService;
import com.example.finalexamplespring.user.entity.CustomUserDetails;
import com.example.finalexamplespring.vodBoard.dto.VodBoardDTO;
import com.example.finalexamplespring.vodBoard.entity.VodBoard;
import com.example.finalexamplespring.vodBoard.entity.VodBoardFile;
import com.example.finalexamplespring.vodBoard.service.VodBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/vod")
@RequiredArgsConstructor
public class VodBoardRestController {
    private final VodBoardService vodBoardService;
    private final ObjectStorageService objectStorageService;


    @GetMapping("/boardList")
    public ResponseEntity<?> getBoardList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<VodBoardDTO> responseDTO = new ResponseDTO<>();
        try {
            List<VodBoard> boardList = vodBoardService.getBoardList();

            List<VodBoardDTO> boardDTOList = new ArrayList<>();

            for (VodBoard board : boardList) {
                boardDTOList.add(board.EntityToDTO());
            }

            responseDTO.setItems(boardDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.ok().body(responseDTO);
        }
    }

    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertBoard(VodBoardDTO boardDTO,
                                         MultipartHttpServletRequest fileRequest) {

        System.out.println("되긴하니?");
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        List<VodBoardFile> uploadFileList = new ArrayList<>();

        try {
            VodBoard board = VodBoard.builder()
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .user(boardDTO.getUserDTO().DTOToEntity())
                    .regDate(LocalDateTime.now())
                    .build();

            Iterator<String> iterator = fileRequest.getFileNames();

            while (iterator.hasNext()) {
                List<MultipartFile> fileList = fileRequest.getFiles(iterator.next());

                for (MultipartFile file : fileList) {
                    if (!file.isEmpty()) {
                        VodBoardFile boardFile = new VodBoardFile();
                        String saveName = objectStorageService.uploadFile(file);
                        boardFile.setVodOriginName(file.getOriginalFilename());
                        boardFile.setVodSaveName(saveName);
                        boardFile.setVodBoard(board);
                        uploadFileList.add(boardFile);
                    }
                }
            }

            vodBoardService.insertBoard(board, uploadFileList);

            Map<String, String> returnMap =
                    new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
