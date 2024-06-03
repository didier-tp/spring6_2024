package tp.appliSpring.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;
import tp.appliSpring.dto.CompteDto;
import tp.appliSpring.dto.CompteDtoEx;
import tp.appliSpring.dto.OperationDto;

@Component
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

	@Override
	public CompteDtoEx compteToCompteDtoEx(Compte c) {
		CompteDtoEx compteDto = new CompteDtoEx(c.getNumero(),c.getLabel(),c.getSolde());
		ArrayList<OperationDto> opDtoList = new ArrayList<OperationDto>();
		for(Operation op : c.getOperations()) {
			opDtoList.add(operationToOperationDto(op));
		}
		compteDto.setOperations(opDtoList);
		return compteDto;
	}

	@Override
	public OperationDto operationToOperationDto(Operation source) {
		return new OperationDto(source.getNumero(),source.getLabel(),source.getMontant());
	}

}
