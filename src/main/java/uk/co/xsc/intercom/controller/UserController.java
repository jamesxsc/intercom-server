package uk.co.xsc.intercom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.xsc.intercom.entity.User;
import uk.co.xsc.intercom.entity.dto.UserDetailDto;
import uk.co.xsc.intercom.repo.UserRepo;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/detail")
    public UserDetailDto getUserDetail(Authentication auth) {
        Optional<User> user = userRepo.findByEmail((String) auth.getPrincipal());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Logged in user cannot be retrieved from data service");
        }
        return UserDetailDto.from(user.get());
    }

}
