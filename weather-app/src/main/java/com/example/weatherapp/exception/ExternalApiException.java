package com.example.weatherapp.exception;

/**
 * 外部API（Open-Meteo）との連携で発生した例外
 */
public class ExternalApiException extends RuntimeException {

    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}