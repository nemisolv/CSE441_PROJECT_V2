package com.cse441.weather.data.model;

public class ApiResponse<T> {
    private T data; // assuming the API returns a user object
    private String message; // you can include a message if your API returns one
    private boolean success; // to indicate if the API call was successful

    // Constructor
    public ApiResponse(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
