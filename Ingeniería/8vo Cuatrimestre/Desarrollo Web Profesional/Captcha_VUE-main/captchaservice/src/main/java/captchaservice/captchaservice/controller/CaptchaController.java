package captchaservice.captchaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import captchaservice.captchaservice.Dto.CaptchaResponse;
import captchaservice.captchaservice.Service.CaptchaService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("/api/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/verificar-captcha")
    public CaptchaResponse verificarCaptcha(@RequestParam("solution") String solution) {
        return captchaService.verificarCaptcha(solution);
    }
}