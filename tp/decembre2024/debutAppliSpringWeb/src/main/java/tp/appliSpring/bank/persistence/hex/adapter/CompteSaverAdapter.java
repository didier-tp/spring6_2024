package tp.appliSpring.bank.persistence.hex.adapter;

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

    @Autowired
    public CompteSaverAdapter(CompteRepository daoCompte) {
        super(Compte.class,CompteEntity.class,daoCompte);
        this.daoCompte=daoCompte;
    }


}

