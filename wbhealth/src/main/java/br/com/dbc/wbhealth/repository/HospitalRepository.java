package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.model.entity.Hospital;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HospitalRepository implements Repositorio<Integer, Hospital> {

    @Override
    public Integer getProximoId(Connection connection, String nextSequence) throws BancoDeDadosException {
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

    @Override
    public List<Hospital> findAll() throws BancoDeDadosException {
        List<Hospital> hospitais = new ArrayList<>();
        Connection con = null;

        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement st = con.createStatement();

            String sql = "SELECT * FROM HOSPITAL\n";

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                Hospital hospital = new Hospital();
                hospital.setIdHospital(res.getInt("id_hospital"));
                hospital.setNome(res.getString("nome"));
                hospitais.add(hospital);
            }
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
        return hospitais;
    }

    @Override
    public Hospital findById(Integer id) throws BancoDeDadosException {
        Hospital hospital = null;
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM HOSPITAL WHERE ID_HOSPITAL = " + id;

            PreparedStatement st = con.prepareStatement(sql);

            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                Integer idHospital = res.getInt("id_hospital");
                String nome = res.getString("nome");

                hospital = new Hospital(idHospital, nome);
            }

        } catch (BancoDeDadosException e) {
            System.err.println("Erro ao acessar o banco de dados:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado:");
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new BancoDeDadosException(e.getCause());
            }
        }
        return hospital;
    }

    @Override
    public Hospital save(Hospital hospital) throws BancoDeDadosException {
        Connection con = null;
        Hospital hospitalAux = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoHospitalId = this.getProximoId(con, "seq_hospital.nextval");
            hospital.setIdHospital(proximoHospitalId);

            String sqlHospital = "INSERT INTO Hospital\n" +
                    "(id_hospital, nome)\n" +
                    "VALUES(?, ?)\n";

            PreparedStatement stHospital = con.prepareStatement(sqlHospital);

            stHospital.setInt(1, hospital.getIdHospital());
            stHospital.setString(2, hospital.getNome());

            int hospitaisInseridos = stHospital.executeUpdate();

            if (hospitaisInseridos == 0) throw new SQLException("Ocorreu um erro ao inserir!");
            hospitalAux = findById(proximoHospitalId);

        } catch (BancoDeDadosException e) {
            System.err.println("Erro ao acessar o banco de dados:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado:");
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new BancoDeDadosException(e.getCause());
            }
        }
        return hospitalAux;
    }

    @Override
    public Hospital update(Integer id, Hospital hospital) throws BancoDeDadosException {

        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Hospital hospitalBd = this.findById(id);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE HOSPITAL SET nome = ?");
            sql.append(" WHERE id_hospital = ?");

            PreparedStatement st = con.prepareStatement(sql.toString());

            int index = 1;

            if (hospital != null && hospital.getNome() != null) {
                st.setString(index++, hospital.getNome());
            }

            st.setInt(index++, hospitalBd.getIdHospital());
            st.executeUpdate();

            return findById(id);
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
    public boolean deleteById(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Hospital hospital = findById(id);

            String sql = "DELETE FROM HOSPITAL WHERE id_hospital = ?";

            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, id);

            int res = st.executeUpdate();

            if (res > 0) {
                String sqlHospital = "DELETE FROM HOSPITAL WHERE id_hospital = ?";
                PreparedStatement sqlHospitais = con.prepareStatement(sqlHospital);
                sqlHospitais.setInt(1, hospital.getIdHospital());
                res = st.executeUpdate();
            } else {
                throw new SQLException("Ocorreu um erro na operação");
            }
            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new BancoDeDadosException(e.getCause());
            }
        }
    }
}
