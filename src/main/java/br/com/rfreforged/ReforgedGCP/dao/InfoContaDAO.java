package br.com.rfreforged.ReforgedGCP.dao;

import br.com.rfreforged.ReforgedGCP.model.usuario.DetalheConta;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class InfoContaDAO {

    @Autowired
    @Qualifier("jdbcTempRFUser")
    private JdbcTemplate rfUser;

    @Autowired
    @Qualifier("jdbcTempBilling")
    private JdbcTemplate rfBilling;

    public DetalheConta getDetalheConta(String nomeUsuario) {
        String sql = "SELECT lastlogintime, lastlogofftime FROM RF_User.dbo.tbl_UserAccount WHERE CONVERT(varchar, id) = ?;";
        DetalheConta detalheConta = new DetalheConta();
        try {
            rfUser.query(sql, new Object[]{nomeUsuario}, resultSet -> {
                detalheConta.setUltimoLogon(resultSet.getTimestamp(1));
                detalheConta.setUltimoLogoff(resultSet.getTimestamp(2));
            });

            sql = "SELECT Status ,DTEndPrem, Cash FROM BILLING.dbo.tbl_UserStatus WHERE id = ?;";
            rfBilling.query(sql, new Object[]{nomeUsuario}, resultSet -> {
                detalheConta.setPremium(resultSet.getInt(1) > 1);
                detalheConta.setDataFinalPremium(resultSet.getTimestamp(2));
                detalheConta.setCashPoint(resultSet.getDouble(3));
            });
        } catch (DataAccessException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage());
        }
        detalheConta.setStatusPersonagem(detalheConta.getUltimoLogon().after(detalheConta.getUltimoLogoff()));
        detalheConta.setNome(nomeUsuario);
        return detalheConta;
    }

}
