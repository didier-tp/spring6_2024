package tp.appliSpring.converter;

import java.util.List;

import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;
import tp.appliSpring.dto.CompteDto;
import tp.appliSpring.dto.CompteDtoEx;
import tp.appliSpring.dto.OperationDto;

//@Mapper(componentModel = "spring")
public interface Converter {
	
	//@Mapping(target="number", source="numero") //si CompteDto.number
	public CompteDto compteToCompteDto(Compte source);
	public OperationDto operationToOperationDto(Operation source);
	public CompteDtoEx compteToCompteDtoEx(Compte source);
	
	
	public List<CompteDto> listCompteToListCompteDto(List<Compte> lc);
	public Compte compteDtoToCompte(CompteDto dto);
	
	
    //..
}
