package tp.springwebsocket.model;

import lombok.*;

//classe représentant une demande de dessin d'une nouvelle ligne
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Line {
    @NonNull
    private Integer x1;

    @NonNull
    private Integer y1;

    @NonNull
    private Integer x2;

    @NonNull
    private Integer y2;

    private String color="black";//ex: "red" , "green" , ...
}
