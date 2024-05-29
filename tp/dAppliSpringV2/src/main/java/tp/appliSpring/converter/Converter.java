package tp.appliSpring.converter;

import java.util.List;

import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.dto.CompteDto;

public interface Converter {
	
	public CompteDto compteToCompteDto(Compte c);
	public List<CompteDto> listCompteToListCompteDto(List<Compte> lc);
	public Compte compteDtoToCompte(CompteDto dto);
	
    //..
}
