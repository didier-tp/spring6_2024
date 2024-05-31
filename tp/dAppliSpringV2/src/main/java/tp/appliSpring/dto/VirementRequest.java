package tp.appliSpring.dto;

public class VirementRequest {
	//pour request et response
	protected Double montant;
	protected Long numCptDeb;
	protected Long numCptCred;
	
	
	@Override
	public String toString() {
		return "Virement [montant=" + montant + ", numCptDeb=" + numCptDeb + ", numCptCred=" + numCptCred + "]";
	}

	
	
	
	public VirementRequest(Double montant, Long numCptDeb, Long numCptCred) {
		super();
		this.montant = montant;
		this.numCptDeb = numCptDeb;
		this.numCptCred = numCptCred;
	
	}
	
	
	public VirementRequest() {
		this(null,null,null);
	}


	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Long getNumCptDeb() {
		return numCptDeb;
	}

	public void setNumCptDeb(Long numCptDeb) {
		this.numCptDeb = numCptDeb;
	}

	public Long getNumCptCred() {
		return numCptCred;
	}

	public void setNumCptCred(Long numCptCred) {
		this.numCptCred = numCptCred;
	}


	
}
