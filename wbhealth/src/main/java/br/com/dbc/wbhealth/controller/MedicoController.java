package br.com.dbc.wbhealth.controller;

import br.com.dbc.wbhealth.documentation.MedicoControllerDoc;
import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoInputDTO;
import br.com.dbc.wbhealth.model.dto.medico.MedicoOutputDTO;
import br.com.dbc.wbhealth.service.MedicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/medico")
public class MedicoController implements MedicoControllerDoc {
    private final MedicoService medicoService;

    @Autowired
    private ObjectMapper objectMapper;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }


    @GetMapping
    public ResponseEntity<List<MedicoOutputDTO>> findAll() throws BancoDeDadosException {
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<MedicoOutputDTO> findById(@PathVariable int id) {
        MedicoOutputDTO medicoOutputDTO = new MedicoOutputDTO();
        try {
            medicoOutputDTO = medicoService.findById(id);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(medicoOutputDTO);
    }

    @PostMapping()
    public ResponseEntity<MedicoOutputDTO> save(@Valid @RequestBody MedicoInputDTO medicoInputDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.save(medicoInputDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<MedicoOutputDTO> update(@PathVariable int id, @Valid @RequestBody MedicoInputDTO medicoInputDTO) {
        MedicoOutputDTO medicoOutputDTO = new MedicoOutputDTO();
        try {
            medicoOutputDTO = medicoService.update(id, medicoInputDTO);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(medicoOutputDTO);
    }

    @DeleteMapping("{id}")
    public String deleteById(@PathVariable int id) throws EntityNotFound {
        return medicoService.deletarPeloId(id);
    }
}
