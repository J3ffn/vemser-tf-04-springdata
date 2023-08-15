package br.com.dbc.wbhealth.documentation;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoInputDTO;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoOutputDTO;
import br.com.dbc.wbhealth.model.entity.AtendimentoEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

public interface AtendimentoControllerDoc {

    @Operation(summary = "Listar atendimentos.", description = "Lista todos os atendimentos do banco.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de atendimentos."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @GetMapping
    ResponseEntity<List<AtendimentoOutputDTO>> findAll() throws BancoDeDadosException;

    @Operation(summary = "Adicionar atendimento.", description = "Adiciona um atendimento ao banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o atendimento salvo."),
                    @ApiResponse(responseCode = "400", description = "Não foi possível registar o atendimento no banco."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PostMapping
    ResponseEntity<AtendimentoOutputDTO> save(@Valid @RequestBody AtendimentoInputDTO novoAtendimento) throws BancoDeDadosException, EntityNotFound, MessagingException;

    @Operation(summary = "Buscar atendimento pelo ID.", description = "Busca um atendimento pelo seu ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o atendimento buscado."),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar o atendimento."),
                    @ApiResponse(responseCode = "404", description = "Atendimento não encontrado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @GetMapping("/{idAtendimento}")
    ResponseEntity<AtendimentoOutputDTO> buscarAtendimentoPeloId(@Positive(message = "Deve ser positivo") @PathVariable Integer idAtendimento) throws BancoDeDadosException, EntityNotFound;

    @Operation(summary = "Buscar atendimentos de um usuário.", description = "Busca um atendimento pelo ID de um paciente.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os atendimentos relacionados ao paciente."),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar os atendimentos."),
                    @ApiResponse(responseCode = "404", description = "Nenhum atendimento foi encontrado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @GetMapping("/paciente/{idPaciente}")
    ResponseEntity<List<AtendimentoOutputDTO>> bucarAtendimentoPeloIdUsuario(@Positive(message = "Deve ser positivo") @PathVariable Integer idPaciente) throws BancoDeDadosException;

    @Operation(summary = "Alterar informações de um atendimento.", description = "Altera informações de um atendimento com o id passado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os atendimento já com as alterações."),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar o atendimento."),
                    @ApiResponse(responseCode = "404", description = "Nenhum atendimento foi encontrado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PutMapping("/{idAtendimento}")
    ResponseEntity<AtendimentoOutputDTO> alterarPeloId(@Positive(message = "Deve ser positivo") @PathVariable Integer idAtendimento,
                                                       @Valid @RequestBody AtendimentoInputDTO atendimento) throws BancoDeDadosException, EntityNotFound, MessagingException;

    @Operation(summary = "Deletar um atendimento.", description = "Deleta um atendimento do banco de dados pelo seu ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Não há retorno."),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar o atendimento."),
                    @ApiResponse(responseCode = "404", description = "Nenhum atendimento foi encontrado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @DeleteMapping("/{idAtendimento}")
    ResponseEntity<Void> deletarAtendimento(@Positive(message = "Deve ser positivo") @PathVariable Integer idAtendimento) throws EntityNotFound;

}
