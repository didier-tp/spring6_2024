package tp.appliSpring.bank.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="client")
//@NamedQuery(name="ClientEntity.findAll",query="SELECT c FROM ClientEntity c")
@NamedQuery(name="ClientEntity.findWithAccountById",
            query="SELECT c FROM ClientEntity c LEFT JOIN FETCH c.comptes WHERE c.numero = ?1")

public class ClientEntity {
	
	//un client aura souvent plusieurs comptes
	//bien que rare , un compte peut être associé à plusieurs client (ex: co-propriété)
	//many-to-many , avec coté principal "client" et coté secondaire "compte"
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "client_compte" ,
	    joinColumns = { @JoinColumn(name="numClient")} ,
	    inverseJoinColumns = { @JoinColumn(name="numCompte")}
	)
	@JsonIgnore //pour ignorer .comptes lorsque le client java sera transformé en client json
	            //MAIS c'est beaucoup moins bien que les DTO
	private List<CompteEntity> comptes=new ArrayList<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	private String prenom;
	private String nom;
	private String adresse;
	private String email;
	private String password; //mot de passe (crypté via bcrypt)
	//...
	

	public ClientEntity(Long numero, String prenom, String nom, String adresse, String email, String password) {
		super();
		this.numero = numero;
		this.prenom = prenom;
		this.nom = nom;
		this.adresse = adresse;
		this.email=email;
		this.password=password;
	}

	public ClientEntity(Long numero, String prenom, String nom, String adresse, String email) {
		this(numero,prenom,nom,adresse,email,null);
	}
	
	public ClientEntity() {
		this(null,null,null,null,null);
	}
	

	@Override
	public String toString() {
		return "Client [numero=" + numero + ", prenom=" + prenom + ", nom=" + nom + ", adresse=" + adresse + ", email="
				+ email + "]";
	}

	public List<CompteEntity> getComptes() {
		return comptes;
	}

	public void setComptes(List<CompteEntity> comptes) {
		this.comptes = comptes;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {	return password;	}

	public void setPassword(String password) {		this.password = password;	}
}
