package puter.balek.ksuSrikandi.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReminderDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nominalPinjaman")
    private String nominalPinjaman;

    @JsonProperty("deadline")
    private String deadline;

    @JsonProperty("kreditAktif")
    private String kreditAktif;

    @JsonProperty("status")
    private String status;

    @JsonProperty("denda")
    private String denda;
}
