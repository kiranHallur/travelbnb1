package com.travelbnb.travelbnb.config;



import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @Value("${twilio.whatsapp.number}")
    private String twilioWhatsappNumber;



    @Bean
    public void twilioInitializing(){
        Twilio.init(accountSid,authToken);
    }

    public String getTwilioPhoneNumber() {
        return twilioPhoneNumber;
    }

    public String getWhatsappTwilio(){
        return twilioWhatsappNumber;
    }
}

