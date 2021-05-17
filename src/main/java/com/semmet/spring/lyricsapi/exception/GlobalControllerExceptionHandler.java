package com.semmet.spring.lyricsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class GlobalControllerExceptionHandler extends RuntimeException {
    
}
