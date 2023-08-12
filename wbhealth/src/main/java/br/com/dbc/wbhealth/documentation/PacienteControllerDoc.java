package br.com.dbc.wbhealth.documentation;

import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteInputDTO;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

public interface PacienteControllerDoc {
    @Operation(
            summary = "Listar pacientes",
            description = "Lista todos os pacientes cadastrados no sistema"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pacientes"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<PacienteOutputDTO> findAll();


    @Operation(
            summary = "Listar paciente por id",
            description = "Lista o paciente associado à um id específico"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados do paciente"),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar o paciente"),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/by-id")
    ResponseEntity<PacienteOutputDTO> findById(@RequestParam("idPaciente") @Positive Integer idPaciente)
            throws EntityNotFound;


    @Operation(
            summary = "Cadastrar paciente",
            description = "Cadastra um novo paciente no sistema com base nos dados fornecidos"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados do paciente cadastrado"),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar o paciente"),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<PacienteOutputDTO> save(@RequestBody @Valid PacienteInputDTO paciente);


    @Operation(
            summary = "Atualizar paciente por id",
            description = "Atualiza o paciente no sistema com base nos dados fornecidos"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados do paciente atualizado"),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar o paciente"),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPaciente}")
    ResponseEntity<PacienteOutputDTO> update(@PathVariable @Positive Integer idPaciente,
                                             @RequestBody @Valid PacienteInputDTO paciente)
            throws EntityNotFound;


    @Operation(
            summary = "Remover paciente por id",
            description = "Remove o paciente do sistema associado à um id específico"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O paciente foi removido do sistema com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar o paciente"),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPaciente}")
    ResponseEntity<Void> delete(@PathVariable @Positive Integer idPaciente) throws EntityNotFound;
}
