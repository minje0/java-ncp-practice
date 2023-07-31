package com.example.finalexamplespring.cdn.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class CdnPlusInstanceListDTO {
    @JacksonXmlProperty(localName = "cdnPlusInstance")
    private CdnPlusInstanceDTO cdnPlusInstance;
}
