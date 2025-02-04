package tp.appliSpring.core.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
//@NamedQuery(name = "Compte.findWithOperations" , query="...")
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//pour récupérer pk auto-incrémentée par base h2 ou autre
    private Long numero;
    
    @Column(length=64,name="label")
    private String label;
    
    private Double solde;
    
  
  //  private List<Operation> operations = new ArrayList<>(); //+get/set
    
  //+get/set , constructeur , toString()
    
	@Override
	public String toString() {
		return "Compte [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
	}


	public Compte(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}


	public Compte() {
		super();
	}
	


    //+get/set , constructeur , toString()


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
	
	
}