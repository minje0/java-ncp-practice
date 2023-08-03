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
    private String vodFileObject;
    private VodBoardDTO vodBoardDTO;

    public VodBoardFile DTOTOEntity() {
        VodBoardFile vodBoardFile = VodBoardFile.builder()
                .vodBoard(this.vodBoardDTO.DTOTOEntity())
                .vodFileNo(this.vodFileNo)
                .vodOriginName(this.vodOriginName)
                .vodSaveName(this.vodSaveName)
                .vodFileObject(this.vodFileObject)
                .build();
        return vodBoardFile;
    }
}
