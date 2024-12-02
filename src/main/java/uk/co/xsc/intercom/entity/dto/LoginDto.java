package uk.co.xsc.intercom.entity.dto;

import lombok.Getter;
import lombok.Setter;

public class LoginDto {

    @Setter @Getter
    private String username;

    @Setter @Getter
    private String password;

}
