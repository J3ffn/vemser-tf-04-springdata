package br.com.dbc.wbhealth.model.entity;

import br.com.dbc.wbhealth.model.Pagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MEDICO")
public class MedicoEntity implements Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDICO_SEQ")
    @SequenceGenerator(name = "MEDICO_SEQ", sequenceName = "seq_medico", allocationSize = 1)
    @Column(name = "id_medico")
    private Integer idMedico;

    @Column(name = "id_hospital")
    private Integer idHospital;

    @Column(name = "crm")
    private String crm;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
    private PessoaEntity pessoa;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_hospital", referencedColumnName = "id_Hospital")
    private HospitalEntity hospital;

    @OneToMany(mappedBy = "medicoEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AtendimentoEntity> atendimentos;

    @Override
    public Double calcularSalarioMensal() {
        Double taxaInss = 0.14;
        return pessoa.getSalarioMensal() - pessoa.getSalarioMensal() * taxaInss;
    }

}
