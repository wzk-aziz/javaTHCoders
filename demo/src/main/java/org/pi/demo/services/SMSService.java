package org.pi.demo.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {

    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    public void sendSMS(String to, String from, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message.creator(
                new PhoneNumber(to),  // To number
                new PhoneNumber(from),  // From number
                message)  // SMS body
                .create();
    }
}