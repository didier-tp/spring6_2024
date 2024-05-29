package tp.appliSpring.converter;

import org.springframework.stereotype.Component;

import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.dto.CompteDto;

@Component
public class ConverterImpl implements Converter {

	@Override
	public CompteDto compteToCompteDto(Compte c) {
		CompteDto compteDto = new CompteDto();
		
		compteDto.setNumero(c.getNumero());
		compteDto.setLabel(c.getLabel());
		compteDto.setSolde(c.getSolde());
		//BeanUtils.copyProperties(c, compteDto);
		
		return compteDto;
	}

	@Override
	public Compte compteDtoToCompte(CompteDto compteDto) {
		Compte c =  new Compte();
		
		c.setNumero(compteDto.getNumero());
		c.setLabel(compteDto.getLabel());
		c.setSolde(compteDto.getSolde());
		
		return c;
	}

}
