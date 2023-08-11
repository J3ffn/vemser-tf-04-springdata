package br.com.dbc.wbhealth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Pessoa {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Integer idPessoa;

    private String nome;

    private String cep;

    private LocalDate dataNascimento;

    private String cpf;

    private Double salarioMensal;

    private String email;

    public Pessoa(String nome, String cep, String dataNascimento, String cpf, Double salarioMensal, String email) {
        this.nome = nome;
        this.cep = cep;
        this.dataNascimento = LocalDate.parse(dataNascimento, FORMAT);
        this.cpf = cpf;
        this.salarioMensal = salarioMensal;
        this.email = email;
    }

//    public Pessoa(String nome, String cep, String dataNascimento, String cpf, Double salarioMensal) {
//        this.nome = nome;
//        this.cep = cep;
//        if (dataNascimento == ""){
//            this.dataNascimento = null;
//        }else {
//            this.dataNascimento = LocalDate.parse(dataNascimento, fmt);
//        }
//        this.cpf = cpf;
//        this.salarioMensal = salarioMensal;
//    }

    // Getters & Setters
//    public Integer getIdPessoa() {
//        return idPessoa;
//    }
//
//    public void setIdPessoa(Integer idPessoa) {
//        this.idPessoa = idPessoa;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public String getCep() {
//        return cep;
//    }
//
//    public void setCep(String cep) {
//        this.cep = cep;
//    }
//
//    public LocalDate getDataNascimento() {
//        return dataNascimento;
//    }
//
//    public void setDataNascimento(LocalDate dataNascimento) {
//        this.dataNascimento = dataNascimento;
//    }
//
//    public String getCpf() {
//        return cpf;
//    }
//
//    public void setCpf(String cpf) {
//        this.cpf = cpf;
//    }
//
//    public Double getSalarioMensal() {
//        return salarioMensal;
//    }
//
//    public void setSalarioMensal(Double salarioMensal) {
//        this.salarioMensal = salarioMensal;
//    }
}
