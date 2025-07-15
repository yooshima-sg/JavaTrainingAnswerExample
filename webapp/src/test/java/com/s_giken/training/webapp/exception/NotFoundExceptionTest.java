package com.s_giken.training.webapp.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class NotFoundExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "テストエラーメッセージ";
        NotFoundException exception = new NotFoundException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void testResponseStatusAnnotation() {
        ResponseStatus annotation = NotFoundException.class.getAnnotation(ResponseStatus.class);

        assertEquals(HttpStatus.NOT_FOUND, annotation.value());
    }

    @Test
    void testIsRuntimeException() {
        NotFoundException exception = new NotFoundException("test");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}