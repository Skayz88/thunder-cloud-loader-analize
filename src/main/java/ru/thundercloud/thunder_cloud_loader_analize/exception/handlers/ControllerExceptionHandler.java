package ru.thundercloud.thunder_cloud_loader_analize.exception.handlers;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.HttpHostConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.thundercloud.thunder_cloud_loader_analize.exception.dto.HandleError;


/**
 *
 * @author DRakovskiy
 */

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpHostConnectException.class)
    public HandleError handleHttpHostConnectException(HttpHostConnectException ex) {
        return new HandleError(
                ex.getMessage(),
                "HttpHostConnectException"
        );
    }


}
