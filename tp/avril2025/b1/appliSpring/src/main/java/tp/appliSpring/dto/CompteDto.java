package tp.appliSpring.dto;

/*
 * DTO = Data Transfert Object
 * objet de données transféré à travers le réseau HTTP ou bien entre couche logicielle
 */
public class CompteDto {
	private Long numero;
	
	private String label;
	
	private Double solde;
	
	
	

	public CompteDto() {
		super();
	}

	public CompteDto(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}

	@Override
	public String toString() {
		return "CompteDto [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}
	
	//+get/set, contructeur, toString
	
}
