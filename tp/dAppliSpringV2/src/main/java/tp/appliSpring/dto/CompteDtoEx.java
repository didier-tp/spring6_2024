package tp.appliSpring.dto;

import java.util.ArrayList;
import java.util.List;

public class CompteDtoEx extends CompteDto {

	private List<OperationDto> operations; 
    //+get/set, ...

	public CompteDtoEx(Long numero, String label, Double solde, List<OperationDto> operations) {
		super(numero, label, solde);
		this.operations = operations;
	}
	
	public CompteDtoEx(Long numero, String label, Double solde) {
		super(numero, label, solde);
		this.operations = new ArrayList<OperationDto>();
	}

	public List<OperationDto> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationDto> operations) {
		this.operations = operations;
	}

	@Override
	public String toString() {
		return "CompteDtoEx [operations=" + operations + ", toString()=" + super.toString() + "]";
	}
	
	

}
