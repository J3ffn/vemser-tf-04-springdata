package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.entity.Atendimento;
import br.com.dbc.wbhealth.model.enumarator.TipoDeAtendimento;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AtendimentoRepository implements Repositorio<Integer, Atendimento> {

    @Override
    public Atendimento save(Atendimento atendimento) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoAtendimentoId = this.getProximoId(con, "seq_atendimento.nextval");

            atendimento.setIdAtendimento(proximoAtendimentoId);

            String sqlAtendimento = """
                    INSERT INTO Atendimento
                    (id_atendimento, id_hospital, id_paciente, id_medico, data_atendimento, laudo, tipo_de_atendimento, valor_atendimento)
                    VALUES(?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            PreparedStatement stAtendimento = con.prepareStatement(sqlAtendimento);

            stAtendimento.setInt(1, atendimento.getIdAtendimento());
            stAtendimento.setInt(2, atendimento.getIdHospital());
            stAtendimento.setInt(3, atendimento.getIdPaciente());
            stAtendimento.setInt(4, atendimento.getIdMedico());
            stAtendimento.setDate(5, Date.valueOf(atendimento.getDataAtendimento()));
            stAtendimento.setString(6, atendimento.getLaudo());
            stAtendimento.setString(7, atendimento.getTipoDeAtendimento().name());
            stAtendimento.setDouble(8, atendimento.getValorDoAtendimento());

            stAtendimento.executeUpdate();

            return atendimento;

        } catch (SQLException e) {
            throw new BancoDeDadosException(new Throwable("DEU RUIM NO SALVAMENTO!"));
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Atendimento> pupuleAtendimentos(Statement statement, String query) {
        List<Atendimento> atendimentos = null;
        try {
            ResultSet res = statement.executeQuery(query);

            atendimentos = new ArrayList<>();
            while (res.next()) {
                Integer idAtendimento = res.getInt("id_atendimento");
                Integer idHospital = res.getInt("id_hospital");
                Integer idPaciente = res.getInt("id_paciente");
                Integer idMedico = res.getInt("id_medico");
                LocalDate dataAtendimento = res.getDate("data_atendimento").toLocalDate();
                String laudo = res.getString("laudo");
                TipoDeAtendimento tipoDeAtendimento = TipoDeAtendimento.getTipo(res.getString("tipo_de_atendimento"));
                Double valorAtendimento = res.getDouble("valor_atendimento");

                Atendimento atendimento = new Atendimento(idAtendimento, idHospital, idPaciente, idMedico, dataAtendimento, laudo, tipoDeAtendimento, valorAtendimento);

                atendimento.setIdAtendimento(idAtendimento);
                atendimentos.add(atendimento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return atendimentos;
    }

    @Override
    public List<Atendimento> findAll() throws BancoDeDadosException {
        List<Atendimento> atendimentos;
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement st = con.createStatement();

            String sql = "SELECT * FROM ATENDIMENTO";

            atendimentos = pupuleAtendimentos(st, sql);

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return atendimentos;
    }

    @Override
    public Atendimento findById(Integer id) throws BancoDeDadosException, EntityNotFound {
        Atendimento atendimento;
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ATENDIMENTO "
                    + "WHERE id_atendimento = " + id;

            Statement st = con.prepareStatement(sql);

            List<Atendimento> lista = this.pupuleAtendimentos(st, sql);

            if (lista.isEmpty()) {
                throw new EntityNotFound("Id inválido!");
            }

            atendimento = lista.get(0);

            return atendimento;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Atendimento update(Integer id, Atendimento atendimento) throws BancoDeDadosException, EntityNotFound {
        Connection con = null;
        try {
            Atendimento atendimentoBuscado = this.findById(id);

            if (atendimentoBuscado == null) {
                throw new EntityNotFound("ID de atendimento inválido!");
            }

            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ATENDIMENTO SET ");
            sql.append("data_atendimento = ?, ");
            sql.append("laudo = ?, ");
            sql.append("tipo_de_atendimento = ? ");
            sql.append("WHERE id_atendimento = ?");

            PreparedStatement st = con.prepareStatement(sql.toString());

            int index = 1;
            st.setDate(index++, Date.valueOf(atendimento.getDataAtendimento()));
            st.setString(index++, atendimento.getLaudo());
            st.setString(index++, atendimento.getTipoDeAtendimento().name());
            st.setInt(index++, atendimentoBuscado.getIdAtendimento());

            st.execute();

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return atendimento;
    }

    @Override
    public boolean deleteById(Integer id) throws BancoDeDadosException, EntityNotFound {
        Connection con = null;
        try {
            Atendimento atendimento = findById(id);

            if (atendimento == null) {
                throw new EntityNotFound("Atendimento não encontrado!");
            }

            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM ATENDIMENTO " +
                    "WHERE id_atendimento = ?";

            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, id);

            return st.execute();

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Integer getProximoId(Connection connection, String nextSequence) throws SQLException {
        String sql = "SELECT " + nextSequence + " mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

}
