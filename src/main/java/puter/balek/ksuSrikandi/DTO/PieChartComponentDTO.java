package puter.balek.ksuSrikandi.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PieChartComponentDTO {

    private String id;
    private String label;
    private Long value;
    private String color;

    public PieChartComponentDTO() {
    }
}
