package pl.patora.xCode.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import pl.patora.xCode.dto.NbpRateResponse;
import pl.patora.xCode.dto.Rate;
import pl.patora.xCode.exceptions.CurrencyNotFoundException;
import pl.patora.xCode.exceptions.ServiceException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static pl.patora.xCode.services.CurrencyService.RATE_API_URL;

@RestClientTest( CurrencyService.class )
class CurrencyServiceTest
{
    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCurrentRate_Successful()
        throws JsonProcessingException
    {
        Rate expectedRate = new Rate( "1", LocalDate.of( 2024, 6, 5 ), 4.53 );
        NbpRateResponse expectedNbpRateResponse = new NbpRateResponse( "Euro", "EUR", List.of( expectedRate ) );

        server.expect( requestTo( RATE_API_URL.replace( "{code}", "EUR" ) ) )
            .andRespond( withSuccess( objectMapper.writeValueAsString( expectedNbpRateResponse ), APPLICATION_JSON ) );
        Rate actualRate = currencyService.getCurrentRate( "EUR" );

        assertEquals( expectedNbpRateResponse.rates().get( 0 ).mid(), actualRate.mid() );
    }

    @Test
    void getCurrentRate_NotFound()
    {
        server.expect( requestTo( RATE_API_URL.replace( "{code}", "XYZ" ) ) )
            .andRespond( withStatus( HttpStatus.NOT_FOUND ) );

        assertThrows( CurrencyNotFoundException.class, () -> currencyService.getCurrentRate( "XYZ" ) );
    }

    @Test
    void getCurrentRate_BadRequest()
    {
        server.expect( requestTo( RATE_API_URL.replace( "{code}", "BAD" ) ) )
            .andRespond( withStatus( HttpStatus.BAD_REQUEST ) );

        assertThrows( ServiceException.class, () -> currencyService.getCurrentRate( "BAD" ) );
    }

    @Test
    void getCurrentRate_ServerError()
    {
        server.expect( requestTo( RATE_API_URL.replace( "{code}", "EUR" ) ) )
            .andRespond( withServerError() );

        assertThrows( ServiceException.class, () -> currencyService.getCurrentRate( "EUR" ) );
    }
}