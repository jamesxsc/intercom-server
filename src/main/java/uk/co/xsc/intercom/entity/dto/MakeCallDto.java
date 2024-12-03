package uk.co.xsc.intercom.entity.dto;

import lombok.Getter;

public class MakeCallDto {

    @Getter
    private String destination;

    @Getter
    private String identity;

}
