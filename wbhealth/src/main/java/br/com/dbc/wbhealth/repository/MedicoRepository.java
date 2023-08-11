package br.com.dbc.wbhealth.repository;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.entity.Medico;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicoRepository implements Repositorio<Integer, Medico> {


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

    @Override
    public Medico save(Medico medico) throws BancoDeDadosException {
        Connection con = null;
        Medico medicoAtualizado;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoPessoaId = getProximoId(con, "seq_pessoa.nextval");
            medico.setIdPessoa(proximoPessoaId);

            final String sqlPessoa = "INSERT INTO PESSOA\n"
                    + "(id_pessoa, nome, cep, data_nascimento, cpf, salario_mensal, email)\n"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stPesssoa = con.prepareStatement(sqlPessoa);

            stPesssoa.setInt(1, medico.getIdPessoa());
            stPesssoa.setString(2, medico.getNome());
            stPesssoa.setString(3, medico.getCep());
            stPesssoa.setDate(4, Date.valueOf(medico.getDataNascimento()));
            stPesssoa.setString(5, medico.getCpf());
            stPesssoa.setDouble(6, medico.getSalarioMensal());
            stPesssoa.setString(7, medico.getEmail());


            int pessoasInseridas = stPesssoa.executeUpdate();

            if (pessoasInseridas == 0) throw new SQLException("Ocorreu um erro ao inserir!");

            Integer proximoMedicoId = getProximoId(con, "seq_medico.nextval");
            medico.setIdMedico(proximoMedicoId);

            String sql = "INSERT INTO Medico\n" +
                    "(id_pessoa, id_medico, id_hospital, crm)\n" +
                    "VALUES(?, ?, ?, ?)\n";

            PreparedStatement stMedico = con.prepareStatement(sql);
            stMedico.setInt(1, medico.getIdPessoa());
            stMedico.setInt(2, medico.getIdMedico());
            stMedico.setInt(3, 1);
            stMedico.setString(4, medico.getCrm());

            int res = stMedico.executeUpdate();
            medico = findById(medico.getIdMedico());

        } catch (BancoDeDadosException e) {
            e.getLocalizedMessage();
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
            }
        }
        return medico;
    }

    @Override
    public ArrayList<Medico> findAll() throws BancoDeDadosException {
        ArrayList<Medico> medicos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement st = con.createStatement();

            String sql = "SELECT * FROM MEDICO\n" +
                    "INNER JOIN PESSOA\n" +
                    "ON PESSOA.ID_PESSOA = MEDICO.ID_PESSOA";

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                Medico medico = new Medico();
                medico.setIdPessoa(res.getInt("id_pessoa"));
                medico.setNome(res.getString("nome"));
                medico.setCep(res.getString("cep"));
                medico.setDataNascimento(res.getDate("data_nascimento").toLocalDate());
                medico.setCpf(res.getString("cpf"));
                medico.setSalarioMensal(res.getDouble("salario_mensal"));
                medico.setIdMedico(res.getInt("id_medico"));
                medico.setIdHospital(res.getInt("id_hospital"));
                medico.setCrm(res.getString("crm"));
                medico.setEmail(res.getString("email"));

                medicos.add(medico);
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
        return medicos;
    }

    @Override
    public Medico findById(Integer id) throws BancoDeDadosException, EntityNotFound {
        Medico medico = new Medico();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM MEDICO\n" +
                    "INNER JOIN PESSOA ON MEDICO.ID_MEDICO = ? AND PESSOA.ID_PESSOA = MEDICO.ID_PESSOA\n";

            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet res = st.executeQuery();
            if (res.next()) {
                medico.setIdPessoa(res.getInt("id_pessoa"));
                medico.setNome(res.getString("nome"));
                medico.setCep(res.getString("cep"));
                medico.setDataNascimento(res.getDate("data_nascimento").toLocalDate());
                medico.setCpf(res.getString("cpf"));
                medico.setSalarioMensal(res.getDouble("salario_mensal"));
                medico.setIdMedico(res.getInt("id_medico"));
                medico.setIdHospital(res.getInt("id_hospital"));
                medico.setCrm(res.getString("crm"));
                medico.setEmail(res.getString("email"));

                if (medico.equals(null)) {
                    throw new EntityNotFound("Medico não encontrado");
                }

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
        return medico;
    }

    @Override
    public Medico update(Integer id, Medico medico) throws BancoDeDadosException, EntityNotFound {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Medico medicoId = this.findById(id);
            if (medicoId.equals(null)) {
                throw new EntityNotFound("Medico não encontrado!");
            }
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PESSOA SET \n");
            List<String> camposAtualizados = new ArrayList<>();
            if (medico != null) {
                if (medico.getNome() != null) {
                    camposAtualizados.add("nome = ?");
                }
                if (medico.getCep() != null) {
                    camposAtualizados.add("cep = ?");
                }
                if (medico.getDataNascimento() != null) {
                    camposAtualizados.add("data_nascimento = ?");
                }
                if (medico.getCpf() != null) {
                    camposAtualizados.add("cpf = ?");
                }
                if (medico.getSalarioMensal() != null) {
                    camposAtualizados.add("salario_mensal = ?");
                }
                if (medico.getEmail() != null) {
                    camposAtualizados.add("email = ?");
                }
            }

            if (!camposAtualizados.isEmpty()) {
                sql.append(String.join(", ", camposAtualizados));
                sql.append(" WHERE id_pessoa = ?");

                PreparedStatement st = con.prepareStatement(sql.toString());

                int index = 1;
                if (medico != null) {
                    if (medico.getNome() != null) {
                        st.setString(index++, medico.getNome());
                    }
                    if (medico.getCep() != null) {
                        st.setString(index++, medico.getCep());
                    }
                    if (medico.getDataNascimento() != null) {
                        st.setDate(index++, Date.valueOf(medico.getDataNascimento()));
                    }
                    if (medico.getCpf() != null) {
                        st.setString(index++, medico.getCpf());
                    }
                    if (medico.getSalarioMensal() != null) {
                        st.setDouble(index++, medico.getSalarioMensal());
                    }
                    if (medico.getEmail() != null) {
                        st.setString(index++, medico.getEmail());
                    }
                }

                st.setInt(index++, medicoId.getIdPessoa());

                int res = st.executeUpdate();


                String sqlMedico = "UPDATE MEDICO SET crm = ?\n" +
                        "WHERE MEDICO.id_medico= ?";
                if (medico.getCrm() != null) {
                    st.setString(1, medico.getCrm());
                }
                PreparedStatement statement = con.prepareStatement(sqlMedico);
                statement.setString(1, medico.getCrm());
                statement.setInt(2, id);

                int res2 = statement.executeUpdate();

                return findById(id);
            } else {
                throw new RuntimeException("Nenhum campo para atualizar.");
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
                throw new BancoDeDadosException(e.getCause());
            }
        }
    }

    @Override
    public boolean deleteById(Integer id) throws BancoDeDadosException, EntityNotFound {
        Connection con = null;

        try {
            con = ConexaoBancoDeDados.getConnection();

            Medico medico = findById(id);

            if (medico.equals(null)) {
                throw new EntityNotFound("Medico não encontrado");
            }

            String sql = "DELETE FROM MEDICO WHERE ID_MEDICO = ?";

            PreparedStatement stMedico = con.prepareStatement(sql);

            stMedico.setInt(1, id);

            int resMedico = stMedico.executeUpdate();
            int resPessoa = 0;
            if (resMedico > 0) {
                String sqlPessoa = "DELETE FROM PESSOA WHERE ID_PESSOA = ?";
                PreparedStatement stPessoa = con.prepareStatement(sqlPessoa);
                stPessoa.setInt(1, medico.getIdPessoa());
                resPessoa = stPessoa.executeUpdate();
            } else {
                throw new SQLException("Ocorreu um erro na operação");
            }

            return resPessoa > 0;
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

//    public boolean buscarCpf(Medico medico){
//        Connection con = null;
//        boolean retorno = false;
//        try {
//            con = ConexaoBancoDeDados.getConnection();
//            String sql = "SELECT * FROM Pessoa " +
//                    "WHERE cpf = ?";
//
//            PreparedStatement st = con.prepareStatement(sql);
//
//            st.setString(1, medico.getCpf());
//
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()){
//                retorno = true;
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return retorno;
//
//    }
}
