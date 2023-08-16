package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.model.dto.relatorio.RelatorioLucro;
import br.com.dbc.wbhealth.model.entity.AtendimentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, Integer> {

//    @Query("SELECT '*' FROM PESSOA" +
//            "INNER JOIN ")
    boolean existsById(Integer id);

    Page<AtendimentoEntity> findAtendimentoEntitiesByDataAtendimentoBetween(LocalDate inicio, LocalDate fim, Pageable pageable);

    @Query("select new br.com.dbc.wbhealth.model.dto.relatorio.RelatorioLucro(a.tipoDeAtendimento, SUM(a.valorDoAtendimento)) " +
            "FROM ATENDIMENTO a " +
            "WHERE a.dataAtendimento >= :dataInicio " +
            "AND a.dataAtendimento <= :dataFinal " +
            "GROUP BY a.tipoDeAtendimento")
    Page<RelatorioLucro> getLucroByData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFinal") LocalDate dataFinal, Pageable pageable);

    @Query("select new br.com.dbc.wbhealth.model.dto.relatorio.RelatorioLucro(a.tipoDeAtendimento, SUM(a.valorDoAtendimento)) " +
            "FROM ATENDIMENTO a " +
            "WHERE a.dataAtendimento <= :tempoAtual " +
            "GROUP BY a.tipoDeAtendimento")
    Page<RelatorioLucro> getLucroAteOMomento(@Param("tempoAtual") LocalDate tempoAtual, Pageable pageable); // Terminar

}
