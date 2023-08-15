package br.com.dbc.wbhealth.model.entity;

import br.com.dbc.wbhealth.model.enumarator.TipoDeAtendimento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "ATENDIMENTO")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATENDIMENTO_SEQ")
    @SequenceGenerator(name = "ATENDIMENTO_SEQ", sequenceName = "SEQ_ATENDIMENTO", allocationSize = 1)
    @Column(name = "id_atendimento")
    private Long idAtendimento;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_hospital", referencedColumnName = "id_hospital")
//    private Hospital idHospital;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_hospital", referencedColumnName = "id_hospital")
    private HospitalEntity hospitalEntity;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
//    private Paciente idPaciente;
    @Column(name = "id_paciente")
    private Long idPaciente;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_medico", referencedColumnName = "id_medico", table = "medico")
//    private Medico idMedico;

    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "data_atendimento")
    private LocalDate dataAtendimento;

    @Column(name = "laudo")
    private String laudo;

    @Column(name = "tipo_de_atendimento")
    @Enumerated(EnumType.ORDINAL) // Tornar cardinal depois
    private TipoDeAtendimento tipoDeAtendimento;

    @Column(name = "valor_do_atendimento")
    private Double valorDoAtendimento;


}
