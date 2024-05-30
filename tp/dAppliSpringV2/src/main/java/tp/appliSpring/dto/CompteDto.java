package tp.appliSpring.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "CompteDto (infos essentielles sur compte bancaire)")
@Getter @Setter @ToString @NoArgsConstructor
public class CompteDto {
	
	@Schema(description = "numero du compte")
    private Long numero;
    
    @Length(min=2, max=30, message = "Nom trop long ou trop court")
    @Schema(description = "libelle du compte", defaultValue = "compteQuiVaBien")
	private String label;
    
    @Min(-999)
    @Schema(description = "solde du compte", defaultValue = "100")
	private Double solde;

	
	
	public CompteDto(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}
	

	

}
