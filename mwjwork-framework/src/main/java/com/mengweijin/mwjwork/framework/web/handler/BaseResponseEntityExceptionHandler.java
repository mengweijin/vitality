package com.mengweijin.mwjwork.framework.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * @author mengweijin
 */
@Slf4j
public abstract class BaseResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * create error info
     *
     * @param e      Throwable
     * @param status HttpStatus
     * @return ResponseEntity<Object>
     */
    public ResponseEntity<Object> errorResponseEntity(Throwable e, HttpStatus status) {
        log.error(e.getMessage(), e);
        ErrorInfo errorInfo = new ErrorInfo().setCode(status.value()).addMessage(e.getMessage());
        return ResponseEntity.status(status).body(errorInfo);
    }

    /**
     * response binding result response entity
     *
     * @param bindingResult bindingResult
     * @param e             Throwable
     * @param status        HttpStatus
     * @return ResponseEntity<Object>
     */
    public ResponseEntity<Object> errorBindingResultResponseEntity(BindingResult bindingResult, Throwable e, HttpStatus status) {
        log.error(e.getMessage(), e);
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        ErrorInfo errorInfo = new ErrorInfo().setCode(status.value());
        for (FieldError error : fieldErrors) {
            errorInfo.addMessage(error.getField() + ": " + error.getDefaultMessage() + "!");
        }
        log.error("ErrorInfo: {}", errorInfo.toString());
        return ResponseEntity.status(status).body(errorInfo);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorBindingResultResponseEntity(ex.getBindingResult(), ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorBindingResultResponseEntity(ex.getBindingResult(), ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        return errorResponseEntity(ex, status);
    }

}
