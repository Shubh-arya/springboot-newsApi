package com.demo.newsapi.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default exception handler class for input validation
 *
 * @Author: Shubham Arya
 * @Version: 0.1
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String INVALID_REQUEST_BODY = "Invalid request body";
    public static final String INVALID_VALUE = "Invalid value";
    public static final String INVALID_TYPE = "Invalid type";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> fieldsError = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                FieldError::getField, FieldError::getDefaultMessage
        ));
        String message = INVALID_REQUEST_BODY;
        return getErrorResponse(headers, status, fieldsError, message);
    }

    private ResponseEntity<Object> getErrorResponse(HttpHeaders headers, HttpStatusCode status, Map<String, String> fieldsError, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setFields(fieldsError);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException exx) {
            String completePath = exx.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(Collectors.joining());
            HashMap<String, String> fieldErrors = new HashMap<>();
            fieldErrors.put(completePath, INVALID_VALUE);
            return getErrorResponse(headers, status, fieldErrors, INVALID_TYPE);
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }
}

