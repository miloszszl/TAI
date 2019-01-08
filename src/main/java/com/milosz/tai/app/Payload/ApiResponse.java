package com.milosz.tai.app.Payload;

public class ApiResponse {
    private Boolean success;
    private String fieldName;
    private String message;

    public ApiResponse(Boolean success, String fieldName, String message) {
        this.success = success;
        this.fieldName = fieldName;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
