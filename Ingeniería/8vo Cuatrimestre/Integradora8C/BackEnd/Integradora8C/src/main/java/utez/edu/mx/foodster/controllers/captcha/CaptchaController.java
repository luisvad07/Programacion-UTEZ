package utez.edu.mx.foodster.controllers.captcha;


import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.captcha.CaptchaResponse;
import utez.edu.mx.foodster.services.captcha.CaptchaService;

@RestController
@CrossOrigin(
    origins = "*", 
    methods = 
    { RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("${apiPrefix}/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @PostMapping("/verificar-captcha")
    public CaptchaResponse verificarCaptcha(
        @RequestParam("solution") String solution) {
        return captchaService.
        verificarCaptcha(solution);
    }
}