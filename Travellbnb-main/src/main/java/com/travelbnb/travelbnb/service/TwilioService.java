package com.travelbnb.travelbnb.service;
import com.travelbnb.travelbnb.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {


 private final TwilioConfig twilioConfig ;


    public static final String ACCOUNT_SID = "acc sid";
    public static final String AUTH_TOKEN = "token";

    public TwilioService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;

    }

    public String sendSms(String to, String message) {
        Message sms= Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),
                message).create();
        return sms.getSid();
    }

//TwilioWhatsapp
    public String sendWhatsAppMessage(String to, String messageBody) {
        try {
            PhoneNumber toPhoneNumber = new PhoneNumber("whatsapp:" + to);
            PhoneNumber fromPhoneNumber = new PhoneNumber("whatsapp:" + twilioConfig.getWhatsappTwilio());
            Message message = Message.creator(toPhoneNumber, fromPhoneNumber, messageBody).create();
            return message.getSid();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


