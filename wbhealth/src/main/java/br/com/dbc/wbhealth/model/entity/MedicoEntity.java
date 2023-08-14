package br.com.dbc.wbhealth.model.entity;

import br.com.dbc.wbhealth.model.Pagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MEDICO")
//@PrimaryKeyJoinColumn(name = "id_pessoa", foreignKey = @ForeignKey(name = "fk_medico_pessoa"))
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
    private PessoaEntity pessoa;

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.getIdMedico());
        sb.append("\nMédico: ").append(this.getNome());
        sb.append("\nCRM: ").append(this.getCrm());
        sb.append("\nSalário Mensal: R$").append(String.format("%.2f", this.getSalarioMensal()));
        return sb.toString();
    }*/

    @Override
    public Double calcularSalarioMensal() {
        Double taxaInss = 0.14;
        return taxaInss;
        //return getSalarioMensal() - getSalarioMensal() * taxaInss;
    }

}
