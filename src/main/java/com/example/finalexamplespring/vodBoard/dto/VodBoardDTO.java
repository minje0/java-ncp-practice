package com.example.finalexamplespring.vodBoard.dto;

import com.example.finalexamplespring.user.dto.UserDTO;
import com.example.finalexamplespring.vodBoard.entity.VodBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VodBoardDTO {
    private Integer id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int hits;
    private String path;

    public VodBoard DTOTOEntity() {
        VodBoard vodBoard = VodBoard.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .regDate(this.regDate)
                .modDate(this.modDate)
                .hits(this.hits)
                .path(this.path)
                .build();
        return vodBoard;
    }
}
