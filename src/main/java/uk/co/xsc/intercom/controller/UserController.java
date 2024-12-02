package uk.co.xsc.intercom.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.xsc.intercom.entity.PhoneNumber;
import uk.co.xsc.intercom.entity.User;
import uk.co.xsc.intercom.entity.dto.UserDetailDto;
import uk.co.xsc.intercom.repo.UserRepo;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/detail")
    public UserDetailDto getUserDetail(Authentication auth) {
        User user = userRepo.findByEmail((String) auth.getPrincipal()).orElse(new User());
        return UserDetailDto.from(user);
    }

}
