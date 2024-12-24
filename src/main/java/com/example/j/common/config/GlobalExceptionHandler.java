package com.example.j.common.config;


import com.example.j.common.JsonResponse;
import com.example.j.common.exception.JException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.management.JMException;
import java.util.Iterator;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public JsonResponse<Object> messageExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.toString(), e);
        return JsonResponse.FAIL().message("http 消息不可读");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public JsonResponse<Object> handle(MethodArgumentTypeMismatchException e) {
        String name = e.getName();
        return JsonResponse.FAIL().message(name + " 格式错误");
    }

    // support for @Validated message
    @ExceptionHandler(ConstraintViolationException.class)
    public JsonResponse handle(ConstraintViolationException e) {
        String message = null;
        Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator();
        if (it.hasNext()) {
            message = it.next().getMessageTemplate();
        }
        return new JsonResponse().fail().message(message);
    }

    @ExceptionHandler(BindException.class)
    public JsonResponse handle(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        return JsonResponse.FAIL().message(Objects.requireNonNull(fieldError.getDefaultMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JsonResponse handle(HttpRequestMethodNotSupportedException e) {
        log.warn(e.toString(), e);
        return new JsonResponse().code(JsonResponse.METHOD_NOT_ALLOWED).message("请求方法不支持");
    }


    @ExceptionHandler(JMException.class)
    public JsonResponse handle(JException e) {
        log.warn(e.toString(), e);
        return new JsonResponse().code(e.getCode()).message(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public JsonResponse handle(Exception e) {
        log.error("服务器内部错误: {}, 堆栈如下:", e.toString(), e);
        return new JsonResponse().message("操作失败，服务器内部错误").code(500);
    }
}
