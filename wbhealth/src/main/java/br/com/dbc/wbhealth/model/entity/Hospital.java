package br.com.dbc.wbhealth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "HOSPITAL")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOSPITAL_SEQ")
    @SequenceGenerator(name = "HOSPITAL_SEQ", sequenceName = "seq_hospital", allocationSize = 1)
    @Column(name = "id_hospital")
    private Integer idHospital;

    @Column(name = "nome")
    private String nome;
}
