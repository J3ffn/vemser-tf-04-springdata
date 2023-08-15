package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.model.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

//    @Query("SELECT '*' FROM PESSOA" +
//            "INNER JOIN ")
    boolean existsById(Long aLong);

}
