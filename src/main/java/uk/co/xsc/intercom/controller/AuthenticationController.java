package uk.co.xsc.intercom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uk.co.xsc.intercom.JWTUtil;
import uk.co.xsc.intercom.entity.dto.JWTTokenDto;
import uk.co.xsc.intercom.entity.dto.LoginDto;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    // TODO put register somewhere

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public JWTTokenDto login(@RequestBody LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            authenticationManager.authenticate(token);
            return new JWTTokenDto(jwtUtil.generateToken(loginDto.getUsername()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials provided");
        }
    }

}
