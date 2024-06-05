package pl.patora.xCode.dto;

import pl.patora.xCode.exceptions.ServiceException;

import java.util.Optional;

public record CurrencyRequest(String name, String currencyCode)
{
    public CurrencyRequest
    {
        currencyCode = Optional.ofNullable( currencyCode )
            .filter( n -> !n.trim().isEmpty() )
            .map( String::toUpperCase )
            .orElseThrow( () -> new ServiceException( "Required property is empty: currencyCode" ) );
        
        name = Optional.ofNullable( name )
            .filter( n -> !n.trim().isEmpty() )
            .orElseThrow( () -> new ServiceException( "Required property is empty: name" ) );
    }
}
