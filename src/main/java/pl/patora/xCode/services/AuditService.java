package pl.patora.xCode.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patora.xCode.entities.Audit;
import pl.patora.xCode.repositories.AuditRepository;

import java.util.List;

@Service
@RequiredArgsConstructor( onConstructor_ = { @Autowired } )
@Transactional
public class AuditService
{
    private final AuditRepository auditRepository;

    public void saveAudit( Audit audit )
    {
        auditRepository.save( audit );
    }

    public List<Audit> getAll()
    {
        return auditRepository.findAll();
    }
}
