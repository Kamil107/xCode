package pl.patora.xCode.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import pl.patora.xCode.dto.NbpRateResponse;
import pl.patora.xCode.dto.Rate;
import pl.patora.xCode.exceptions.CurrencyNotFoundException;
import pl.patora.xCode.exceptions.ServiceException;

@Service
@Transactional
public class CurrencyService
{
    public static final String LOCAL_HOST = "http://localhost:8080";

    public static final String RATE_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/{code}?format=json";

    private final RestClient restClient;

    public CurrencyService( RestClient.Builder builder )
    {
        this.restClient = builder
            .baseUrl( LOCAL_HOST )
            .build();
    }

    public Rate getCurrentRate( String code )
    {
        return restClient
            .get()
            .uri( RATE_API_URL, code )
            .retrieve()
            .onStatus( httpStatusCode -> httpStatusCode.equals( HttpStatus.NOT_FOUND ), ( request, response ) -> {
                throw new CurrencyNotFoundException( "Currency with code: %s not found".formatted( code ) );
            } )
            .onStatus( httpStatusCode -> httpStatusCode.equals( HttpStatus.BAD_REQUEST ), ( request, response ) -> {
                throw new ServiceException( "The request was not formulated correctly" );
            } )
            .onStatus( HttpStatusCode::is5xxServerError, ( request, response ) -> {
                throw new ServiceException( "Error with the NBP API" );
            } )
            .body( NbpRateResponse.class )
            .rates()
            .stream()
            .findFirst()
            .orElseThrow( () -> new ServiceException( "Current rate not found" ) );
    }
}
