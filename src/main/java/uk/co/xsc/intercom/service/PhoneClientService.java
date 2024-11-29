package uk.co.xsc.intercom.service;

import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VoiceGrant;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhoneClientService {

    private final String appSid;
    private final String sid;
    private final String apiKey;
    private final String secret;

    @Autowired
    public PhoneClientService(
            @Value("${twilio.appSid}") String appSid,
            @Value("${twilio.sid}") String sid,
            @Value("${twilio.apiKey}") String apiKey,
            @Value("${twilio.secret}") String secret
    ) {
        this.appSid = appSid;
        this.sid = sid;
        this.apiKey = apiKey;
        this.secret = secret;
    }

    public String getAccessToken(String identity) {
        VoiceGrant grant = new VoiceGrant();
        grant.setOutgoingApplicationSid(appSid);
//        grant.setPushCredentialSid(); // to be done when we have push support

        AccessToken token = new AccessToken.Builder(
                sid,
                apiKey,
                secret
        ).identity(identity).grant(grant).build();
        return token.toJwt();
    }

    public String makeCall(String to) {
        // TODO validate "to" phone number
        Number destination = new Number.Builder(to).build();
        Dial dial = new Dial.Builder().callerId("442045380800").number(destination).build(); // this can be identity or number
        VoiceResponse response = new VoiceResponse.Builder().dial(dial).build();

        return response.toXml();
    }

}
