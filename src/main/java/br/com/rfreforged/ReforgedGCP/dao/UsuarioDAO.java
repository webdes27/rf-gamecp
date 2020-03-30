package br.com.rfreforged.ReforgedGCP.dao;

import br.com.rfreforged.ReforgedGCP.exception.ContaBanidaException;
import br.com.rfreforged.ReforgedGCP.exception.SenhaAtualIncorretaException;
import br.com.rfreforged.ReforgedGCP.model.usuario.AlterarSenha;
import br.com.rfreforged.ReforgedGCP.model.ApiResponse;
import br.com.rfreforged.ReforgedGCP.model.usuario.Permissao;
import br.com.rfreforged.ReforgedGCP.model.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAO {

    @Autowired
    @Qualifier("jdbcTempRFUser")
    private JdbcTemplate tempRFUser;
    @Autowired
    @Qualifier("jdbcTempGameCP")
    private JdbcTemplate tempGameCP;
    @Autowired
    private PasswordEncoder encoder;


    public Usuario getUsuario(String nome) throws ContaBanidaException {
        String sql = "SELECT COUNT(*) FROM RF_User.dbo.tbl_UserBan AS B INNER JOIN RF_User.dbo.tbl_UserAccount AS U" +
                " ON U.serial = B.nAccountSerial WHERE CONVERT(varchar, U.id) = ?";
        Integer count = tempRFUser.queryForObject(sql, new Object[]{nome}, Integer.class);
        assert count != null;
        if (count > 0) {
            throw new ContaBanidaException("Esta conta foi banida");
        }
        sql = "SELECT ac.id, ac.name as nome, ac.password as senha, email, role.id as idRole, role.name as roleName" +
                " FROM [GameCP].[dbo].[tbl_useraccount] as ac INNER JOIN [GameCP].[dbo].[tbl_role] as role ON role.id = ac.id_role" +
                " WHERE ac.name = ?";
        Usuario u = new Usuario();
        try {
            tempRFUser.query(sql, new Object[]{nome}, resultSet -> {
                u.setId(resultSet.getInt("id"));
                u.setSenha(resultSet.getString("senha"));
                u.setEmail(resultSet.getString("email"));
                u.setNome(resultSet.getString("nome"));
                Permissao p = new Permissao(resultSet.getInt("idRole"), resultSet.getString("roleName"));
                u.setRole(p);
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if (u.getNome() == null || u.getNome().isEmpty()) {
            return null;
        }
        return u;
    }

    public boolean checaNomeUsuario(String nome) {
        String sql = "SELECT COUNT(*) FROM [GameCP].[dbo].[tbl_useraccount] AS ac WHERE ac.name = ?";
        return tempRFUser.queryForObject(sql, new Object[]{nome}, Integer.class) > 0;
    }

    public boolean checaEmail(String email) {
        String sql = "SELECT COUNT(*) FROM [GameCP].[dbo].[tbl_useraccount] AS ac WHERE ac.email = ?";
        return tempRFUser.queryForObject(sql, new Object[]{email}, Integer.class) > 0;
    }

    public ApiResponse criarConta(Usuario usuario) {
        boolean[] error = new boolean[1];
        error[0] = false;
        try {
            if (checaNomeUsuario(usuario.getNome())) {
                return new ApiResponse(false, "Este nome de usuário já existe.");
            } else if (checaEmail(usuario.getEmail())) {
                return new ApiResponse(false, "Este email já existe.");
            }
            String sql = "INSERT INTO GameCP.dbo.tbl_useraccount (name, password, email, id_role) values (?, ?, ?, ?)";
            tempGameCP.update(sql, usuario.getNome(),
                    encoder.encode(usuario.getSenha()), usuario.getEmail(), 2);

            sql = "SELECT name, password, email FROM GameCP.dbo.tbl_useraccount WHERE name = ?;";
            tempGameCP.query(sql, new Object[]{usuario.getNome()}, resultSet -> {
                var nome = resultSet.getString("name");
                var email = resultSet.getString("email");
                var password = resultSet.getString("password");
                if (encoder.matches(usuario.getSenha(), password)
                        && nome.equals(usuario.getNome()) && email.equals(usuario.getEmail())) {
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                } else {
                    String delete = "DELETE FROM GameCP.dbo.tbl_useraccount WHERE name = ?;";
                    tempGameCP.update(delete, usuario.getNome());
                    error[0] = true;
                }
            });

            if (error[0]) {
                return new ApiResponse(false, "Você tentou salvar dados, em um formato incorreto.");
            }

            sql = "INSERT INTO dbo.tbl_rfaccount (id, password, birthdate, Email) " +
                    "VALUES (CONVERT(binary, '" + usuario.getNome() + "'), CONVERT(binary, '" + usuario.getSenha() + "')," +
                    " '1970-01-01 00:00:00', '"+usuario.getEmail()+"')";
            tempRFUser.update(sql);

            return new ApiResponse(true, "Registrado com Sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean alterarSenha(AlterarSenha usuario) throws SenhaAtualIncorretaException {
        String sql = "SELECT password FROM dbo.tbl_useraccount WHERE name = ?;";
        String senhaAtual = tempGameCP.queryForObject(sql, new Object[]{usuario.getNomeUsuario()}, String.class);
        if (senhaAtual != null && encoder.matches(usuario.getSenhaAtual(), senhaAtual)) {
            sql = "UPDATE dbo.tbl_useraccount SET password = ? WHERE name = ?;";
            tempGameCP.update(sql, encoder.encode(usuario.getNovaSenha()), usuario.getNomeUsuario());
            sql = "UPDATE dbo.tbl_rfaccount SET password = CONVERT(binary, ?) WHERE id = CONVERT(binary, ?);";
            tempRFUser.update(sql, usuario.getNovaSenha(), usuario.getNomeUsuario());
            return true;
        } else {
            throw new SenhaAtualIncorretaException("A senha atual informada, está incorreta.");
        }
    }
}
