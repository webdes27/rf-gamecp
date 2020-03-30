package br.com.rfreforged.ReforgedGCP.dao;

import br.com.rfreforged.ReforgedGCP.model.servidor.*;
import br.com.rfreforged.ReforgedGCP.model.usuario.Banido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ServerDAO {

    @Autowired
    @Qualifier("jdbcTempRFUser")
    private JdbcTemplate jdbcRFUser;
    @Autowired
    @Qualifier("jdbcTempRFWorld")
    private JdbcTemplate jdbcRFWorld;

    public ServerStats getEstatisticas() {

        String sql = "SELECT nMaxOn FROM [RF_User].[dbo].[tbl_ServerUser_Log]";
        final List<Integer> maxOn = new ArrayList<>();
        jdbcRFUser.query(sql, new Object[]{}, resultSet -> {
            maxOn.add(resultSet.getInt(1));
        });
        ServerStats stats1 = new ServerStats();
        int maior = 0;
        try {
            File fileConfig = Paths.get(System.getProperty("user.dir") + "\\config\\schedule_config.json").toFile();
            Map<String, String> config = new ObjectMapper().readValue(fileConfig, new TypeReference<>() {});
            String serverStateUrl = config.get("serverStateUrl");
            File file = Paths.get(serverStateUrl).toFile();
            maior = getOnlinePl(maxOn, stats1, maior, file);
            if (maxOn.size() == 0) {
                sql = "INSERT INTO [RF_User].[dbo].[tbl_ServerUser_Log] ([dtDate],[nAverageUser],[nMaxUser] " +
                        ",[ServerName],[nBellaUser],[nCoraUser],[nAccUser],[nMaxOn]) VALUES ('2020-03-03',0,0,'S',0,0,0," + maior + ") ";
                jdbcRFUser.update(sql);
            } else if (maior > 0) {
                sql = "UPDATE [RF_User].[dbo].[tbl_ServerUser_Log] SET nMaxOn = " + maior + " WHERE serial = 1";
                jdbcRFUser.update(sql);
            }
        } catch (Exception ignored) {}

        stats1.setMaxOn(maxOn.get(0));

        sql = "SELECT COUNT(*) FROM [RF_User].[dbo].[tbl_rfaccount]";

        ServerStats finalStats = stats1;

        jdbcRFUser.query(sql, new Object[]{}, resultSet -> {
            finalStats.setTotalContas(resultSet.getInt(1));
        });

        sql = "SELECT COUNT(base1.Serial) AS personagensExistentes," +
                " COUNT(base2.Serial) AS totalPersonagens, COUNT(base3.Serial) AS personagensDeletados " +
                "FROM [RF_World].[dbo].[tbl_base] AS base1 " +
                "RIGHT JOIN [RF_World].[dbo].[tbl_base] base2 ON base2.Serial = base1.Serial AND base2.DCK = 0 " +
                "LEFT JOIN [RF_World].[dbo].[tbl_base] base3 ON base3.Serial = base2.Serial AND base3.DCK = 1";
        jdbcRFWorld.query(sql, new Object[]{}, resultSet -> {
            finalStats.setTotalPersonagens(resultSet.getInt("totalPersonagens"));
            finalStats.setPersonagensExistentes(resultSet.getInt("personagensExistentes"));
            finalStats.setPersonagensDeletados(resultSet.getInt("personagensDeletados"));
        });

        return finalStats;
    }

    private int getOnlinePl(List<Integer> maxOn, ServerStats stats1, int maior, File file) {
        try (BufferedReader bis = new BufferedReader(new FileReader(file))) {
            String linha;
            boolean stop = false;
            while ((linha = bis.readLine()) != null || stop) {
                if (linha != null && linha.contains("UserNum")) {
                    int numUser = Integer.parseInt(linha.split("=")[1]);
                    linha = bis.readLine();
                    int bell = Integer.parseInt(linha.split("=")[1]);
                    linha = bis.readLine();
                    int cora = Integer.parseInt(linha.split("=")[1]);
                    linha = bis.readLine();
                    int acc = Integer.parseInt(linha.split("=")[1]);
                    if (maxOn.size() == 0 || maxOn.get(0) < numUser) {
                        maior = numUser;
                        stats1.setMaxOn(maior);
                    } else {
                        stats1.setMaxOn(maxOn.get(0));
                    }
                    stats1.setAcc(acc);
                    stats1.setCora(cora);
                    stats1.setBell(bell);
                    stop = true;
                }
            }
        } catch (Exception ignored) {
            return getOnlinePl(maxOn, stats1, maior, file);
        }
        return maior;
    }

    public List<TopOnline> getTopOnlines() {
        String sql = "SELECT TOP 30 (g.[TotalPlayMin] * 60) AS tempoJogo," +
                "       guild.profile as guild," +
                "       b.Name AS nomePersonagem," +
                "       b.Race as racaNum," +
                "       b.Class as classeString," +
                "       b.Lv as nivel," +
                "       g.PvpPoint as ptContribuicao, " +
                "       b.isOnline as status " +
                "  FROM [RF_World].[dbo].[tbl_general] AS g INNER JOIN [RF_World].[dbo].[tbl_base] as b" +
                "  ON g.Serial = b.Serial AND b.DCK = 0 LEFT JOIN [RF_World].[dbo].[tbl_Guild] as guild ON g.GuildSerial = guild.Serial" +
                "  WHERE b.Account NOT LIKE '!%' " +
                "  ORDER BY g.[TotalPlayMin] DESC;";

        return jdbcRFWorld.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(TopOnline.class));
    }

    public List<Banido> getListaBanidos(String parametroQuery) {
        String sql = "SELECT * FROM RF_User.dbo.vban_list";
        Object[] params = new Object[]{};
        if (parametroQuery != null && !parametroQuery.isBlank()) {
            sql += " WHERE nome_usuario like ? OR GM like ? personagens like ?";
            params = new Object[]{"%[" + parametroQuery + "]%"};
        }
        try {
            return jdbcRFUser.query(sql, params, new BeanPropertyRowMapper<>(Banido.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public GuerraChipStats getGuerraChipStats() {
        String sql = "SELECT " +
                "SUM((case when winrace = 0 then 1 else 0 end)) AS bwins," +
                "SUM((case when winrace = 1 then 1 else 0 end)) AS cwins," +
                "SUM((case when winrace = 2 then 1 else 0 end)) AS awins," +
                "SUM((case when winrace = 0 then 1 else 0 end)) AS bloses," +
                "SUM((case when winrace = 1 then 1 else 0 end)) AS closes," +
                "SUM((case when winrace = 2 then 1 else 0 end)) AS aloses," +
                "SUM((case when winrace = 255 AND loserace = 255 then 1 else 0 end)) AS draw " +
                "FROM RF_World.dbo.racebattle_log_view;";
        List<GuerraChipStats> query = jdbcRFWorld.query(sql, new Object[]{},
                new BeanPropertyRowMapper<>(GuerraChipStats.class));
        if (query.size() == 0) {
            return null;
        }
        return query.get(0);
    }

    public List<HistoricoCW> getHistoricoCW() {
        String sql = "SELECT TOP 10 [starttime] as data_inicio, " +
                "(case when winrace = 0 then 'Bellato' else case when winrace = 1 then 'Cora' else" +
                " case when winrace = 2 then 'Accretia' else 'Empate' end end end) " +
                "as raca_vencedora," +
                "(case when loserace = 0 then 'Bellato' else case when loserace = 1 then 'Cora' else " +
                " case when loserace = 2 then 'Accretia' else 'Empate' end end end) " +
                "as raca_perdedora,time_elapsed as tempo_decorrido, regdate as data_fim FROM RF_World.dbo.racebattle_log_view " +
                "ORDER BY starttime DESC;";
        return jdbcRFWorld.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(HistoricoCW.class));
    }

    public Concelhos getConcelhos() {
        String sql = "SELECT (case when P.race = 0 then 'Bellato' else case when P.race = 1 then 'Cora' else " +
                "case when P.race = 2 then 'Accretia' else 'Empate' end end end) as raca " +
                ",P.[AName] as nome_personagem " +
                ",(case when P.ClassType = 0 then 'Arconde' else case when P.ClassType = 1 OR P.ClassType = 5 then 'Concelho' else " +
                "case when P.ClassType = 2 OR P.ClassType = 6 then 'Time de Ataque' else case when P.ClassType = 3 OR P.ClassType = 7 " +
                "then 'Time de Defesa' else case when P.ClassType = 4 OR P.ClassType = 8 then 'Time de Suporte' " +
                "end end end end end) as posicao, " +
                "B.isOnline as status " +
                "FROM [RF_World].[dbo].[tbl_patriarch_candidate] AS P INNER JOIN [RF_World].[dbo].[tbl_base] AS B ON B.Serial = P.Serial;";
        List<Concelho> query = jdbcRFWorld.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(Concelho.class));
        Concelhos concelhos = new Concelhos();
        concelhos.setAcc(query.stream().filter(c -> c.getRaca().equals("Accretia")).collect(Collectors.toList()));
        concelhos.setBell(query.stream().filter(c -> c.getRaca().equals("Bellato")).collect(Collectors.toList()));
        concelhos.setCora(query.stream().filter(c -> c.getRaca().equals("Cora")).collect(Collectors.toList()));
        return concelhos;
    }


    public Optional<Integer> getTotPlayerOnline() {
        String sql = "SELECT COUNT(*) FROM [RF_World].[dbo].[tbl_base] WHERE isOnline = 1 AND Account NOT LIKE '!%';";
        return  Optional.ofNullable(jdbcRFWorld.queryForObject(sql, new Object[]{}, Integer.class));
    }
}
