package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.model.entity.AtendimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, Integer> {

//    @Query("SELECT '*' FROM PESSOA" +
//            "INNER JOIN ")
    boolean existsById(Integer id);

}
