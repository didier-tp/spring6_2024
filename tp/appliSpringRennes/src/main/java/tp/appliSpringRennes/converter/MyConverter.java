package tp.appliSpringRennes.converter;

import org.mapstruct.Mapper;

import tp.appliSpringRennes.dto.CompteDto;
import tp.appliSpringRennes.entity.Compte;

import java.util.List;


@Mapper(componentModel = "spring") //for @Autowired
public interface MyConverter {

    public CompteDto compteToCompteDto(Compte c);

    public Compte compteDtoToCompte(CompteDto dto);

    public List<CompteDto> compteListToCompteDtoList(List<Compte> listeComptes);
}
