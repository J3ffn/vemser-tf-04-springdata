package br.com.dbc.wbhealth.model.dto.relatorio;

import br.com.dbc.wbhealth.model.enumarator.TipoDeAtendimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor

public class RelatorioLucro {

    private TipoDeAtendimento tipoDeAtendimento;

    private Double lucro;

    public RelatorioLucro(TipoDeAtendimento tipoDeAtendimento, Double lucro) {
        this.tipoDeAtendimento = tipoDeAtendimento;
        this.lucro = lucro;
    }
}
