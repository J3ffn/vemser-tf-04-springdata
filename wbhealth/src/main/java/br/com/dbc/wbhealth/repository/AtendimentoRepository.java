package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.entity.Atendimento;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class AtendimentoRepository implements Repositorio<Integer, Atendimento> {

    @Override
    public Atendimento save(Atendimento atendimento) throws BancoDeDadosException {
        return null;
    }

    private List<Atendimento> pupuleAtendimentos(Statement statement, String query) {
        return null;
    }

    @Override
    public List<Atendimento> findAll() throws BancoDeDadosException {
        return null;
    }

    @Override
    public Atendimento findById(Integer id) throws BancoDeDadosException, EntityNotFound {
        return null;
    }

    @Override
    public Atendimento update(Integer id, Atendimento atendimento) throws BancoDeDadosException, EntityNotFound {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) throws BancoDeDadosException, EntityNotFound {
        return false;
    }

    @Override
    public Integer getProximoId(Connection connection, String nextSequence) throws SQLException {
        return null;
    }
}
