package br.com.dbc.wbhealth.model.entity;

import br.com.dbc.wbhealth.model.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MEDICO")
@PrimaryKeyJoinColumn(name = "id_pessoa", foreignKey = @ForeignKey(name = "fk_medico_pessoa"))
public class MedicoEntity extends PessoaEntity implements Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDICO_SEQ")
    @SequenceGenerator(name = "MEDICO_SEQ", sequenceName = "seq_medico", allocationSize = 1)
    @Column(name = "id_medico")
    private Integer idMedico;

    @Column(name = "id_hospital")
    private Integer idHospital;


    @Column(name = "crm")
    private String crm;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.getIdMedico());
        sb.append("\nMédico: ").append(this.getNome());
        sb.append("\nCRM: ").append(this.getCrm());
        sb.append("\nSalário Mensal: R$").append(String.format("%.2f", this.getSalarioMensal()));
        return sb.toString();
    }

    @Override
    public Double calcularSalarioMensal() {
        Double taxaInss = 0.14;
        return getSalarioMensal() - getSalarioMensal() * taxaInss;
    }


}
