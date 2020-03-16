package br.com.rfreforged.ReforgedGCP.dao;

import br.com.rfreforged.ReforgedGCP.exception.SenhaAtualIncorretaException;
import br.com.rfreforged.ReforgedGCP.model.AlterarSenha;
import br.com.rfreforged.ReforgedGCP.model.Permissao;
import br.com.rfreforged.ReforgedGCP.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private PasswordEncoder passwordEncoder;

    public Usuario getUsuario(String nome) {
        String sql = "SELECT ac.id, ac.name as nome, ac.password as senha, email, role.id as idRole, role.name as roleName" +
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

    public boolean criarConta(Usuario usuario) {
        try {
            String sql = "INSERT INTO dbo.tbl_rfaccount (id, password, birthdate, Email) " +
                    "VALUES CONVERT(binary, ?), CONVERT(binary, ?), '1970-01-01 00:00:00', ?)";
            tempRFUser.update(sql, usuario.getNome(), usuario.getSenha(), usuario.getEmail());
            sql = "INSERT INTO dbo.tbl_useraccount (name, password, email, id_role) values (?, ?, ?, ?)";
            int update = tempGameCP.update(sql, usuario.getNome(),
                    passwordEncoder.encode(usuario.getSenha()), usuario.getEmail(), 2);
            return update > 0;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean alterarSenha(AlterarSenha usuario) throws SenhaAtualIncorretaException {
        String sql = "SELECT password FROM dbo.tbl_useraccount WHERE name = ?;";
        String senhaAtual = tempGameCP.queryForObject(sql, new Object[]{usuario.getNomeUsuario()}, String.class);
        if (senhaAtual != null && senhaAtual.equals(passwordEncoder.encode(usuario.getSenhaAtual()))) {
            sql = "UPDATE dbo.tbl_useraccount SET password = ? WHERE name = ?;";
            tempGameCP.update(sql, passwordEncoder.encode(usuario.getNovaSenha()), usuario.getNomeUsuario());
            sql = "UPDATE dbo.tbl_rfaccount SET password = CONVERT(binary, ?) WHERE id = CONVERT(binary, ?);";
            int update = tempRFUser.update(sql, usuario.getNovaSenha(), usuario.getNomeUsuario());
            return true;
        } else {
            throw new SenhaAtualIncorretaException("A senha atual informada, está incorreta.");
        }
    }
}
