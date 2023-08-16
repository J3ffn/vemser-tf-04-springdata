package br.com.dbc.wbhealth.documentation;

import br.com.dbc.wbhealth.model.dto.hospital.HospitalAtendimentoDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalInputDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public interface HospitalControllerDoc {

    @Operation(summary = "Listar hospitais", description = "Lista  todos os hospitais do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista gerada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping
    public ResponseEntity<List<HospitalOutputDTO>> findAll();

    @Operation(summary = "Buscar hospital pelo id", description = "Busca um hospital pelo o seu id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Busca do hospital realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idHospital}")
    public ResponseEntity<HospitalOutputDTO> findById(@Positive @PathVariable Integer idHospital);

    @Operation(
            summary = "Listar Hospitais com Atendimentos",
            description = "Lista todos os hospitais com todos os atendimentos forma paginada"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Não foi possível buscar o hospital"),
                    @ApiResponse(responseCode = "404", description = "HOspital não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/atendimentos")
    List<HospitalAtendimentoDTO> findHospitaisWithAllAtendimentos(@RequestParam @PositiveOrZero Integer pagina,
                                                     @RequestParam @Positive Integer quantidadeRegistros);

    @Operation(summary = "Adicionar hospital", description = "Cria hospital com o dado repassado pela requisicao")
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
