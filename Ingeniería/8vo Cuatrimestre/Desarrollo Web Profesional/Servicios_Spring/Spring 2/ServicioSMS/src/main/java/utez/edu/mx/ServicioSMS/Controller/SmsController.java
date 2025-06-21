package utez.edu.mx.ServicioSMS.Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Value("${TWILIO_ACCOUNT_SID}")
    String sid;

    @Value("${TWILIO_AUTH_TOKEN}")
    String token;

    @Value("${TWILIO_NUMBER}")
    String phone_number;

    @GetMapping(value = "/sendSMS")
    public ResponseEntity<String> sendSMS() {

        Twilio.init(sid, token);

        Message.creator(
                new PhoneNumber("+527772832725"),
                    new PhoneNumber(phone_number),
                    "Las Chivas").create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }
}
