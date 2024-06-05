package pl.patora.xCode.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
public final class Audit
{
    @Id
    @SequenceGenerator( name = "audit_sequence", sequenceName = "audit_sequence", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "audit_sequence" )
    private Long id;

    private String currency;

    private String name;

    private Double value;

    private LocalDateTime dateTime;

    private boolean success;

    public Audit( String currency, String name, Double value, LocalDateTime dateTime, boolean success )
    {
        this.currency = currency;
        this.name = name;
        this.value = value;
        this.dateTime = dateTime;
        this.success = success;
    }
}
