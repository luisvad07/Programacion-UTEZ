package utez.tienda.tiendautez.utils;

public class ResultAction<T> {
    T obj;
    private String message;
    private int Status;
    private boolean result;

    public ResultAction() {
    }

    public ResultAction(String message, int status, boolean result) {
        this.message = message;
        Status = status;
        this.result = result;
    }

    public ResultAction(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
