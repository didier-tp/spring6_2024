package tp.appliSpring.converter;

import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.dto.CompteDto;

public interface Converter {
	
	public CompteDto compteToCompteDto(Compte c);
	public Compte compteDtoToCompte(CompteDto dto);
	
    //..
}
