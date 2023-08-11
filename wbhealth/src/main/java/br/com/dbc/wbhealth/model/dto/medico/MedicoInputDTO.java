package br.com.dbc.wbhealth.model.dto.medico;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoInputDTO {
    @NotBlank
    @Schema(description = "Nome do medico", required = true)
    private String nome;
    @Schema(description = "Cep do medico", example = "12345678", required = true)
    @NotBlank
    @Size(min = 8, max = 8)
    private String cep;
    @Schema(description = "Data de nascimento do medico", example = "22-04-1995", required = true)
    @NotNull
    @PastOrPresent
    private LocalDate dataNascimento;
    @Schema(description = "CPF do medico", example = "28283051040", required = true)
    @CPF
    private String cpf;
    @Schema(description = "Salario do medico", example = "9000.00", required = true)
    @PositiveOrZero
    private Double salarioMensal;
    @Schema(description = "Id do hospital onde o medico trabalha", example = "1", required = true)
    @Positive
    private Integer idHospital;
    @Schema(description = "CRM do medico", example = "AM-7654321/82", required = true)
    @NotBlank
    @Size(min = 13, max = 13)
    private String crm;
    @NotBlank
    @Email
    private String email;

}
