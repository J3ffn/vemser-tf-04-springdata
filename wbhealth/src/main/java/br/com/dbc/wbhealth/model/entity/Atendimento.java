package br.com.dbc.wbhealth.model.entity;

import br.com.dbc.wbhealth.model.enumarator.TipoDeAtendimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Atendimento {

    private Integer idAtendimento;

    private Integer idHospital;

    private Integer idPaciente;

    private Integer idMedico;

    private LocalDate dataAtendimento;

    private String laudo;

    private TipoDeAtendimento tipoDeAtendimento;

    private Double valorDoAtendimento;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.idAtendimento);
        sb.append("\nData: ").append(this.dataAtendimento.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        sb.append("\nLaudo: ").append(this.laudo);
        sb.append("\nTipo de Atendimento: ").append(this.tipoDeAtendimento);
        sb.append("\nValor: R$").append(String.format("%.2f", this.valorDoAtendimento));
        return sb.toString();
    }
}
