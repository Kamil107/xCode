package pl.patora.xCode.dto;

import java.time.LocalDateTime;

public record AuditDto(String currency, String name, LocalDateTime dateTime, Double value, boolean success)
{
}
