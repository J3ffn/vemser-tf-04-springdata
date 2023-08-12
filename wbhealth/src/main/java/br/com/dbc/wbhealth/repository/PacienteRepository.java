package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.entity.Paciente;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class PacienteRepository implements Repositorio<Integer, Paciente> {
    @Override
    public List<Paciente> findAll() throws BancoDeDadosException {
        return null;
    }

    @Override
    public Paciente findById(Integer idPaciente) throws BancoDeDadosException, EntityNotFound {
        return null;
    }

    @Override
    public Paciente save(Paciente paciente) throws BancoDeDadosException {
        return null;
    }

    @Override
    public Paciente update(Integer idPaciente, Paciente pacienteModificado)
            throws BancoDeDadosException, EntityNotFound {
        return null;
    }

    @Override
    public boolean deleteById(Integer idPaciente) throws BancoDeDadosException, EntityNotFound {
        return false;
    }

    @Override
    public Integer getProximoId(Connection connection, String nextSequence) throws SQLException {
        return null;
    }

}