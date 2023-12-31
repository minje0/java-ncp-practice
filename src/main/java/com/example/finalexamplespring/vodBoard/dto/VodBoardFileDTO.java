package com.example.finalexamplespring.vodBoard.dto;

import com.example.finalexamplespring.vodBoard.entity.VodBoardFile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VodBoardFileDTO {
    private int vodFileNo;
    private String vodOriginName;
    private String vodSaveName;
    private int vodBoardNo;

    public VodBoardFile DTOTOEntity() {
        VodBoardFile vodBoardFile = VodBoardFile.builder()
                .vodFileNo(this.vodFileNo)
                .vodOriginName(this.vodOriginName)
                .vodSaveName(this.vodSaveName)
                .vodBoardNo(this.vodBoardNo)
                .build();
        return vodBoardFile;
    }
}
