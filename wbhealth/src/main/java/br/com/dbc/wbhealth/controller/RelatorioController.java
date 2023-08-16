package br.com.dbc.wbhealth.controller;

import br.com.dbc.wbhealth.model.dto.relatorio.RelatorioLucro;
import br.com.dbc.wbhealth.model.dto.relatorio.RelatorioOutput;
import br.com.dbc.wbhealth.service.AtendimentoService;
import br.com.dbc.wbhealth.service.AtendimentoServiceRelatorio;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    private final AtendimentoServiceRelatorio atendimentoService;

    @GetMapping("/lucro")
    public ResponseEntity<Page<RelatorioLucro>> relatorioLucro(@RequestParam Integer pagina, @RequestParam Integer quantidade) {
        Pageable paginacao = PageRequest.of(pagina, quantidade);
        return ResponseEntity.status(HttpStatus.OK).body(atendimentoService.findLucro(paginacao));
    }

    @GetMapping("/lucro/data")
    public ResponseEntity<Page<RelatorioLucro>> relatorioLucroPorData(@RequestParam Integer pagina, @RequestParam Integer quantidade, @RequestParam String dataInicio, @RequestParam String dataFim) {
        Pageable paginacao = PageRequest.of(pagina, quantidade);
        return ResponseEntity.status(HttpStatus.OK).body(atendimentoService.getLucroByData(LocalDate.parse(dataInicio), LocalDate.parse( dataFim), paginacao));
    }

}
