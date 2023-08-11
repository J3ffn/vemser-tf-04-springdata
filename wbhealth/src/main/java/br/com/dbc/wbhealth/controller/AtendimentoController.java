package br.com.dbc.wbhealth.controller;

import br.com.dbc.wbhealth.documentation.AtendimentoControllerDoc;
import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoInputDTO;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoOutputDTO;
import br.com.dbc.wbhealth.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/atendimento")
public class AtendimentoController implements AtendimentoControllerDoc {

    private AtendimentoService atendimentoService;

    @Autowired
    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoOutputDTO>> findAll() throws BancoDeDadosException {
        return ResponseEntity.status(HttpStatus.OK).body(atendimentoService.findAll());
    }

    @PostMapping
    public ResponseEntity<AtendimentoOutputDTO> save(@Valid @RequestBody AtendimentoInputDTO novoAtendimento) throws BancoDeDadosException, EntityNotFound, MessagingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoService.save(novoAtendimento));
    }

    @GetMapping("/{idAtendimento}")
    public ResponseEntity<AtendimentoOutputDTO> buscarAtendimentoPeloId(@Positive(message = "Deve ser positivo") @PathVariable Integer idAtendimento) throws BancoDeDadosException, EntityNotFound {
        return ResponseEntity.status(HttpStatus.OK).body(atendimentoService.findById(idAtendimento));
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<AtendimentoOutputDTO>> bucarAtendimentoPeloIdUsuario(@Positive(message = "Deve ser positivo") @PathVariable Integer idPaciente) throws BancoDeDadosException {
        return ResponseEntity.status(HttpStatus.OK).body(atendimentoService.bucarAtendimentoPeloIdUsuario(idPaciente));
    }

    @PutMapping("/{idAtendimento}")
    public ResponseEntity<AtendimentoOutputDTO> alterarPeloId(@Positive(message = "Deve ser positivo") @PathVariable Integer idAtendimento,
                                                              @Valid @RequestBody AtendimentoInputDTO atendimento) throws BancoDeDadosException, EntityNotFound, MessagingException {
        return ResponseEntity.status(HttpStatus.OK).body(atendimentoService.update(idAtendimento, atendimento));
    }

    @DeleteMapping("/{idAtendimento}")
    public ResponseEntity<Void> deletarAtendimento(@Positive(message = "Deve ser positivo") @PathVariable Integer idAtendimento) throws EntityNotFound {
        atendimentoService.deletarPeloId(idAtendimento);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
