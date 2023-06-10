package puter.balek.ksuSrikandi.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusDTO {

    private String id;
    private String status;
    private String message;



    public UpdateStatusDTO() {
    }


}
