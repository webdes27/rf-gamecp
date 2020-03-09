package br.com.rfreforged.ReforgedGCP.dao;

import br.com.rfreforged.ReforgedGCP.model.*;
import br.com.rfreforged.ReforgedGCP.utils.EquipamentHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class PersonagemDAO {

    @Autowired
    @Qualifier("jdbcTempRFWorld")
    private JdbcTemplate rfWorld;
    private final EquipamentHelper equipamentHelper = new EquipamentHelper();

    public List<Personagem> getPersonagem(String nomeUsuario) {
        List<Personagem> personagems = new ArrayList<>();
        String sql = "SELECT general.[TotalPlayMin]" +
                "      ,general.[PvpPoint]" +
                "      ,base.[Name] AS nome" +
                "      ,base.[Race] AS raca" +
                "      ,base.[Class] AS classe" +
                "      ,base.[Lv] AS nivel" +
                "      ,base.[Dalant] AS dinheiro" +
                "      ,base.[Gold] AS ouro" +
                "      ,base.[EK0]" +
                "      ,base.[EU0]" +
                "      ,base.[EK1]" +
                "      ,base.[EU1]" +
                "      ,base.[EK2]" +
                "      ,base.[EU2]" +
                "      ,base.[EK3]" +
                "      ,base.[EU3]" +
                "      ,base.[EK4]" +
                "      ,base.[EU4]" +
                "      ,base.[EK6]" +
                "      ,base.[EU6]" +
                "      ,base.[EK7]" +
                "      ,base.[EU7]" +
                "      ,general.EK0 AS GK0" +
                "      ,general.EK1 AS GK1" +
                "      ,general.EK2 AS GK2" +
                "      ,general.EK3 AS GK3" +
                "  FROM [RF_World].[dbo].[tbl_general] as general " +
                "  INNER JOIN RF_World.dbo.tbl_base as base ON general.Serial = base.Serial " +
                "  AND base.Account = ? AND base.DCK = 0";

        try {
            rfWorld.query(sql, new Object[]{nomeUsuario}, resultSet -> {
                Personagem p = new Personagem();
                equipamentSetter(resultSet, p);
                personagems.add(p);
            });
        } catch (DataAccessException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage());
        }
        return personagems;
    }

    public Personagem buscaEquipamento(String nomePersonagem) {
        Personagem p = new Personagem();

        String sql = "SELECT general.[TotalPlayMin]" +
                "      ,general.[PvpPoint]" +
                "      ,base.[Name] AS nome" +
                "      ,base.[Race] AS raca" +
                "      ,base.[Class] AS classe" +
                "      ,base.[Lv] AS nivel" +
                "      ,base.[Dalant] AS dinheiro" +
                "      ,base.[Gold] AS ouro" +
                "      ,base.[EK0]" +
                "      ,base.[EU0]" +
                "      ,base.[EK1]" +
                "      ,base.[EU1]" +
                "      ,base.[EK2]" +
                "      ,base.[EU2]" +
                "      ,base.[EK3]" +
                "      ,base.[EU3]" +
                "      ,base.[EK4]" +
                "      ,base.[EU4]" +
                "      ,base.[EK6]" +
                "      ,base.[EU6]" +
                "      ,base.[EK7]" +
                "      ,base.[EU7]" +
                "      ,general.EK0 AS GK0" +
                "      ,general.EK1 AS GK1" +
                "      ,general.EK2 AS GK2" +
                "      ,general.EK3 AS GK3" +
                "  FROM [RF_World].[dbo].[tbl_general] as general " +
                "  INNER JOIN RF_World.dbo.tbl_base as base ON general.Serial = base.Serial " +
                "  AND base.[Name] = ? AND base.DCK = 0";
        try {
            rfWorld.query(sql, new Object[]{nomePersonagem}, resultSet -> {
                equipamentSetter(resultSet, p);
            });
        } catch (DataAccessException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
        return p;
    }

    private void equipamentSetter(ResultSet resultSet, Personagem p) throws SQLException {
        p.setTempoJogado(resultSet.getInt(1));
        p.setPtCaca(resultSet.getDouble(2));
        p.setNome(resultSet.getString(3));
        int numRaca = resultSet.getInt(4);
        p.setRaca(equipamentHelper.getRaca(numRaca));
        String classe = resultSet.getString(5);
        p.setClasse(Stream.of(Classe.values()).filter(r -> r.getClasse().equals(classe)).findFirst().orElseThrow());
        p.setNivel(resultSet.getInt(6));
        p.setDinheiro(resultSet.getDouble(7));
        p.setOuro(resultSet.getDouble(8));
        equipamentHelper.setaEquipamentos(p, resultSet);
        equipamentHelper.setaAcessorios(p, resultSet);
        p.setGenero(equipamentHelper.getGenero(numRaca));
        equipamentHelper.setEquipamentosNulos(p);
    }
}
