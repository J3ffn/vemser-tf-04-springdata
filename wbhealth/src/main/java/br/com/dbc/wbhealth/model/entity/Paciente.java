package br.com.dbc.wbhealth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente extends Pessoa {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Integer idPaciente;
    private Integer idHospital;

    public Paciente(String nome, String cep, String dataNacimento, String cpf,
                    Double salarioMensal, String email, Integer idHospital) {
        super(nome, cep, dataNacimento, cpf, salarioMensal, email);
        this.idHospital = idHospital;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.getIdPaciente());
        sb.append("\nPaciente: ").append(this.getNome());
        sb.append("\nCPF: ").append(this.getCpf());
        sb.append("\nData Nascimento: ").append(this.getDataNascimento().format(FORMAT));
        return sb.toString();
    }

}
