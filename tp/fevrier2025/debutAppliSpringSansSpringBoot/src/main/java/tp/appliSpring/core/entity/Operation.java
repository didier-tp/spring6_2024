package tp.appliSpring.core.entity;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;
    
    private String label;
    
    private Double montant;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOp;
    
    @ManyToOne
    @JoinColumn(name = "num_compte")
    private Compte compte; //+get/set
    

	public Operation() {
		super();
	}

	public Operation(Long numero, String label, Double montant, Date dateOp) {
		super();
		this.numero = numero;
		this.label = label;
		this.montant = montant;
		this.dateOp = dateOp;
	}

	@Override
	public String toString() {
		return "Operation [numero=" + numero + ", label=" + label + ", montant=" + montant + ", dateOp=" + dateOp + "]";
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

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

    
    
}