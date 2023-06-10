package puter.balek.ksuSrikandi.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PengajuanPinjamanDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tanggalPengajuan")
    private String tanggalPengajuan;

    @JsonProperty("nominalPengajuan")
    private String nominalPengajuan;

    @JsonProperty("status")
    private String status;
}
