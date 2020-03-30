package br.com.rfreforged.ReforgedGCP.dao;

import br.com.rfreforged.ReforgedGCP.exception.NomePersonagemException;
import br.com.rfreforged.ReforgedGCP.model.servidor.Pontos;
import br.com.rfreforged.ReforgedGCP.model.personagem.Classe;
import br.com.rfreforged.ReforgedGCP.model.equipamento.Equipamento;
import br.com.rfreforged.ReforgedGCP.model.personagem.Inventario;
import br.com.rfreforged.ReforgedGCP.model.personagem.Personagem;
import br.com.rfreforged.ReforgedGCP.model.servidor.DarItens;
import br.com.rfreforged.ReforgedGCP.utils.EquipamentHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
public class PersonagemDAO {

    @Autowired
    @Qualifier("jdbcTempRFWorld")
    private JdbcTemplate rfWorld;
    @Autowired
    @Qualifier("jdbcTempBilling")
    private JdbcTemplate billing;

    private final EquipamentHelper equipamentHelper = new EquipamentHelper();

    public Map<String, Object> getInfoBasico(int serial) {
        String sql = "SELECT b.Class as classe, b.BaseClass as classeBase FROM RF_World.dbo.tbl_base as b WHERE b.Serial = ?;";
        Map<String, Object> objects = new HashMap<>();
        rfWorld.query(sql, new Object[]{serial}, resultSet -> {
            objects.put("classe", resultSet.getString("classe"));
            objects.put("classeBase", resultSet.getString("classeBase"));
        });
        return objects;
    }

    public List<Personagem> getPersonagem(String nomeUsuario) {
        List<Personagem> personagems = new ArrayList<>();
        String sql = "SELECT general.[TotalPlayMin] as tempoJogado,general.[PvpPoint] as ptContribuicao,base.[Name] AS nome,base.[Race] AS raca,base.[Class] AS classe, " +
                " base.[BaseClass] AS classeBase, base.[Lv] AS nivel,base.[Dalant] AS dinheiro,base.[Gold] AS ouro,base.[EK0],base.[EU0],base.[EK1],base.[EU1] " +
                ",base.[EK2],base.[EU2],base.[EK3],base.[EU3],base.[EK4],base.[EU4],base.[EK6],base.[EU6],base.[EK7],base.[EU7] " +
                ",general.EK0 AS GK0,general.EK1 AS GK1,general.EK2 AS GK2,general.EK3 AS GK3,general.EK4 AS GK4,general.EK5 AS GK5, " +
                "(pvp.PvpCash + pvp.PvpTempCash) as ptCertos, sup.ActionPoint_2 as ptOuro, sup.ActionPoint_0 as ptProcessamento, " +
                "sup.ActionPoint_1 as ptCaca " +
                "FROM [RF_World].[dbo].[tbl_general] as general INNER JOIN RF_World.dbo.tbl_base as base ON general.Serial = base.Serial " +
                "AND base.Account = ? AND base.DCK = 0 " +
                "LEFT JOIN [RF_World].[dbo].[tbl_pvporderview] pvp ON pvp.serial = general.Serial " +
                "LEFT JOIN [RF_World].[dbo].[tbl_supplement] sup ON sup.Serial = pvp.serial;";

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

        String sql = "SELECT general.[TotalPlayMin] as tempoJogado" +
                "      ,general.[PvpPoint] as ptContribuicao" +
                "      ,base.[Name] AS nome" +
                "      ,base.[Race] AS raca" +
                "      ,base.[Class] AS classe" +
                "      ,base.[Lv] AS nivel" +
                "      ,base.[Dalant] AS dinheiro" +
                "      ,base.[Gold] AS ouro" +
                "      ,base.[BaseClass] AS classeBase" +
                "      ,(pvp.PvpCash + pvp.PvpTempCash) as ptCertos" +
                "      ,sup.ActionPoint_2 as ptOuro" +
                "      ,sup.ActionPoint_0 as ptProcessamento " +
                "      ,sup.ActionPoint_1 as ptCaca " +
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
                "      ,general.EK4 AS GK4" +
                "      ,general.EK5 AS GK5" +
                "  FROM [RF_World].[dbo].[tbl_general] as general " +
                "  INNER JOIN RF_World.dbo.tbl_base as base ON general.Serial = base.Serial " +
                "  AND base.[Name] = ? AND base.DCK = 0" +
                "  LEFT JOIN [RF_World].[dbo].[tbl_pvporderview] pvp ON pvp.serial = general.Serial" +
                "  LEFT JOIN [RF_World].[dbo].[tbl_supplement] sup ON sup.Serial = pvp.serial;";
        try {
            rfWorld.query(sql, new Object[]{nomePersonagem}, resultSet -> {
                equipamentSetter(resultSet, p);
            });
        } catch (DataAccessException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
        return p;
    }

    public Inventario getInventarioByNomePerso(String nome) {
        Inventario inventario = new Inventario();
        String sql = "SELECT I.[K0],I.[D0],I.[U0],I.[K1],I.[D1],I.[U1],I.[K2],I.[D2],I.[U2],I.[K3],I.[D3],I.[U3],I.[K4],I.[D4],I.[U4],I.[K5],I.[D5] " +
                ",I.[U5],I.[K6],I.[D6],I.[U6],I.[K7],I.[D7],I.[U7],I.[K8] ,I.[D8],I.[U8],I.[K9],I.[D9],I.[U9],I.[K10],I.[D10],I.[U10],I.[K11],I.[D11],I.[U11],I.[K12] " +
                ",I.[D12],I.[U12],I.[K13],I.[D13],I.[U13],I.[K14],I.[D14],I.[U14],I.[K15],I.[D15],I.[U15],I.[K16],I.[D16],I.[U16],I.[K17],I.[D17],I.[U17],I.[K18] " +
                ",I.[D18],I.[U18],I.[K19],I.[D19],I.[U19],I.[K20],I.[D20],I.[U20],I.[K21],I.[D21],I.[U21],I.[K22],I.[D22],I.[U22],I.[K23],I.[D23],I.[U23],I.[K24] " +
                ",I.[D24],I.[U24],I.[K25] ,I.[D25],I.[U25],I.[K26],I.[D26],I.[U26],I.[K27],I.[D27],I.[U27],I.[K28],I.[D28],I.[U28],I.[K29],I.[D29],I.[U29],I.[K30] " +
                ",I.[D30],I.[U30],I.[K31],I.[D31],I.[U31],I.[K32],I.[D32],I.[U32],I.[K33],I.[D33],I.[U33],I.[K34],I.[D34],I.[U34],I.[K35],I.[D35],I.[U35],I.[K36] " +
                ",I.[D36],I.[U36],I.[K37],I.[D37],I.[U37],I.[K38],I.[D38],I.[U38],I.[K39],I.[D39],I.[U39],I.[K40],I.[D40],I.[U40],I.[K41],I.[D41],I.[U41],I.[K42] " +
                ",I.[D42],I.[U42],I.[K43],I.[D43],I.[U43],I.[K44],I.[D44],I.[U44],I.[K45],I.[D45],I.[U45],I.[K46],I.[D46],I.[U46],I.[K47],I.[D47],I.[U47],I.[K48] " +
                ",I.[D48],I.[U48],I.[K49],I.[D49],I.[U49],I.[K50],I.[D50],I.[U50],I.[K51],I.[D51],I.[U51],I.[K52],I.[D52],I.[U52],I.[K53],I.[D53],I.[U53],I.[K54] " +
                ",I.[D54],I.[U54],I.[K55],I.[D55],I.[U55],I.[K56],I.[D56],I.[U56],I.[K57],I.[D57],I.[U57],I.[K58],I.[D58],I.[U58],I.[K59],I.[D59],I.[U59],I.[K60] " +
                ",I.[D60],I.[U60],I.[K61],I.[D61],I.[U61],I.[K62],I.[D62],I.[U62],I.[K63],I.[D63],I.[U63],I.[K64],I.[D64],I.[U64],I.[K65],I.[D65],I.[U65],I.[K66] " +
                ",I.[D66],I.[U66],I.[K67],I.[D67],I.[U67],I.[K68],I.[D68],I.[U68],I.[K69],I.[D69],I.[U69],I.[K70],I.[D70],I.[U70],I.[K71],I.[D71],I.[U71],I.[K72] " +
                ",I.[D72],I.[U72],I.[K73],I.[D73],I.[U73],I.[K74],I.[D74],I.[U74],I.[K75],I.[D75],I.[U75],I.[K76],I.[D76],I.[U76],I.[K77],I.[D77],I.[U77],I.[K78] " +
                ",I.[D78],I.[U78],I.[K79],I.[D79],I.[U79],I.[K80],I.[D80],I.[U80],I.[K81],I.[D81],I.[U81],I.[K82],I.[D82],I.[U82],I.[K83],I.[D83],I.[U83],I.[K84] " +
                ",I.[D84],I.[U84],I.[K85],I.[D85],I.[U85],I.[K86],I.[D86],I.[U86],I.[K87],I.[D87],I.[U87],I.[K88],I.[D88],I.[U88],I.[K89],I.[D89],I.[U89],I.[K90] " +
                ",I.[D90],I.[U90],I.[K91],I.[D91],I.[U91],I.[K92],I.[D92],I.[U92],I.[K93],I.[D93],I.[U93],I.[K94],I.[D94],I.[U94],I.[K95],I.[D95],I.[U95],I.[K96] " +
                ",I.[D96],I.[U96],I.[K97],I.[D97],I.[U97],I.[K98],I.[D98],I.[U98],I.[K99],I.[D99],I.[U99] " +
                "FROM [RF_World].[dbo].[tbl_inven] I INNER JOIN [RF_World].[dbo].[tbl_base] as B ON I.Serial = B.Serial " +
                "WHERE B.Name = ?";
        rfWorld.query(sql, new Object[]{nome}, resultSet -> {
            for (int i = 0; i < 100; i++) {
                BigDecimal kValue = resultSet.getBigDecimal("K" + i);
                BigDecimal dValue = resultSet.getBigDecimal("D" + i);
                BigDecimal uValue = resultSet.getBigDecimal("U" + i);
                inventario.setNome(nome);
                Equipamento equipamento = equipamentHelper.extraiEquipamentoInventario(kValue.intValue(), uValue.intValue(), i);
                equipamento.setQtd(dValue.intValue());
                inventario.getEquipamentos().add(equipamento);
            }
        });
        return inventario;
    }

    public void giveItemToPlayer(long cbSerial, long dbCode, int amount, long dbUpgradeCode, long expira) {
        String sql = "INSERT INTO RF_World.dbo.tbl_ItemCharge (nAvatorSerial,nItemCode_K," +
                "nItemCode_D,nItemCode_U,dtGiveDate,dtTakeDate,DCK,Type,S,T) " +
                " VALUES(?,?,?,?,CURRENT_TIMESTAMP,'1900-01-01',0,0,0,?)";
        rfWorld.update(sql, cbSerial, dbCode, amount, dbUpgradeCode, expira);
    }

    private void equipamentSetter(ResultSet resultSet, Personagem p) throws SQLException {
        p.setTempoJogado(resultSet.getInt("tempoJogado"));
        p.setPtContribuicao(resultSet.getDouble("ptContribuicao"));
        p.setPtCertos(resultSet.getDouble("ptCertos"));
        p.setPtOuro(resultSet.getDouble("ptOuro"));
        p.setPtCaca(resultSet.getDouble("ptCaca"));
        p.setPtProcessamento(resultSet.getDouble("ptProcessamento"));
        p.setNome(resultSet.getString("nome"));
        int numRaca = resultSet.getInt("raca");
        p.setRaca(EquipamentHelper.getRaca(numRaca));
        String classe = resultSet.getString("classe");
        p.setClasse(Stream.of(Classe.values()).filter(r -> r.getClasse().equals(classe)).findFirst().orElseThrow());
        p.setNivel(resultSet.getInt("nivel"));
        p.setDinheiro(resultSet.getDouble("dinheiro"));
        p.setOuro(resultSet.getDouble("ouro"));
        equipamentHelper.setaEquipamentos(p, resultSet);
        equipamentHelper.setaAcessorios(p, resultSet);
        p.setGenero(equipamentHelper.getGenero(numRaca));
        equipamentHelper.setEquipamentosNulos(p);
    }

    public void giveMoney(long cbSerial, int amount) {
        String sql = "UPDATE RF_World.dbo.tbl_base SET Dalant = (Dalant + ?) WHERE Serial = ?;";
        rfWorld.update(sql, cbSerial, amount);
    }

    public void giveCash(long cbSerial, int amount) {
        String sql = "SELECT Account FROM RF_World.dbo.tbl_base WHERE Serial = ?;";
        String account = rfWorld.queryForObject(sql, new Object[]{cbSerial}, String.class);
        sql = "SELECT count(*) BILLING.dbo.tbl_UserStatus WHERE id = ?;";
        int hasId = billing.queryForObject(sql, new Object[]{account}, Integer.class);
        if (hasId > 0) {
            sql = "UPDATE BILLING.dbo.tbl_UserStatus SET Cash = (Cash + ?) WHERE id = ?";
            billing.update(sql, amount, account);
        } else {
            sql = "INSERT INTO BILLING.dbo.tbl_UserStatus (id, Status, Cash) VALUES (?, 1, ?) ";
            billing.update(sql, account, amount);
        }
    }

    public void giveGoldPoint(long cbSerial, int amount) {
        String sql = "UPDATE [RF_World].[dbo].[tbl_supplement] SET [ActionPoint_2] = ([ActionPoint_2] + ?) WHERE Serial = ?;";
        rfWorld.update(sql, amount, cbSerial);
    }

    public void givePoints(Pontos pontos) throws NomePersonagemException {
        String sql;
        sql = "SELECT Serial FROM RF_World.dbo.tbl_base WHERE Name = ?";
        Integer serial = rfWorld.queryForObject(sql, new Object[]{pontos.getPersonagem()}, Integer.class);
        if (serial == null) {
            throw new NomePersonagemException("Este personagem não existe.");
        }
        if (pontos.getDinheiro() > 0 || pontos.getGold() > 0) {
            sql = "UPDATE RF_World.dbo.tbl_base SET Dalant = (Dalant + ?), Gold = (Gold + ?) WHERE Name = ?;";
            rfWorld.update(sql, pontos.getDinheiro(), pontos.getGold(), pontos.getPersonagem());
        }

        if (pontos.getGoldPoint() > 0 || pontos.getHuntingPoint() > 0 || pontos.getProcessingPoint() > 0) {
            sql = "UPDATE RF_World.dbo.tbl_supplement SET ActionPoint_0  = (ActionPoint_0 + ?)," +
                    " ActionPoint_1  = (ActionPoint_1 + ?), ActionPoint_2  = (ActionPoint_2 + ?)" +
                    " WHERE Serial = ?;";
            rfWorld.update(sql, pontos.getHuntingPoint(), pontos.getProcessingPoint(), pontos.getGoldPoint(), serial);
        }
        if (pontos.getPvpPoint() > 0) {
            sql = "UPDATE RF_World.dbo.tbl_pvporderview SET PvpCash = (PvpCash + ?) " +
                    "WHERE serial = ?";
            rfWorld.update(sql, pontos.getPvpPoint(), serial);
        }
        if (pontos.getCash() > 0) {
            giveCash(serial, (int) pontos.getCash());
        }
    }

    public void giveItens(DarItens darItens) throws NomePersonagemException, IOException {
        String sql;
        sql = "SELECT Serial FROM RF_World.dbo.tbl_base WHERE Name = ?";
        Integer serial = rfWorld.queryForObject(sql, new Object[]{darItens.getPersonagem()}, Integer.class);
        if (serial == null) {
            throw new NomePersonagemException("Este personagem não existe.");
        }
        EquipamentHelper helper = new EquipamentHelper();
        long dbCode = helper.serverCodeToDbCode(darItens.getPlanilha(), darItens.getCodigo(), darItens.getTipo());
        long dbUpgradeCode = helper.getDbUpgradeCode(darItens.getSlot(), darItens.getGrade(), darItens.getTalica());
        giveItemToPlayer(serial, dbCode, darItens.getQtd(), dbUpgradeCode, darItens.getExpira());
    }
}
