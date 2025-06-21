package captchaservice.captchaservice.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import captchaservice.captchaservice.Dto.CaptchaResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${captcha_key}")
    private String captchaKey;
    @Value("{sitekey}")
    private String siteKey;

    public CaptchaResponse verificarCaptcha(String solution) {
        String url = "https://api.friendlycaptcha.com/api/v1/siteverify"; // Reemplaza con la URL adecuada
        
        // Configurar los parámetros de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("solution", solution);
        requestBody.put("secret", captchaKey);
        requestBody.put("sitekey", siteKey);

        // Crear la entidad HTTP con los encabezados y el cuerpo
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Realizar la solicitud POST y obtener la respuesta
        ResponseEntity<CaptchaResponse> responseEntity = restTemplate.postForEntity(url, requestEntity, CaptchaResponse.class);

        // Devolver la respuesta
        return responseEntity.getBody();

    }
}