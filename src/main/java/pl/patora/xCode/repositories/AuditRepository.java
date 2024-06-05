package pl.patora.xCode.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patora.xCode.entities.Audit;

@Repository
public interface AuditRepository
    extends JpaRepository<Audit, Long>
{
}
