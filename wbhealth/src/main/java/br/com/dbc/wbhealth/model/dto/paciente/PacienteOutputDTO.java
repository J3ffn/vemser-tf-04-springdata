package br.com.dbc.wbhealth.model.dto.paciente;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteOutputDTO {

    @Schema(description = "Identificador de paciente; associado à pessoa")
    private Integer idPaciente;

    @Schema(description = "Identificador do hospital que fornece o atendimento")
    private Integer idHospital;

    @Schema(description = "Identificador da pessoa")
    private Integer idPessoa;

    @Schema(description = "Nome do paciente")
    private String nome;

    @Schema(description = "Número CEP do paciente")
    private String cep;

    @Schema(description = "Data de nascimento do paciente")
    private LocalDate dataNascimento;

    @Schema(description = "Número de CPF do paciente")
    private String cpf;

    @Schema(description = "Salário mensal do paciente")
    private Double salarioMensal;

    @Schema(description = "Email pessoal do paciente")
    private String email;
}
