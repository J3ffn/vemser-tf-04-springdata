package br.com.dbc.wbhealth.model.entity;

import br.com.dbc.wbhealth.model.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Medico extends PessoaEntity implements Pagamento {

    private Integer idHospital;
    private Integer idMedico;
    private String crm;

    public Medico(String nome, String cep, String dataNascimento, String cpf, Double salarioMensal,
                   Integer idHospital, String crm, String email) {
        super(nome, cep, dataNascimento, cpf, salarioMensal, email);
        this.idHospital = idHospital;
        this.crm = crm;
    }

    // Getters & Setters
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

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
