package tp.appliSpring.dto;

import java.util.Date;

public class OperationDto {
	    private Long numero;
	    private String label;
	    private Double montant;
	    private Date dateOp;
	    
	    
	  
		public OperationDto(Long numero, String label, Double montant, Date dateOp) {
			super();
			this.numero = numero;
			this.label = label;
			this.montant = montant;
			this.dateOp = dateOp;
		}
		
		public OperationDto(Long numero, String label, Double montant) {
			this(numero, label, montant,new Date());
		}
		
		public OperationDto() {
			this(null, null, null);
		}
		
		@Override
		public String toString() {
			return "OperationDto [numero=" + numero + ", label=" + label + ", montant=" + montant + ", dateOp=" + dateOp
					+ "]";
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
		public Double getMontant() {
			return montant;
		}
		public void setMontant(Double montant) {
			this.montant = montant;
		}
		public Date getDateOp() {
			return dateOp;
		}
		public void setDateOp(Date dateOp) {
			this.dateOp = dateOp;
		}

	    
}
