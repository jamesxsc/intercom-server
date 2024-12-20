package uk.co.xsc.intercom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uk.co.xsc.intercom.entity.User;
import uk.co.xsc.intercom.entity.dto.PhoneClientAccessTokenDto;
import uk.co.xsc.intercom.exception.NumberNotFoundException;
import uk.co.xsc.intercom.repo.UserRepo;
import uk.co.xsc.intercom.service.PhoneClientService;

import java.util.Optional;

@RestController
@RequestMapping("client")
public class PhoneClientController {

    private final PhoneClientService phoneClientService;
    private final UserRepo userRepo;

    @Autowired
    public PhoneClientController(PhoneClientService phoneClientService, UserRepo userRepo) {
        this.phoneClientService = phoneClientService;
        this.userRepo = userRepo;
    }

    @GetMapping("/accessToken")
    public PhoneClientAccessTokenDto getAccessToken(Authentication auth) {
        Optional<User> user = userRepo.findByEmail((String) auth.getPrincipal());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Logged in user cannot be retrieved from data service");
        }
        return new PhoneClientAccessTokenDto(phoneClientService.getAccessToken(user.get()));
    }

    // todo consider moving to another controller as this endpoint has no auth
    @PostMapping("/makeCall")
    public String makeCall(@RequestParam("destination") String destination, @RequestParam("number") String number) {
        return phoneClientService.makeCall(destination, number);
    }

    @PostMapping("/incomingCall")
    public String handleIncomingCall(@RequestParam("To") String to) {
        try {
            return phoneClientService.handleIncomingCall(to);
        } catch (NumberNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number or owner not found!");
        }
    }

}