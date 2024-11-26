package uk.co.xsc.intercom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public String getAccessToken(@RequestParam("identity") String identity) {
        return phoneClientService.getAccessToken(identity);
    }

    // TODO add from parameter once that is on the client
    @PostMapping("/makeCall")
    public String makeCall(@RequestParam("to") String to) {
        return phoneClientService.makeCall(to);
    }

}
