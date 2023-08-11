//package br.com.dbc.wbhealth.model.entity;
//
//import br.com.dbc.wbhealth.model.Pagamento;
//
//public class Funcionario extends Pessoa implements Pagamento {
//    private Integer idFuncionario;
//    private Integer idHospital;
//    private Integer idPessoa;
//
//    public Funcionario() {}
//
//    public Funcionario(String nome, String cep, String dataNascimento, String cpf, Double salarioMensal, Integer idHospital) {
//        super(nome, cep, dataNascimento, cpf, salarioMensal);
//        this.idHospital = idHospital;
//    }
//
//    // Getters & Setters
//
//    public Integer getIdFuncionario() {
//        return idFuncionario;
//    }
//
//    public void setIdFuncionario(Integer idFuncionario) {
//        this.idFuncionario = idFuncionario;
//    }
//
//    public Integer getIdHospital() {
//        return idHospital;
//    }
//
//    public void setIdHospital(Integer idHospital) {
//        this.idHospital = idHospital;
//    }
//
//    @Override
//    public Integer getIdPessoa() {
//        return idPessoa;
//    }
//
//    @Override
//    public void setIdPessoa(Integer idPessoa) {
//        this.idPessoa = idPessoa;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Id: ").append(this.getIdFuncionario());
//        sb.append("\nFuncionário: ").append(this.getNome());
//        sb.append("\nCPF: ").append(this.getCpf());
//        sb.append("\nSalário Mensal: R$").append(String.format("%.2f", this.getSalarioMensal()));
//        return sb.toString();
//    }
//
//    @Override
//    public Double calcularSalarioMensal() {
//        Double taxaInss = 0.075;
//        if (getSalarioMensal() > 1320.0 && getSalarioMensal() <= 2571.29) {
//            taxaInss = 0.09;
//        }
//        else if (getSalarioMensal() > 2571.29 && getSalarioMensal() <= 3856.94) {
//            taxaInss = 0.12;
//        }
//        else if (getSalarioMensal() > 3856.94 && getSalarioMensal() <= 7507.49) {
//            taxaInss = 0.14;
//        }
//
//        return getSalarioMensal() - getSalarioMensal() * taxaInss;
//    }
//
//
//}