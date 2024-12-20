package uk.co.xsc.intercom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.xsc.intercom.entity.dto.PhoneClientAccessTokenDto;
import uk.co.xsc.intercom.service.PhoneClientService;

@RestController
@RequestMapping("client")
public class PhoneClientController {

    private final PhoneClientService phoneClientService;

    @Autowired
    public PhoneClientController(PhoneClientService phoneClientService) {
        this.phoneClientService = phoneClientService;
    }

    @GetMapping("/accessToken")
    public PhoneClientAccessTokenDto getAccessToken(@RequestParam("identity") String identity) {
        return new PhoneClientAccessTokenDto(phoneClientService.getAccessToken(identity));
    }

    // todo consider moving to another controller as this endpoint has no auth
    @PostMapping("/makeCall")
    public String makeCall(@RequestParam("destination") String destination, @RequestParam("identity") String identity) {
        return phoneClientService.makeCall(destination, identity);
    }

}
