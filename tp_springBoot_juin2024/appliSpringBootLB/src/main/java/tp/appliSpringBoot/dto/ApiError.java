package tp.appliSpringBoot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@NoArgsConstructor
public class ApiError {
    private String message;
    private HttpStatus status;

    //...
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, Exception ex) {
        this.status = status;
        this.message = ex.getMessage();
    }
}
