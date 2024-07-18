package tp.appliSpringRennes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompteDto {

    private Long numero;

    private String label;

    private Double solde;
}
