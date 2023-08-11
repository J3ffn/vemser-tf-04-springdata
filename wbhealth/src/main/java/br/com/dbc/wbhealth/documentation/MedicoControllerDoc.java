package br.com.dbc.wbhealth.documentation;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoInputDTO;
import br.com.dbc.wbhealth.model.dto.medico.MedicoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<MedicoOutputDTO>> findAll() throws BancoDeDadosException;

    @Operation(summary = "Retornar medico por id", description = "Retorna um DTO com os dados do medico cujo id corresponde ao id recebido por pathVariable.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Medico recuperado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping("{id}")
    public ResponseEntity<MedicoOutputDTO> findById(@PathVariable int id);

    @Operation(summary = "Criar medico", description = "Cria um medico com os dados passados através do InputDTO, cria um id e salva no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastrado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()
    public ResponseEntity<MedicoOutputDTO> save(@Valid @RequestBody MedicoInputDTO medicoInputDTO);


    @Operation(summary = "Atualizar medico", description = "Atualiza o medico correspondente ao id passado via pathVariable com os dados passados pelo InputDTO e salva no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<MedicoOutputDTO> update(@PathVariable int id, @Valid @RequestBody MedicoInputDTO medicoInputDTO);

    @Operation(summary = "Deletar medico", description = "Exclui o médico com id correspondente ao id passado por pathVariable")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Excluído com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public String deleteById(@PathVariable int id) throws EntityNotFound;
}
