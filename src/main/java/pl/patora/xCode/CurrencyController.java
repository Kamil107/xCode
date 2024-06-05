package pl.patora.xCode;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.patora.xCode.dto.AuditDto;
import pl.patora.xCode.dto.CurrencyRequest;
import pl.patora.xCode.dto.Rate;
import pl.patora.xCode.dto.RateValue;
import pl.patora.xCode.entities.Audit;
import pl.patora.xCode.services.AuditService;
import pl.patora.xCode.services.CurrencyService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "currencies" )
@RequiredArgsConstructor
public class CurrencyController
{
    private final AuditService auditService;

    private final CurrencyService currencyService;

    @PostMapping( "get-current-currency-value-command" )
    public RateValue getCurrentRate( @RequestBody CurrencyRequest currencyRequest )
    {
        try
        {
            Rate rate = currencyService.getCurrentRate( currencyRequest.currencyCode() );
            auditService.saveAudit( createAudit( currencyRequest, rate.mid(), true ) );
            return new RateValue( rate.mid() );
        }
        catch ( Exception e )
        {
            auditService.saveAudit( createAudit( currencyRequest, null, false ) );
            throw e;
        }
    }

    @GetMapping( "requests" )
    public List<AuditDto> getAudits()
    {
        return auditService.getAll()
            .stream()
            .map( audit -> new AuditDto( audit.getCurrency(), audit.getName(), audit.getDateTime(), audit.getValue(), audit.isSuccess() ) )
            .sorted( Comparator.comparing( AuditDto::dateTime ) )
            .collect( Collectors.toList() );
    }

    private Audit createAudit( CurrencyRequest currencyRequest, Double value, boolean success )
    {
        return new Audit( currencyRequest.currencyCode(), currencyRequest.name(), value, LocalDateTime.now(), success );
    }
}
