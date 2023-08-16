package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.model.entity.AtendimentoEntity;
import br.com.dbc.wbhealth.model.entity.MedicoEntity;
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

    @Query("SELECT COUNT(a) FROM ATENDIMENTO a " +
            "WHERE a.medicoEntity = :medico " +
            "AND a.dataAtendimento BETWEEN :dataInicio AND :dataFim")
    Long countAtendimentosByMedicoAndDateRange(@Param("medico") MedicoEntity medico, LocalDate dataInicio, LocalDate dataFim);

}
