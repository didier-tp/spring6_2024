package tp.appliSpring.bank.persistence.hex.adapter;

import tp.appliSpring.generic.hex.persistence.GenericLoaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.appliSpring.generic.converter.GenericMapper;
import tp.appliSpring.bank.core.hex.spi.CompteLoader;
import tp.appliSpring.bank.persistence.repository.CompteRepository;
import tp.appliSpring.bank.persistence.entity.CompteEntity;
import tp.appliSpring.bank.core.model.Compte;

import java.util.List;
import java.util.Optional;

@Component
public class CompteLoaderAdapter
        extends GenericLoaderAdapter<Compte, CompteEntity,Long>
        implements CompteLoader {


    private CompteRepository daoCompte;

    @Autowired
    public CompteLoaderAdapter(CompteRepository daoCompte) {
        super(Compte.class,CompteEntity.class,daoCompte);
        this.daoCompte=daoCompte;
    }

    @Override
    public List<Compte> findBySoldeMini(double soldeMini) {
        List<CompteEntity> compteEntityList = daoCompte.findBySoldeGreaterThanEqual(soldeMini);
        return GenericMapper.MAPPER.map(compteEntityList,Compte.class);
    }

    @Override
    public Optional<Compte> loadById(Long id, String... wishedDetails) {
        if(wishedDetails.length==0)
            return super.loadById(id, wishedDetails);
        String details = wishedDetails[0];
        if (details.equals("withOperations")){
            CompteEntity ce = daoCompte.findWithOperations(id);
            return Optional.of(GenericMapper.MAPPER.map(ce,Compte.class));
        }else return Optional.empty();
    }

    @Override
    public List<Compte> findByCustomerNumber(long customerNum) {
        List<CompteEntity> compteEntityList = daoCompte.findByClientsNumero(customerNum);
        return GenericMapper.MAPPER.map(compteEntityList,Compte.class);
    }
}

