package br.com.rfreforged.ReforgedGCP.dao;

import br.com.rfreforged.ReforgedGCP.model.Permissao;
import br.com.rfreforged.ReforgedGCP.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAO {

    @Autowired
    @Qualifier("jdbcTempRFUser")
    private JdbcTemplate tempRFUser;
    @Autowired
    @Qualifier("jdbcTempGameCP")
    private JdbcTemplate tempGameCP;

    public Usuario getUsuario(String nome) {
        String sql = "SELECT ac.id, ac.name as nome, ac.password as senha, email, role.id as idRole, role.name as roleName" +
                " FROM dbo.tbl_useraccount ac INNER JOIN dbo.tbl_role role ON role.id = ac.id_role" +
                " WHERE ac.name = ?";
        Usuario u = new Usuario();
        tempRFUser.query(sql, new Object[]{nome}, resultSet -> {
            u.setSenha(resultSet.getString("senha"));
            u.setEmail(resultSet.getString("email"));
            u.setNome(resultSet.getString("nome"));
            Permissao p = new Permissao(resultSet.getInt("idRole"), resultSet.getString("roleName"));
            u.setRole(p);
        });

        if (u.getNome() == null || u.getNome().isEmpty()) {
            return null;
        }

        return u;
    }

    public boolean criarConta(Usuario usuario) {

        String sql = "INSERT INTO dbo.tbl_rfaccount (id, password, birthdate, Email) " +
                "VALUES CONVERT(binary, ?), CONVERT(binary, ?), '1970-01-01 00:00:00', ?)";

        int update = tempRFUser.update(sql, usuario.getNome(), usuario.getSenha(), usuario.getEmail());

        sql = "INSERT INTO dbo.tbl_useraccount (name, password, email, id_role) values (?, ?, ?, ?)";

        update = tempGameCP.update(sql, usuario.getNome(), new BCryptPasswordEncoder().encode(usuario.getSenha()), usuario.getEmail(), 2);

        return update > 0;

    }

}
