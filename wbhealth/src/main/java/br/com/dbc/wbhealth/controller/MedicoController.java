package br.com.dbc.wbhealth.controller;

import br.com.dbc.wbhealth.documentation.MedicoControllerDoc;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoInputDTO;
import br.com.dbc.wbhealth.model.dto.medico.MedicoOutputDTO;
import br.com.dbc.wbhealth.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/medico")
public class MedicoController implements MedicoControllerDoc {

    private final MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<MedicoOutputDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.findAll());
    }

    @GetMapping("{idMedico}")
    public ResponseEntity<MedicoOutputDTO> findById(@PathVariable @Positive Integer idMedico) throws EntityNotFound{
        MedicoOutputDTO medicoOutputDTO = medicoService.findById(idMedico);
        return ResponseEntity.status(HttpStatus.OK).body(medicoOutputDTO);
    }

    @PostMapping()
    public ResponseEntity<MedicoOutputDTO> save(@Valid @RequestBody MedicoInputDTO medicoInputDTO) {
        MedicoOutputDTO médicoCriado = medicoService.save(medicoInputDTO);
        return ResponseEntity.status(HttpStatus.OK).body(médicoCriado);
    }

    @PutMapping("{idMedico}")
    public ResponseEntity<MedicoOutputDTO> update(@PathVariable Integer idMedico,
                                                  @Valid @RequestBody MedicoInputDTO medicoInputDTO) throws EntityNotFound {
        MedicoOutputDTO medicoAtualizado = medicoService.update(idMedico, medicoInputDTO);
        return ResponseEntity.status(HttpStatus.OK).body(medicoAtualizado);
    }

    @DeleteMapping("{idMedico}")
    public ResponseEntity<Void> deleteById(@PathVariable @Positive Integer idMedico) throws EntityNotFound {
        medicoService.delete(idMedico);
        return ResponseEntity.ok().build();
    }
}
