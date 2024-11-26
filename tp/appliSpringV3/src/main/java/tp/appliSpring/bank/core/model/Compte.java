package tp.appliSpring.bank.core.model;

import tp.appliSpring.generic.model.WithId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString @NoArgsConstructor
@Getter @Setter
public class Compte implements WithId<Long> {
    private Long numero;

    private String label;

    private Double solde;

    public Compte(Long numero, String label, Double solde) {
        this.numero = numero;
        this.label = label;
        this.solde = solde;
    }

    @Override
    public Long extractId() {
        return this.numero;
    }
}
