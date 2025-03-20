package tp.appliSpring.bank.persistence.hex.adapter;

import org.springframework.transaction.annotation.Transactional;
import tp.appliSpring.bank.persistence.entity.ClientEntity;
import tp.appliSpring.bank.persistence.repository.ClientRepository;
import tp.appliSpring.generic.hex.persistence.GenericSaverAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.appliSpring.bank.core.hex.spi.CompteSaver;
import tp.appliSpring.bank.persistence.repository.CompteRepository;
import tp.appliSpring.bank.persistence.entity.CompteEntity;
import tp.appliSpring.bank.core.model.Compte;



@Component
public class CompteSaverAdapter
        extends GenericSaverAdapter<Compte, CompteEntity,Long>
        implements CompteSaver {


    private CompteRepository daoCompte;
    private ClientRepository daoClient;

    @Autowired
    public CompteSaverAdapter(CompteRepository daoCompte,ClientRepository daoClient) {
        super(Compte.class,CompteEntity.class,daoCompte);
        this.daoCompte=daoCompte;
        this.daoClient=daoClient;
    }

    @Override
    @Transactional
    public void setAccountOwnerNumber(long accountNumber, long customerNumber) {
        ClientEntity clientEntity = daoClient.findById(customerNumber).get();
        CompteEntity compteEntity = daoCompte.findById(accountNumber).get();
        clientEntity.getComptes().add(compteEntity);
        daoClient.save(clientEntity);
    }
}

