package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.model.dto.relatorio.RelatorioLucro;
import br.com.dbc.wbhealth.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor

@Service
public class AtendimentoServiceRelatorio {

    private final AtendimentoRepository atendimentoRepository;

    public Page<RelatorioLucro> getLucroByData(LocalDate inicio, LocalDate fim, Pageable paginacao) {
        return atendimentoRepository.getLucroByData(inicio, fim, paginacao);
    }

    public Page<RelatorioLucro> findLucroAteAgora(Pageable paginacao) {
        return atendimentoRepository.getLucroAteOMomento(LocalDate.now(), paginacao);
    }
}
