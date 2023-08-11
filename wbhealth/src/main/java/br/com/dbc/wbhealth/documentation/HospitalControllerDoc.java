package br.com.dbc.wbhealth.documentation;

import br.com.dbc.wbhealth.model.dto.hospital.HospitalInputDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

public interface HospitalControllerDoc {

    @Operation(summary = "Listar hospitais", description = "Cria uma lista de todos os hospitais cadastrados no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista gerada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping
    public ResponseEntity<List<HospitalOutputDTO>> findAll();

    @Operation(summary = "Buscar hospital", description = "Busca o hospital pelo o seu id especifico")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Busca do hospital realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idHospital}")
    public ResponseEntity<HospitalOutputDTO> findById(@Positive @PathVariable Integer idHospital);

    @Operation(summary = "Criar hospital", description = "Cria hospital com o dado repassado pela requisicao")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<HospitalOutputDTO> save(@Valid @RequestBody HospitalInputDTO hospital);

    @Operation(summary = "Alterar hospital", description = "Altera hospital com dados que sao repassados pela requisicao")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idHospital}")
    public ResponseEntity<HospitalOutputDTO> update(@Positive @PathVariable Integer idHospital, @Valid @RequestBody HospitalInputDTO hospital);

    @Operation(summary = "Deletar hospital", description = "Deleta o hospital pelo o seu id especifico")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Excluido com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idHospital}")
    public ResponseEntity<Boolean> deleteById(@Positive @PathVariable Integer idHospital);
}
