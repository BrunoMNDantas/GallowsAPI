package pt.ipl.isel.gallows_game_bot.serviceInterface;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pt.ipl.isel.gallows_game_bot.logic.service.ServiceException;

@CrossOrigin
@ControllerAdvice
@RestController
public class ExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);



    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = IllegalArgumentException.class)
    public String IllegalArgumentExceptionHandler(IllegalArgumentException e){
        logIllegalArgumentException(e);

        return e.getMessage();
    }

    private void logIllegalArgumentException(IllegalArgumentException e){
        String msg = "Invalid arguments received: " + e.getMessage();

        LOGGER.warn(msg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ServiceException.class)
    public String serverExceptionHandler(ServiceException e){
        logServiceException(e);

        return e.getMessage();
    }

    private void logServiceException(ServiceException e){
        String msg = "Service Error: " + e.getMessage();

        LOGGER.error(msg, e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e){
        logException(e);

        return "There was an Internal Error please try again later!";
    }

    private void logException(Exception e){
        String msg = "Error: " + e.getMessage();

        LOGGER.error(msg, e);
    }

}