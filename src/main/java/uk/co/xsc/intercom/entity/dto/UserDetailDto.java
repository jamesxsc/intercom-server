package uk.co.xsc.intercom.entity.dto;

import lombok.Getter;
import uk.co.xsc.intercom.entity.PhoneNumber;
import uk.co.xsc.intercom.entity.User;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailDto {

    // Getters are required in the DTO

    private final String email;

    private final List<PhoneNumber> phoneNumbers;

    // Constructor for factory method
    private UserDetailDto(String email, List<PhoneNumber> phoneNumbers) {
        this.email = email;
        this.phoneNumbers = phoneNumbers;
    }

    public static UserDetailDto from(User user) {
        return new UserDetailDto(user.getEmail(), user.getPhoneNumbers());
    }

}
