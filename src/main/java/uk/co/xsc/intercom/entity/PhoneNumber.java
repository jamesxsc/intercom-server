package uk.co.xsc.intercom.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
public class PhoneNumber {

    // Getters are required for Spring to return an object

    @Id
    private String number;

    private long userId;

    private String sid;

    // Constructor for Spring Data instantiation
    public PhoneNumber() {}

    // Constructor for registration
    public PhoneNumber(String number, User owner, String sid) {
        this.number = number;
        this.userId = owner.getId();
        this.sid = sid;
    }

}
