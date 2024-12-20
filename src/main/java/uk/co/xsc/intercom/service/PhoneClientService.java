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
    private final String pushSid;

    @Autowired
    public PhoneClientService(
            @Value("${twilio.appSid}") String appSid,
            @Value("${twilio.sid}") String sid,
            @Value("${twilio.apiKey}") String apiKey,
            @Value("${twilio.secret}") String secret,
            @Value("${twilio.pushSid}") String pushSid
    ) {
        this.appSid = appSid;
        this.sid = sid;
        this.apiKey = apiKey;
        this.secret = secret;
        this.pushSid = pushSid;
    }

    public String getAccessToken(String identity) {
        VoiceGrant grant = new VoiceGrant();
        grant.setOutgoingApplicationSid(appSid);
        grant.setPushCredentialSid(pushSid);

        AccessToken token = new AccessToken.Builder(
                sid,
                apiKey,
                secret
        ).identity(identity).grant(grant).build();
        return token.toJwt();
    }

    public String makeCall(String to, String identity) {
        // TODO validate "to" phone number - use @valid annotations?
        Number destination = new Number.Builder(to).build();

        System.out.println("Making call to " + to + " with identity " + identity);
        // TODO the identity parameter here should be a number not the internal identity
        // this will form part of a major refactor splitting numbers from identities

        // the caller id can be internal string identity or number
        Dial dial = new Dial.Builder().callerId("$test number").number(destination).build();
        VoiceResponse response = new VoiceResponse.Builder().dial(dial).build();

        return response.toXml();
    }

}
