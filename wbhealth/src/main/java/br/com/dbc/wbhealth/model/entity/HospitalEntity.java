package br.com.dbc.wbhealth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "HOSPITAL")
public class HospitalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOSPITAL_SEQ")
    @SequenceGenerator(name = "HOSPITAL_SEQ", sequenceName = "seq_hospital", allocationSize = 1)
    @Column(name = "id_hospital")
    private Integer idHospital;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Atendimento> atendimentos;

}
