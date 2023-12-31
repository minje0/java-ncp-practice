package com.example.finalexamplespring.vodBoard.entity;

import com.example.finalexamplespring.vodBoard.dto.VodBoardFileDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_VOD_BOARD_FILE")
public class VodBoardFile {
    @Id
    @Column(name = "VOD_FILE_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vodFileNo;
    @Column(name = "VOD_ORIGIN_NAME")
    private String vodOriginName;
    @Column(name = "VOD_SAVE_NAME")
    private String vodSaveName;
    @Column(name = "VOD_FILE_OBJECT")
    private String vodFileObject;
    @Column(name = "VOD_NO")
    private int vodBoardNo;
    public VodBoardFileDTO EntityToDTO() {
        VodBoardFileDTO vodBoardFileDTO = VodBoardFileDTO.builder()
                .vodFileNo(this.vodFileNo)
                .vodOriginName(this.vodOriginName)
                .vodSaveName(this.vodSaveName)
                .vodBoardNo(this.vodBoardNo)
                .build();
        return vodBoardFileDTO;
    }
}
