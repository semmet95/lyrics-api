package com.semmet.spring.lyricsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid artist/track parameter")
public class InvalidArgumentExceptionHandler extends RuntimeException {
    
}
