package tp.appliSpring.converter;

import java.util.List;

import org.mapstruct.Mapper;

import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.dto.CompteDto;

@Mapper(componentModel = "spring")
public interface Converter {
	
	//@Mapping(target="number", source="numero") //si CompteDto.number
	public CompteDto compteToCompteDto(Compte source);
	
	
	public List<CompteDto> listCompteToListCompteDto(List<Compte> lc);
	public Compte compteDtoToCompte(CompteDto dto);
	
    //..
}
