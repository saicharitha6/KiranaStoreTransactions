package com.example.kirana_store.Exception;

// import org.springframework.web.client.RestClientException;

public class RestClientException extends org.springframework.web.client.RestClientException {

    public RestClientException(String message) {
        super(message);
    }

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

