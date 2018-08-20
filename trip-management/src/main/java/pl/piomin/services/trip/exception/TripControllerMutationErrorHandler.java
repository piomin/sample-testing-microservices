package pl.piomin.services.trip.exception;

import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.piomin.services.trip.model.Trip;
import pl.piomin.services.trip.model.TripStatus;

@ControllerAdvice
public class TripControllerMutationErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RetryableException.class)
    protected ResponseEntity<Object> handleFeignTimeout(RuntimeException ex, WebRequest request) {
        Trip trip = new Trip();
        trip.setStatus(TripStatus.REJECTED);
        return handleExceptionInternal(ex, trip, null, HttpStatus.OK, request);
    }

}
