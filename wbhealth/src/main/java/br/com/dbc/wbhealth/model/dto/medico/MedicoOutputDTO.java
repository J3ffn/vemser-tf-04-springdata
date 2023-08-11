package br.com.dbc.wbhealth.model.dto.medico;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoOutputDTO extends MedicoInputDTO {
    @Schema(description = "Id de pessoa do medico", example = "1", required = true)
    @Positive
    private Integer idPessoa;
    @Schema(description = "Id de medico", example = "1", required = true)
    @Positive
    private Integer idMedico;
}
