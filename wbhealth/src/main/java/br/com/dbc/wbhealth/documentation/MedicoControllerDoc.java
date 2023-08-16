package br.com.dbc.wbhealth.documentation;

import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoInputDTO;
import br.com.dbc.wbhealth.model.dto.medico.MedicoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public interface MedicoControllerDoc {
    @Operation(summary = "Listar medicos", description = "Cria uma lista de OutputDTOs com todos os medicos cadastrados no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista gerada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<MedicoOutputDTO>> findAll(@RequestParam @PositiveOrZero Integer pagina,
                                                  @RequestParam @Positive Integer quantidadeRegistros);

    @Operation(summary = "Retornar medico por id", description = "Retorna um DTO com os dados do medico cujo id corresponde ao id recebido por pathVariable.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Medico recuperado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping("/{idMedico}")
    ResponseEntity<MedicoOutputDTO> findById(@PathVariable @Positive Integer idMedico) throws EntityNotFound;

    @Operation(summary = "Criar medico", description = "Cria um medico com os dados passados através do InputDTO, cria um id e salva no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastrado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()
    ResponseEntity<MedicoOutputDTO> save(@Valid @RequestBody MedicoInputDTO medicoInputDTO);


    @Operation(summary = "Atualizar medico", description = "Atualiza o medico correspondente ao id passado via pathVariable com os dados passados pelo InputDTO e salva no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idMedico}")
    ResponseEntity<MedicoOutputDTO> update(@PathVariable @Positive Integer idMedico,
                                           @Valid @RequestBody MedicoInputDTO medicoInputDTO) throws EntityNotFound;

    @Operation(summary = "Deletar medico", description = "Exclui o médico com id correspondente ao id passado por pathVariable")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Excluído com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idMedico}")
    ResponseEntity<Void> deleteById(@PathVariable @Positive Integer idMedico) throws EntityNotFound;
}
