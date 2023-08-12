package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.entity.Medico;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class MedicoRepository implements Repositorio<Integer, Medico> {

    @Override
    public Integer getProximoId(Connection connection, String nextSequence) throws SQLException {
        return null;
    }

    @Override
    public Medico save(Medico medico) throws BancoDeDadosException {
        return null;
    }

    @Override
    public ArrayList<Medico> findAll() throws BancoDeDadosException {
        return null;
    }

    @Override
    public Medico findById(Integer id) throws BancoDeDadosException, EntityNotFound {
        return null;
    }

    @Override
    public Medico update(Integer id, Medico medico) throws BancoDeDadosException, EntityNotFound {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) throws BancoDeDadosException, EntityNotFound {
        return false;
    }

}
