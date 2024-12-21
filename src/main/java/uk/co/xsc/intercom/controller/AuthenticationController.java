package uk.co.xsc.intercom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uk.co.xsc.intercom.JWTUtil;
import uk.co.xsc.intercom.entity.User;
import uk.co.xsc.intercom.entity.dto.JWTTokenDto;
import uk.co.xsc.intercom.entity.dto.LoginDto;
import uk.co.xsc.intercom.service.UserService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    // TODO put register somewhere

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public JWTTokenDto login(@RequestBody LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            authenticationManager.authenticate(token);
            User user = (User) userService.loadUserByUsername(loginDto.getUsername());
            return new JWTTokenDto(jwtUtil.generateToken(user.getId()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials provided");
        }
    }

}
