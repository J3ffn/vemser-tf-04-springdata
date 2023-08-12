package br.com.dbc.wbhealth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class PessoaEntity {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Integer idPessoa;

    private String nome;

    private String cep;

    private LocalDate dataNascimento;

    private String cpf;

    private Double salarioMensal;

    private String email;

    public PessoaEntity(String nome, String cep, String dataNascimento, String cpf, Double salarioMensal, String email) {
        this.nome = nome;
        this.cep = cep;
        this.dataNascimento = LocalDate.parse(dataNascimento, FORMAT);
        this.cpf = cpf;
        this.salarioMensal = salarioMensal;
        this.email = email;
    }

}
