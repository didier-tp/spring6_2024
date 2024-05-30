package tp.appliSpring.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.dto.CompteDto;

//@Component
public class MyConverterImpl implements Converter {

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

	@Override
	public List<CompteDto> listCompteToListCompteDto(List<Compte> lc) {
		   return lc.stream()
		   .map((c)->compteToCompteDto(c))
		   //.collect(Collectors.toList());
		   .toList(); //depuis java 17
	}

}
