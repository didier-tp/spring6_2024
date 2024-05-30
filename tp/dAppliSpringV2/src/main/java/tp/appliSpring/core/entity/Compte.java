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
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="COMPTE")
@NamedQuery(name = "Compte.findWithOperations" , 
           query="SELECT cpt FROM Compte cpt LEFT JOIN FETCH cpt.operations WHERE cpt.numero = ?1")
@Getter @Setter 
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name="numero")
    private Long numero;
    
    @Column(name="label" , length = 64)
    private String label;
    
    private Double solde;
    
    @OneToMany(mappedBy = "compte" , fetch = FetchType.LAZY )
    //@JsonIgnore
    private List<Operation> operations = new ArrayList<>(); //+get/set
    
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
	

}