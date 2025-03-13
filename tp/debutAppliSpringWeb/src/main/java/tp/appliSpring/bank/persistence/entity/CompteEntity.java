package tp.appliSpring.bank.persistence.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="COMPTE")
@NamedQuery(name = "CompteEntity.findWithOperations" ,
           query="SELECT cpt FROM CompteEntity cpt LEFT JOIN FETCH cpt.operations WHERE cpt.numero = ?1")
public class CompteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name="numero")
    private Long numero;
    
    @Column(name="label" , length = 64)
    private String label;
    
    private Double solde;
    
    @OneToMany(mappedBy = "compte" , fetch = FetchType.LAZY ,cascade = CascadeType.REMOVE)
    //@JsonIgnore
    private List<OperationEntity> operations = new ArrayList<>(); //+get/set

	@ManyToMany(mappedBy = "comptes" , cascade = CascadeType.DETACH) // coté secondaire avec mappedBy="nomJavaRelationInverse"
	private List<ClientEntity> clients = new ArrayList<>();

  //+get/set , constructeur , toString()
    
	@Override
	public String toString() {
		return "Compte [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
	}


	public CompteEntity(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}


	public CompteEntity() {
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


	public List<OperationEntity> getOperations() {
		return operations;
	}


	public void setOperations(List<OperationEntity> operations) {
		this.operations = operations;
	}

	public List<ClientEntity> getClients() {
		return clients;
	}

	public void setClients(List<ClientEntity> clients) {
		this.clients = clients;
	}
	
}