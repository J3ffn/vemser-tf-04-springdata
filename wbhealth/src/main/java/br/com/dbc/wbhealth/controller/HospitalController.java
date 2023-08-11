package br.com.dbc.wbhealth.controller;

import br.com.dbc.wbhealth.documentation.HospitalControllerDoc;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalInputDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalOutputDTO;
import br.com.dbc.wbhealth.service.HospitalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/hospital")
@Validated
public class HospitalController implements HospitalControllerDoc {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public ResponseEntity<List<HospitalOutputDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(hospitalService.findAll());
    }

    @GetMapping("/{idHospital}")
    public ResponseEntity<HospitalOutputDTO> findById(@Positive @PathVariable Integer idHospital) {
        return ResponseEntity.status(HttpStatus.OK).body(hospitalService.findById(idHospital));
    }

    @PostMapping
    public ResponseEntity<HospitalOutputDTO> save(@Valid @RequestBody HospitalInputDTO hospital) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.save(hospital));
    }

    @PutMapping("/{idHospital}")
    public ResponseEntity<HospitalOutputDTO> update(@Positive @PathVariable Integer idHospital, @Valid @RequestBody HospitalInputDTO hospital) {
        return ResponseEntity.status(HttpStatus.OK).body(hospitalService.update(idHospital, hospital));
    }

    @DeleteMapping("/{idHospital}")
    public ResponseEntity<Boolean> deleteById(@Positive @PathVariable Integer idHospital) {
        hospitalService.deleteById(idHospital);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
