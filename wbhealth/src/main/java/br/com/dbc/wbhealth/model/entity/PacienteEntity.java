package br.com.dbc.wbhealth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PACIENTE")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PACIENTE_SEQ")
    @SequenceGenerator(name = "PACIENTE_SEQ", sequenceName = "seq_paciente", allocationSize = 1)
    @Column(name = "id_paciente")
    private Integer idPaciente;

    @Column(name = "id_hospital")
    private Integer idHospital;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
    private PessoaEntity pessoa;

}
