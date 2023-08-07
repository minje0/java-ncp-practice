package com.example.finalexamplespring.vodBoard.entity;

import com.example.finalexamplespring.user.entity.User;
import com.example.finalexamplespring.vodBoard.dto.VodBoardDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_VOD_BOARD")
public class VodBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOD_NO")
    private Integer id;

    @Column(name = "VOD_TITLE")
    private String title;

    @Column(name = "VOD_CONTENT")
    private String content;

    @Column(name = "VOD_WRITER")
    private String writer;

    @Column(name = "VOD_REG")
    private LocalDateTime regDate;

    @Column(name = "VOD_MOD")
    private LocalDateTime modDate;

    @Column(name = "VOD_HITS")
    private int hits;

    @Column(name = "VOD_PATH")
    private String path;

    @Column(name = "VOD_THUMB")
    private String thumb;

    public VodBoardDTO EntityToDTO() {
        VodBoardDTO vodBoardDTO = VodBoardDTO.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .regDate(this.regDate)
                .modDate(this.modDate)
                .hits(this.hits)
                .path(this.path)
                .build();
        return vodBoardDTO;
    }
}
