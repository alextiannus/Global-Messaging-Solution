/**
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.wormwood.config;

import com.wormwood.response.Response;
import com.wormwood.result.ResultEnum;
import com.wormwood.config.i18n.MessageSourceService;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 异常处理基类
 *
 * @author ylin
 */
@ControllerAdvice
public class BaseRestExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Exception type:" + ex.getClass().getName());
        logger.error("ERROR---" + ex.getLocalizedMessage(), ex);
        final Response response = new Response(ResultEnum.UNKNOWN);
        return super.handleExceptionInternal(ex, response, headers, response.getHttpStatus(), request);
    }

    // 400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = getErrors(ex.getBindingResult()).toString();
        final String firstError = getFirstError(ex.getBindingResult());
        final Response response = new Response(ResultEnum.PARAMS_NOT_VALID);
        response.setMsg(firstError);
        response.setValue(error);
        return response.build();
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = getErrors(ex.getBindingResult()).toString();
        final String firstError = getFirstError(ex.getBindingResult());
        final Response response = new Response(ResultEnum.PARAMS_NOT_VALID);
        response.setMsg(firstError);
        response.setValue(error);
        return response.build();
    }

    private String getFirstError(BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        return fieldError == null ? "" : fieldError.getDefaultMessage();
    }

    private List<String> getErrors(BindingResult bindingResult) {
        final List<String> errors = new ArrayList<>(10);
        errors.addAll(bindingResult.getFieldErrors().stream()
                .map(error ->
                        error.getField() + ":" + error.getDefaultMessage())
                .collect(Collectors.toList()));
        errors.addAll(bindingResult.getGlobalErrors().stream()
                .map(error ->
                        error.getObjectName() + ":" + error.getDefaultMessage())
                .collect(Collectors.toList()));
        return errors;
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        final Response response = new Response(ResultEnum.PARAMS_NOT_VALID);
        response.setMsg(error.toString());
        return response.build();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = ex.getRequestPartName() + " part is missing";
        final Response response = new Response(ResultEnum.PARAMS_NOT_VALID);
        response.setMsg(error.toString());
        return response.build();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = ex.getParameterName() + " parameter is missing";
        final Response response = new Response(ResultEnum.PARAMS_NOT_VALID);
        response.setMsg(error.toString());
        return response.build();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        final Response response = new Response(ResultEnum.PARAMS_NOT_VALID);
        response.setMsg(error.toString());
        return response.build();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        final List<String> errors = ex.getConstraintViolations().stream()
                .map(violation ->
                        violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());
        final Response response = new Response(ResultEnum.PARAMS_NOT_VALID);
        response.setMsg(errors.toString());
        return response.build();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = "msg not readable";
        final Response response = new Response(ResultEnum.PARAMS_NOT_VALID);
        response.setMsg(error);
        return response.build();
    }

    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        final Response response = new Response(ResultEnum.NOT_FOUND);
        response.setMsg(error);
        return response.build();
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" request method not supported, only support ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        final Response response = new Response(ResultEnum.METHOD_NOT_ALLOWED);
        response.setMsg(builder.toString());
        return response.build();
    }

    // 415
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type not supported, only support ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
        final Response response = new Response(ResultEnum.UNSUPPORTED_MEDIA_TYPE);
        response.setMsg(builder.toString());
        return response.build();
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDenied(final AccessDeniedException ex, final WebRequest request) {
        final Response response = new Response(ResultEnum.ACCESS_DENIED);
        return response.build();
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        logger.info("Exception type:" + ex.getClass().getName());
        logger.error("ERROR---" + ex.getLocalizedMessage(), ex);
        final Response response = new Response(ResultEnum.FAIL);
        return response.build();
    }

}