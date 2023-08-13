package br.com.dbc.wbhealth.model.entity;

import br.com.dbc.wbhealth.model.enumarator.TipoDeAtendimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATENDIMENTO_SEQ")
    @SequenceGenerator(name = "ATENDIMENTO_SEQ", sequenceName = "SEQ_ATENDIMENTO", allocationSize = 1)
    @Column(name = "id_atendimento")
    private Long idAtendimento;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_hospital", referencedColumnName = "id_hospital")
//    private Hospital idHospital;
    @Column(name = "id_hospital")
    private Long idHospital;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.idAtendimento);
        sb.append("\nData: ").append(this.dataAtendimento.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        sb.append("\nLaudo: ").append(this.laudo);
        sb.append("\nTipo de Atendimento: ").append(this.tipoDeAtendimento);
        sb.append("\nValor: R$").append(String.format("%.2f", this.valorDoAtendimento));
        return sb.toString();
    }
}
