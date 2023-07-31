package com.example.finalexamplespring.cdn.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@JacksonXmlRootElement(localName = "getCdnPlusInstanceListResponse")
@Data
public class CdnPlusInstanceListResponseDTO {
    @JacksonXmlProperty(localName = "requestId")
    private String requestId;

    @JacksonXmlProperty(localName = "returnCode")
    private int returnCode;

    @JacksonXmlProperty(localName = "returnMessage")
    private String returnMessage;

    @JacksonXmlProperty(localName = "totalRows")
    private int totalRows;

    @JacksonXmlProperty(localName = "cdnPlusInstanceList")
    private CdnPlusInstanceListDTO cdnPlusInstanceList;
}
