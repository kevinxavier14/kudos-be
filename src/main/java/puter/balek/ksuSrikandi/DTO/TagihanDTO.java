package puter.balek.ksuSrikandi.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagihanDTO {

    private Double tagihanBulanan;
    private Double penalty;
    private Double tagihanBulanSebelumnya;
    private Double totalTagihan;


    public TagihanDTO() {
    }


}
