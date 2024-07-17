package tp.appliSpringRennes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private HttpStatus status;

    private String message;

    private LocalDateTime timeStamp;

    public ApiError(HttpStatus status, String message){
        this.status=status;
        this.message=message;
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, Exception ex){
        this(status,ex.getMessage());
    }
}
