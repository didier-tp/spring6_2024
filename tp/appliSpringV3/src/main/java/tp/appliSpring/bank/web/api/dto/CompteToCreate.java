package tp.appliSpring.bank.web.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import tp.appliSpring.bank.core.model.Compte;

@Schema(description = "data values of new account to be created")
public class CompteToCreate extends Compte {
    public CompteToCreate(String label,Double solde){
        super(null,label,solde);
    }

    public CompteToCreate(){
        this("myBankAccount",0.0);
    }

    @Override
    //@Schema(description = "unknown account number before creation (should be null)")
    @Schema(hidden = true)  //ou bien @JsonIgnore
    public Long getNumero() {
        return super.getNumero();
    }

    @Schema(description = "account label" , defaultValue = "myBankAccount")
    public String getLabel(){
        return super.getLabel();
    }
}
