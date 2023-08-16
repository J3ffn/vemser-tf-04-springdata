package br.com.dbc.wbhealth.controller;

import br.com.dbc.wbhealth.documentation.PacienteControllerDoc;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteAtendimentosOutputDTO;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteInputDTO;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteOutputDTO;
import br.com.dbc.wbhealth.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/paciente")
public class PacienteController implements PacienteControllerDoc {
    private final PacienteService pacienteService;

    @Override
    @GetMapping
    public List<PacienteOutputDTO> findAll(@RequestParam @PositiveOrZero Integer pagina,
                                           @RequestParam @Positive Integer quantidadeRegistros) {
        return pacienteService.findAll(pagina, quantidadeRegistros);
    }

    @Override
    @GetMapping("/atendimentos")
    public List<PacienteAtendimentosOutputDTO> findAllAtendimentos(@RequestParam @PositiveOrZero Integer pagina,
                                                                   @RequestParam @Positive Integer quantidadeRegistros){
        return pacienteService.findAllAtendimentos(pagina, quantidadeRegistros);
    }

    @Override
    @GetMapping("/by-id")
    public ResponseEntity<PacienteOutputDTO> findById(@RequestParam("idPaciente") @Positive Integer idPaciente)
            throws EntityNotFound {
        PacienteOutputDTO pacienteEncontrado = pacienteService.findById(idPaciente);
        return new ResponseEntity<>(pacienteEncontrado, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<PacienteOutputDTO> save(@RequestBody @Valid PacienteInputDTO paciente) {
        PacienteOutputDTO pacienteCriado = pacienteService.save(paciente);
        return new ResponseEntity<>(pacienteCriado, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idPaciente}")
    public ResponseEntity<PacienteOutputDTO> update(@PathVariable @Positive Integer idPaciente,
                                                    @RequestBody @Valid PacienteInputDTO paciente)
            throws EntityNotFound {
        PacienteOutputDTO pacienteAtualizado = pacienteService.update(idPaciente, paciente);
        return new ResponseEntity<>(pacienteAtualizado, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idPaciente}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Integer idPaciente)
            throws EntityNotFound {
        pacienteService.delete(idPaciente);
        return ResponseEntity.ok().build();
    }

}
