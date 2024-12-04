package tp.appliSpring.bank.core.service.direct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tp.appliSpring.generic.converter.GenericMapper;
import tp.appliSpring.bank.core.service.ServiceOperation;
import tp.appliSpring.bank.persistence.repository.CompteRepository;
import tp.appliSpring.bank.persistence.repository.OperationRepository;
import tp.appliSpring.bank.persistence.entity.CompteEntity;
import tp.appliSpring.bank.persistence.entity.OperationEntity;
import tp.appliSpring.bank.core.model.Operation;

@Service
@Transactional
public class ServiceOperationDirectImpl implements ServiceOperation {

    @Autowired
    private CompteRepository daoCompte;

    @Autowired
    private OperationRepository daoOperation;

    @Override
    public Operation create(Operation op, Long numCpt) {
        OperationEntity opEntity = GenericMapper.MAPPER.map(op,OperationEntity.class);
        CompteEntity compteEntity = daoCompte.findById(numCpt).get();
        opEntity.setCompte(compteEntity);
        OperationEntity savedOpEntity = daoOperation.save(opEntity);
        op.setNumero(savedOpEntity.getNumero());
        return op;
    }
}
