package uk.co.xsc.intercom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.xsc.intercom.service.PhoneNumberService;

import java.util.List;


@RestController
@RequestMapping("/numbers")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("/numbers")
    public List<String> listPhoneNumbers(@RequestParam("country") String iso3166Country,
                                         @RequestParam(required = false) String query) {
        return phoneNumberService.listPhoneNumbers(iso3166Country, query);
    }

}
