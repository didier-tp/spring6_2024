package tp.appliSpring.dto;

public class VirementResponse  extends VirementRequest{

	private Boolean status;
	private String message;//ex: "echec virement" ou bien "virement bien effectué"
	

	
	public VirementResponse(Double montant, Long numCptDeb, Long numCptCred, Boolean status, String message) {
		super(montant,numCptDeb,numCptCred);
		this.status = status;
		this.message = message;
	}
	
	public VirementResponse(Double montant, Long numCptDeb, Long numCptCred) {
		this(montant,numCptDeb,numCptCred,null,null);
	}
	
	public VirementResponse() {
		this(null,null,null);
	}



	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
