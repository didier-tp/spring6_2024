package tp.appliSpringRennes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CompteDto {

    @NonNull
    private Long numero;
    @NonNull
    private String label;
    @NonNull
    private Double solde;
}
