package br.com.dbc.wbhealth.controller;

import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoAtendimentoDTO;
import br.com.dbc.wbhealth.service.MedicoRelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/medico")
public class MedicoRelatorioController {

    private final MedicoRelatorioService medicoRelatorioService;

    @GetMapping("/{medicoId}/atendimentos")
    public ResponseEntity<List<MedicoAtendimentoDTO>> generateMedicoAtendimento(
            @PathVariable Integer medicoId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) throws EntityNotFound {
        List<MedicoAtendimentoDTO> report = medicoRelatorioService.generateMedicoAtendimento(medicoId, dataInicio, dataFim);
        return ResponseEntity.ok(report);
    }

}
