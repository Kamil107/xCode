package pl.patora.xCode.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.patora.xCode.exceptions.CurrencyNotFoundException;
import pl.patora.xCode.exceptions.ServiceException;
import pl.patora.xCode.helpers.RestResult;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler( ServiceException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ResponseBody
    public RestResult handleServiceException( ServiceException e )
    {
        return new RestResult( false, e.getMessage() );
    }

    @ExceptionHandler( CurrencyNotFoundException.class )
    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ResponseBody
    public RestResult handleNotFoundException( CurrencyNotFoundException e )
    {
        return new RestResult( false, e.getMessage() );
    }
}
