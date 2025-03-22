package com.github.mengweijin.vita.framework.mvc.handler;

import com.github.mengweijin.vita.framework.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Objects;

/**
 * @author mengweijin
 */
@Slf4j
public abstract class BaseResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * create error info
     *
     * @param e      Throwable
     * @param status HttpStatusCode
     * @return ResponseEntity<Object>
     */
    private ResponseEntity<Object> errorResponseEntity(Exception e, HttpStatusCode status) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(status).body(R.ajax(status.value(), e.getMessage(), null));
    }

    private ResponseEntity<Object> errorTypeMismatchResponseEntity(TypeMismatchException e, HttpStatusCode status) {
        log.error(e.getMessage(), e);
        Object[] args = {e.getPropertyName(), e.getValue()};
        String defaultMessage = "Failed to convert '" + args[0] + "' with value: '" + args[1] + "'";
        return ResponseEntity.status(status).body(R.ajax(status.value(), defaultMessage, null));
    }

    /**
     * response binding result response entity
     *
     * @param e             Throwable
     * @param bindingResult bindingResult
     * @param status    HttpStatusCode
     * @return ResponseEntity<Object>
     */
    private ResponseEntity<Object> errorBindingResultResponseEntity(Exception e, BindingResult bindingResult, HttpStatusCode status) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        R<Void> r = R.failure(status.value(), null);
        for (FieldError error : fieldErrors) {
            r.appendMessage(error.getField() + ": " + error.getDefaultMessage());
        }
        log.warn("Warn binding info: {}", r);
        return ResponseEntity.status(status).body(r);
    }

    private ResponseEntity<Object> errorMethodValidationResponseEntity(Exception e, MethodValidationResult result, HttpStatusCode status) {
        List<? extends MessageSourceResolvable> allErrors = result.getAllErrors();
        R<Void> r = R.failure(status.value(), null);
        allErrors.forEach(error -> {
            r.appendMessage(Objects.requireNonNull(error.getCodes())[0] + ": " + error.getDefaultMessage());
        });
        log.warn("Warn validation info: {}", r);
        return ResponseEntity.status(status).body(r);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorBindingResultResponseEntity(ex, ex.getBindingResult(), status);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorMethodValidationResponseEntity(ex, ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorTypeMismatchResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorTypeMismatchResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return errorResponseEntity(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodValidationException(MethodValidationException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return errorMethodValidationResponseEntity(ex, ex, status);
    }

}
