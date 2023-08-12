package br.com.dbc.wbhealth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Entity(name = "PESSOA")
public abstract class PessoaEntity {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PESSOA_SEQ")
    @SequenceGenerator(name = "PESSOA_SEQ", sequenceName = "seq_pessoa", allocationSize = 1)
    @Column(name = "id_pessoa")*/
    private Integer idPessoa;

    //@Column(name = "nome")
    private String nome;

    //@Column(name = "cep")
    private String cep;

    //@Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    //@Column(name = "cpf")
    private String cpf;

    //@Column(name = "salario_mensal")
    private Double salarioMensal;

    //@Column(name = "email")
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
