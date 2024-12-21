package uk.co.xsc.intercom.service;

import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VoiceGrant;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.*;
import com.twilio.twiml.voice.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.co.xsc.intercom.entity.PhoneNumber;
import uk.co.xsc.intercom.entity.User;
import uk.co.xsc.intercom.exception.NumberNotFoundException;
import uk.co.xsc.intercom.repo.PhoneNumberRepo;
import uk.co.xsc.intercom.repo.UserRepo;

@Service
public class PhoneClientService {

    private final String appSid;
    private final String sid;
    private final String apiKey;
    private final String secret;
    private final String pushSid;
    private final PhoneNumberRepo phoneNumberRepo;
    private final UserRepo userRepo;

    @Autowired
    public PhoneClientService(
            @Value("${twilio.appSid}") String appSid,
            @Value("${twilio.sid}") String sid,
            @Value("${twilio.apiKey}") String apiKey,
            @Value("${twilio.secret}") String secret,
            @Value("${twilio.pushSid}") String pushSid,
            PhoneNumberRepo phoneNumberRepo, UserRepo userRepo) {
        this.appSid = appSid;
        this.sid = sid;
        this.apiKey = apiKey;
        this.secret = secret;
        this.pushSid = pushSid;
        this.phoneNumberRepo = phoneNumberRepo;
        this.userRepo = userRepo;
    }

    public String getAccessToken(User user) {
        VoiceGrant grant = new VoiceGrant();
        grant.setOutgoingApplicationSid(appSid);
        grant.setPushCredentialSid(pushSid);

        AccessToken token = new AccessToken.Builder(
                sid,
                apiKey,
                secret
        ).identity(user.getEmail()).grant(grant).build();
        return token.toJwt();
    }

    public String makeCall(String to, String number) {
        // TODO validate "to" phone number - use @valid annotations? and clients number use auth
        Number destination = new Number.Builder(to).build();

        System.out.println("Making call to " + to + " with identity " + number);

        Dial dial = new Dial.Builder().callerId(number).number(destination).build();
        VoiceResponse response = new VoiceResponse.Builder().dial(dial).build();

        return response.toXml();
    }

    public String handleIncomingCall(String to) throws NumberNotFoundException {
        // TODO connect on press key https://www.twilio.com/docs/voice/twiml/gather#attributes

        to = to.replace("+", "");

        // Twilio give us a number, we identify the owner and route the call to their client and registered device(s)
        // Note that this is a simple approach and doesn't employ an organisation-wide matrix o.e.
        PhoneNumber number = phoneNumberRepo.findByNumber(to).orElseThrow(NumberNotFoundException::new);
        User user = userRepo.findById(number.getUserId()).orElseThrow(NumberNotFoundException::new);

        Client client = new Client.Builder(user.getEmail()).build();
        Dial dial = new Dial.Builder().client(client).build();
        Say say = new Say.Builder().addText("Routing now")
                .emphasis(new SsmlEmphasis.Builder().level(SsmlEmphasis.Level.STRONG).build())
                .build();

        VoiceResponse response = new VoiceResponse.Builder().say(say).dial(dial).build();

        // Return TwiML to Twilio
        return response.toXml();
    }

}