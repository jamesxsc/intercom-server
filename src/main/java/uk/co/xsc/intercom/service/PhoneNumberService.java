package uk.co.xsc.intercom.service;

import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.Local;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.LocalReader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneNumberService {

    public List<String> listPhoneNumbers(String iso3166Country, String query) {
        LocalReader localReader = Local.reader(iso3166Country);
        if (query != null && query.length() >= 2) {
            localReader.setContains(query);
        }
        ResourceSet<Local> locals = localReader.read();

        List<String> returnList = new ArrayList<>();
        for (Local local : locals) {
            returnList.add(local.getPhoneNumber().toString());
        }
        return returnList;
    }

}
