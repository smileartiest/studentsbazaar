package com.studentsbazaar.studentsbazaarapp.model;

public class Response {

    private String uuid;

    private int code;

    private String message;

    private boolean isServerError = false;
    private String errorName;

    public boolean isServerError() {
        return isServerError;
    }

    public void setServerError(boolean serverError) {
        isServerError = serverError;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
