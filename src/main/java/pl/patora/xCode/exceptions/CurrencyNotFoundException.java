package pl.patora.xCode.exceptions;

public class CurrencyNotFoundException
    extends RuntimeException
{
    public CurrencyNotFoundException( String message )
    {
        super( message );
    }
}
