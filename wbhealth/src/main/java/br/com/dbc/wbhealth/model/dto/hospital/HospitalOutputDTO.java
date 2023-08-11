package br.com.dbc.wbhealth.model.dto.hospital;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalOutputDTO extends HospitalInputDTO {

    @Positive
    @NotNull
    @Schema(description = "Id do Hospital", example = "6", required = true)
    private Integer idHospital;
}
