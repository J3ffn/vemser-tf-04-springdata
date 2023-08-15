package br.com.dbc.wbhealth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "HOSPITAL")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HospitalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOSPITAL_SEQ")
    @SequenceGenerator(name = "HOSPITAL_SEQ", sequenceName = "seq_hospital", allocationSize = 1)
    @Column(name = "id_hospital")
    private Integer idHospital;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "hospitalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Atendimento> atendimentos = new HashSet<>();

}
