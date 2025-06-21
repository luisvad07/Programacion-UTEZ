package utez.edu.mx.foodster.dtos.captcha;

import java.util.List;

public class CaptchaResponse {
    
    
    private boolean success;
    private List<String> errors; 
    
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
}