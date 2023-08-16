package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.model.dto.relatorio.RelatorioLucro;
import br.com.dbc.wbhealth.model.dto.relatorio.RelatorioQuantidade;
import br.com.dbc.wbhealth.model.entity.AtendimentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, Integer> {

//    @Query("SELECT '*' FROM PESSOA" +
//            "INNER JOIN ")
    boolean existsById(Integer id);

    Page<AtendimentoEntity> findAtendimentoEntitiesByDataAtendimentoBetween(LocalDate inicio, LocalDate fim, Pageable pageable);

    @Query(value = "select SUM(a.VALOR_ATENDIMENTO) from ATENDIMENTO a " +
            "WHERE a.DATA_ATENDIMENTO >= :dataInicio AND a.DATA_ATENDIMENTO <= :dataFinal", nativeQuery = true)
    Page<RelatorioLucro> getLucroByData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFinal") LocalDate dataFinal, Pageable pageable);

    @Query(value = "select SUM(a.VALOR_ATENDIMENTO) from ATENDIMENTO a " +
            "       WHERE a.DATA_ATENDIMENTO <= ?1", nativeQuery = true)
    Page<RelatorioLucro> getLucroAteOMomento(LocalDate tempoAtual); // Terminar

}
