package com.github.mengweijin.vitality.framework.mvc;

import com.github.mengweijin.vitality.framework.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
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
     * @param statusCode HttpStatusCode
     * @return ResponseEntity<Object>
     */
    public ResponseEntity<Object> errorResponseEntity(Exception e, HttpStatusCode statusCode) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(statusCode).body(R.info(statusCode.value(), null, e.getMessage()));
    }

    /**
     * response binding result response entity
     *
     * @param e             Throwable
     * @param bindingResult bindingResult
     * @param statusCode    HttpStatusCode
     * @return ResponseEntity<Object>
     */
    public ResponseEntity<Object> errorBindingResultResponseEntity(Exception e, BindingResult bindingResult, HttpStatusCode statusCode) {
        log.error(e.getMessage(), e);
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        R r = R.error(statusCode.value(), null);
        for (FieldError error : fieldErrors) {
            r.appendMessage(error.getField() + ": " + error.getDefaultMessage() + "!");
        }
        log.error("ErrorInfo: {}", r);
        return ResponseEntity.status(statusCode).body(r);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorBindingResultResponseEntity(ex, ex.getBindingResult(), statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return errorResponseEntity(ex, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest webRequest) {
        return errorResponseEntity(ex, statusCode);
    }

}
