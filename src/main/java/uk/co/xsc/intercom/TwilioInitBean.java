package uk.co.xsc.intercom;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TwilioInitBean {

    private final String sid;
    private final String token;

    public TwilioInitBean(@Value("${twilio.sid}") String sid, @Value("${twilio.token}") String token) {
        this.sid = sid;
        this.token = token;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("TwilioInitBean.onApplicationEvent");
        Twilio.init(sid, token);
    }

}
