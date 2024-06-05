package pl.patora.xCode.dto;

import java.util.List;

public record NbpRateResponse(String currency, String code, List<Rate> rates)
{
}
