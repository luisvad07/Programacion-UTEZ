package utez.edu.mx.foodster.services.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utez.edu.mx.foodster.dtos.captcha.CaptchaResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {
    private final RestTemplate restTemplate;
    @Value("${FRIENDLYCAPTCHA.CAPTCHAKEY}")
    private String captchaKey;
    @Value("${FRIENDLYCAPTCHA.SITEKEY}")
    private String siteKey;

    public CaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CaptchaResponse verificarCaptcha(String solucion) {
        String url = "https://api.friendlycaptcha.com/api/v1/siteverify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("solution", solucion);
        requestBody.put("secret", captchaKey);
        requestBody.put("sitekey", siteKey);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<CaptchaResponse> responseEntity =
        restTemplate.postForEntity(
            url, 
            requestEntity,
            CaptchaResponse.class);
        return responseEntity.getBody();
    }

    public Boolean verificarCaptchaBoolean(String solucion) {
        CaptchaResponse response = verificarCaptcha(solucion);
        return response.isSuccess();
    }
}