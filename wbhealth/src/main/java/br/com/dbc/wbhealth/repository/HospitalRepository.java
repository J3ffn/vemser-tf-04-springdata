package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.model.entity.Hospital;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class HospitalRepository implements Repositorio<Integer, Hospital> {

    @Override
    public Integer getProximoId(Connection connection, String nextSequence) throws BancoDeDadosException {
        return null;
    }

    @Override
    public List<Hospital> findAll() throws BancoDeDadosException {
        return null;
    }

    @Override
    public Hospital findById(Integer id) throws BancoDeDadosException {
        return null;
    }

    @Override
    public Hospital save(Hospital hospital) throws BancoDeDadosException {
        return null;
    }

    @Override
    public Hospital update(Integer id, Hospital hospital) throws BancoDeDadosException {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) throws BancoDeDadosException {
        return false;
    }

}
