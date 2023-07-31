package com.example.finalexamplespring.cdn.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class CdnPlusInstanceDTO {
    @JacksonXmlProperty(localName = "cdnInstanceNo")
    private int cdnInstanceNo;

    @JacksonXmlProperty(localName = "cdnInstanceStatusName")
    private String cdnInstanceStatusName;

    @JacksonXmlProperty(localName = "serviceName")
    private String serviceName;
}
