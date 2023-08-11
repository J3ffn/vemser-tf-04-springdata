package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.entity.Paciente;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PacienteRepository implements Repositorio<Integer, Paciente> {
    @Override
    public List<Paciente> findAll() throws BancoDeDadosException {
        List<Paciente> listaPacientes = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            final String QUERY_SQL = "SELECT * FROM PESSOA\n"
                    + "INNER JOIN PACIENTE\n"
                    + "ON PESSOA.ID_PESSOA = PACIENTE.ID_PESSOA";
            Statement statement = conexao.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_SQL);

            while (resultSet.next()) {
                Paciente paciente = getPacienteFromResultSet(resultSet);
                listaPacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            fecharConexaoComBancoDeDados(conexao);
        }

        return listaPacientes;
    }

    @Override
    public Paciente findById(Integer idPaciente) throws BancoDeDadosException, EntityNotFound {
        Paciente paciente = null;
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            final String QUERY_SQL = "SELECT * FROM PACIENTE\n"
                    + "INNER JOIN PESSOA ON PACIENTE.ID_PACIENTE = ? "
                    + "AND PESSOA.ID_PESSOA = PACIENTE.ID_PESSOA\n";
            PreparedStatement statement = conexao.prepareStatement(QUERY_SQL);
            statement.setInt(1, idPaciente);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                paciente = getPacienteFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            fecharConexaoComBancoDeDados(conexao);
        }

        if (paciente == null)
            throw new EntityNotFound("Paciente não encontrado!");

        return paciente;
    }

    @Override
    public Paciente save(Paciente paciente) throws BancoDeDadosException {
        Paciente novoPaciente;
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();

            final String QUERY_PESSOA = "INSERT INTO PESSOA\n"
                    + "(id_pessoa, nome, cep, data_nascimento, cpf, salario_mensal, email)\n"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)\n";
            inserirNaTabelaPessoa(paciente, conexao, QUERY_PESSOA);

            final String QUERY_PACIENTE = "INSERT INTO PACIENTE\n"
                    + "(id_paciente, id_hospital, id_pessoa)\n"
                    + "VALUES(?, ?, ?)\n";
            inserirNaTabelaPaciente(paciente, conexao, QUERY_PACIENTE);

            novoPaciente = findById(paciente.getIdPaciente());

        } catch (SQLException | EntityNotFound e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            fecharConexaoComBancoDeDados(conexao);
        }

        return novoPaciente;
    }

    @Override
    public Paciente update(Integer idPaciente, Paciente pacienteModificado)
            throws BancoDeDadosException, EntityNotFound {
        Paciente pacienteAtualizado = null;
        Connection conexao = null;

        try {
            pacienteAtualizado = findById(idPaciente);
            conexao = ConexaoBancoDeDados.getConnection();
            final String UPDATE_QUERY = "UPDATE PESSOA SET \n"
                    + "nome = ?, "
                    + "cep = ?, "
                    + "data_nascimento = ?, "
                    + "cpf = ?, "
                    + "salario_mensal = ?, "
                    + "email = ?"
                    + "WHERE id_pessoa = ?";

            PreparedStatement preparedStatement = conexao.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, pacienteModificado.getNome());
            preparedStatement.setString(2, pacienteModificado.getCep());
            preparedStatement.setDate(3, Date.valueOf(pacienteModificado.getDataNascimento()));
            preparedStatement.setString(4, pacienteModificado.getCpf());
            preparedStatement.setDouble(5, pacienteModificado.getSalarioMensal());
            preparedStatement.setString(6, pacienteAtualizado.getEmail());
            preparedStatement.setInt(7, pacienteAtualizado.getIdPessoa());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            fecharConexaoComBancoDeDados(conexao);
        }

        return pacienteAtualizado;
    }

    @Override
    public boolean deleteById(Integer idPaciente) throws BancoDeDadosException, EntityNotFound {
        Connection conexao = null;
        try {
            Paciente deletarPaciente = findById(idPaciente);
            conexao = ConexaoBancoDeDados.getConnection();

            final String PACIENTE_QUERY = "DELETE FROM PACIENTE WHERE ID_PACIENTE = ?";
            PreparedStatement stPaciente = conexao.prepareStatement(PACIENTE_QUERY);
            stPaciente.setInt(1, idPaciente);
            int resPaciente = stPaciente.executeUpdate();
            if (resPaciente == 0)
                return false;

            final String PESSOA_QUERY = "DELETE FROM PESSOA WHERE ID_PESSOA = ?";
            PreparedStatement stPessoa = conexao.prepareStatement(PESSOA_QUERY);
            stPessoa.setInt(1, deletarPaciente.getIdPessoa());
            int resPessoa = stPessoa.executeUpdate();

            return resPessoa > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            fecharConexaoComBancoDeDados(conexao);
        }
    }

    @Override
    public Integer getProximoId(Connection connection, String nextSequence) throws SQLException {
        try {
            String sql = "SELECT " + nextSequence + " mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }

            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    private Paciente getPacienteFromResultSet(ResultSet resultSet) throws SQLException {
        Integer idPessoa = resultSet.getInt("id_pessoa");
        String nome = resultSet.getString("nome");
        String cep = resultSet.getString("cep");
        LocalDate data = resultSet.getDate("data_nascimento").toLocalDate();
        String cpf = resultSet.getString("cpf");
        Double salarioMensal = resultSet.getDouble("salario_mensal");
        String email = resultSet.getString("email");
        Integer idPaciente = resultSet.getInt("id_paciente");
        Integer idHospital = resultSet.getInt("id_hospital");

        String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Paciente paciente = new Paciente(nome, cep, dataFormatada, cpf,
                salarioMensal, email, idHospital);

        paciente.setEmail(email);

        paciente.setIdPessoa(idPessoa);
        paciente.setIdPaciente(idPaciente);

        return paciente;
    }

    private void fecharConexaoComBancoDeDados(Connection conexao) throws BancoDeDadosException {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    private void inserirNaTabelaPessoa(Paciente paciente, Connection conexao, String query) throws SQLException {
        Integer idPessoa = this.getProximoId(conexao, "seq_pessoa.nextval");
        paciente.setIdPessoa(idPessoa);

        PreparedStatement stPesssoa = conexao.prepareStatement(query);
        stPesssoa.setInt(1, paciente.getIdPessoa());
        stPesssoa.setString(2, paciente.getNome());
        stPesssoa.setString(3, paciente.getCep());
        stPesssoa.setDate(4, Date.valueOf(paciente.getDataNascimento()));
        stPesssoa.setString(5, paciente.getCpf());
        stPesssoa.setDouble(6, paciente.getSalarioMensal());
        stPesssoa.setString(7, paciente.getEmail());

        stPesssoa.executeUpdate();
    }

    private void inserirNaTabelaPaciente(Paciente paciente, Connection conexao, String query) throws SQLException {
        Integer proximoPacienteId = this.getProximoId(conexao, "seq_paciente.nextval");
        paciente.setIdPaciente(proximoPacienteId);

        PreparedStatement stPaciente = conexao.prepareStatement(query);
        stPaciente.setInt(1, paciente.getIdPaciente());
        stPaciente.setInt(2, 1);
        stPaciente.setInt(3, paciente.getIdPessoa());

        stPaciente.executeUpdate();
    }

}