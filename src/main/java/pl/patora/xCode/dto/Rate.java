package pl.patora.xCode.dto;

import java.time.LocalDate;

public record Rate(String no, LocalDate effectiveDate, Double mid)
{
}
