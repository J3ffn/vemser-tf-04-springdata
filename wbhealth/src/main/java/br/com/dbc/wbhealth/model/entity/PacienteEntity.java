package br.com.dbc.wbhealth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PACIENTE")
public class PacienteEntity extends PessoaEntity{
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PACIENTE_SEQ")
    @SequenceGenerator(name = "PACIENTE_SEQ", sequenceName = "seq_paciente", allocationSize = 1)
    @Column(name = "id_paciente")
    private Integer idPaciente;

    @Column(name = "id_hospital")
    private Integer idHospital;

    @Column(name = "id_pessoa")
    private Integer idPessoa;

}
