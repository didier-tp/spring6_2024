package tp.appliSpringRennes.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import tp.appliSpringRennes.dto.CompteDto;
import tp.appliSpringRennes.entity.Compte;

import java.util.List;

@Component
public class MyConverter {

    public CompteDto compteToCompteDto(Compte c){
        CompteDto dto = new CompteDto();
        BeanUtils.copyProperties(c,dto);
        return dto;
    }

    public List<CompteDto> compteListToCompteDtoList(List<Compte> listeComptes){
        return listeComptes.stream()
                .map( c -> this.compteToCompteDto(c))
                .toList();
    }
}
