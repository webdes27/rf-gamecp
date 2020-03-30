package br.com.rfreforged.ReforgedGCP.schedule;

import br.com.rfreforged.ReforgedGCP.dao.PersonagemDAO;
import br.com.rfreforged.ReforgedGCP.model.chipbreaker.ChipBreakerConfig;
import br.com.rfreforged.ReforgedGCP.model.chipbreaker.GiveItem;
import br.com.rfreforged.ReforgedGCP.model.chipbreaker.Item;
import br.com.rfreforged.ReforgedGCP.model.equipamento.ItemTipo;
import br.com.rfreforged.ReforgedGCP.model.equipamento.Talica;
import br.com.rfreforged.ReforgedGCP.utils.EquipamentHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CBGiveItem {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String SERVER_STATE_URL;
    private static boolean BATTLE_STARTED = false;
    private static boolean ITEM_GIVED = false;
    private EquipamentHelper equipamentHelper = new EquipamentHelper();
    private static int SCENE = 0;
    private static long CB_SERIAL = 0;
    private static final String WAR_CODES_DS = "sp,52;sw,51;kn,52.53;ax,51.52.53.54;ma,52.53;me,50";
    private static final String RANGE_CODES_DS = "bo,51;fi,54.56.58.59";
    private static final String FORCE_CODES_DS = "st,52";
    private static final String LAUNCH_CODES_DS = "lu,54;fl,54;fa,54";

    @Autowired
    private PersonagemDAO personagemDAO;

    public CBGiveItem() throws IOException {
        File scheduleConfigFile = Paths.get(System.getProperty("user.dir") + "\\config\\schedule_config.json").toFile();
        ChipBreakerConfig config = objectMapper.readValue(scheduleConfigFile, ChipBreakerConfig.class);
        SERVER_STATE_URL = config.getServerStateUrl();
    }

    private ChipBreakerConfig getChipBreakerConfig() throws IOException {
        File scheduleConfigFile;
        try {
            scheduleConfigFile = Paths.get(System.getProperty("user.dir") + "\\config\\schedule_config.json").toFile();
            return objectMapper.readValue(scheduleConfigFile, ChipBreakerConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Scheduled(fixedDelay = 60000L)
    public void checaInicioCW() {
        carregaArquivo();
        if (CBGiveItem.ITEM_GIVED && CBGiveItem.SCENE == 1) {
            CBGiveItem.ITEM_GIVED = false;
        }
        if (CBGiveItem.SCENE >= 1 && CBGiveItem.SCENE <= 3 && !CBGiveItem.BATTLE_STARTED) {
            if (!CBGiveItem.ITEM_GIVED) {
                CBGiveItem.BATTLE_STARTED = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        try {
                            carregaArquivo();
                            if (CBGiveItem.SCENE < 4 && CBGiveItem.BATTLE_STARTED) {
                                if (chipHasBreak(CBGiveItem.CB_SERIAL)) {
                                    giveItemsToPlayer(CBGiveItem.CB_SERIAL);
                                    CBGiveItem.ITEM_GIVED = true;
                                }
                            }
                            CBGiveItem.BATTLE_STARTED = false;
                            timer.cancel();
                            timer.purge();
                        } catch (Exception e) {
                            CBGiveItem.ITEM_GIVED = false;
                            CBGiveItem.BATTLE_STARTED = false;
                            timer.cancel();
                            timer.purge();
                        }
                    }
                }, 20000L, 90000000L);
            }
        }
    }

    private void carregaArquivo() {
        List<String> lines = serverStateFileLines();
        if (lines.size() > 0) {
            CBGiveItem.SCENE = Integer.parseInt(lines.get(39).split("=")[1]);
            CBGiveItem.CB_SERIAL = Long.parseLong(lines.get(54).split("=")[1]);
        }
    }

    private void giveItemsToPlayer(long cbSerial) throws IOException {
        GiveItem giveItem = sortGiveItem();
        Map<String, Object> infoBasico = personagemDAO.getInfoBasico((int) cbSerial);
        assert giveItem != null;
        List<String> itemsCode = giveItem.getItems().stream().map(Item::getCode).collect(Collectors.toList());
        String[] letrasArma = new String[itemsCode.size()];
        String classeBase = infoBasico.get("classeBase").toString();
        String classeAtual = infoBasico.get("classe").toString();
        setaLetrasMeioByRaca(itemsCode, letrasArma, classeBase, classeAtual);
        for (int i = 0; i < letrasArma.length; i++) {
            Item item = giveItem.getItems().get(i);
            switch (itemsCode.get(i)) {
                case "gp":
                    personagemDAO.giveGoldPoint(cbSerial, item.getAmount());
                    break;
                case "cash":
                    personagemDAO.giveCash(cbSerial, item.getAmount());
                    break;
                case "money":
                    personagemDAO.giveMoney(cbSerial, item.getAmount());
                    break;
                default:
                    giveItem(cbSerial, giveItem, itemsCode, letrasArma, i);
                    break;
            }

        }
    }

    private void giveItem(long cbSerial, GiveItem giveItem, List<String> itemsCode, String[] letrasArma, int i) throws IOException {
        String novoCodigo = formataServerCodeByRaca(itemsCode, letrasArma, i);
        ItemTipo itemTipo = getItemTipo(novoCodigo);
        long idxPlanilha = equipamentHelper.getIndexFromPlanilha(itemTipo.getArquivo(), novoCodigo);
        long itemDBCode = equipamentHelper.getItemDBCode(idxPlanilha, itemTipo.getCodeItem());
        Item item = giveItem.getItems().get(i);

        long dbUpgradeCode = equipamentHelper.getDbUpgradeCode(item.getSlots(), item.getGrade(), item.getTalic());
        personagemDAO.giveItemToPlayer(cbSerial, itemDBCode, item.getAmount(), dbUpgradeCode, 0);
    }

    private ItemTipo getItemTipo(String novoCodigo) {
        return Stream.of(ItemTipo.values()).filter(t -> {
            if (t.getLetra().length() > 1 && t.getLetra().length() < 4) {
                String inicio = novoCodigo.substring(0, 2);
                return t.getLetra().equals(inicio);
            } else if (t.getLetra().length() == 4) {
                String inicio = novoCodigo.substring(0, 4);
                return t.getLetra().equals(inicio);
            } else {
                String inicio = novoCodigo.substring(1, 2);
                return t.getLetra().equals(inicio);
            }
        }).findFirst().orElseThrow();
    }

    private String formataServerCodeByRaca(List<String> itemsCode, String[] letrasArma, int i) {
        String newCode;
        if (letrasArma[i] == null) {
            newCode = itemsCode.get(i);
        } else {
            if (letrasArma[i].length() > 2) {
                newCode = letrasArma[i];
            } else {
                String codigo = itemsCode.get(i);
                String inicio = codigo.substring(0, 2);
                String fim = codigo.substring(4, 7);
                newCode = inicio + letrasArma[i] + fim;
            }
        }
        return newCode;
    }

    private void setaLetrasMeioByRaca(List<String> itemsCode, String[] letrasArma, String classeBase, String classeAtual) {
        for (int i = 0; i < itemsCode.size(); i++) {
            if (isWeapon(itemsCode.get(i))) {
                letrasArma[i] = getLetrasMeioArma(itemsCode.get(i), classeBase, classeAtual);
            } else if (isArmor(itemsCode.get(i))) {
                letrasArma[i] = getLetrasMeioArmor(itemsCode.get(i), classeAtual, classeBase);
            } else if (isPotion(itemsCode.get(i))) {
                letrasArma[i] = getLetrasMeioPotion(itemsCode.get(i), classeBase);
            }
        }
    }

//    private String getLetrasMeioScrolls(String serverCode, String classeBase) {
//        String letraScroll;
//        int num = Integer.parseInt(serverCode.charAt(5) + serverCode.charAt(6) + "");
//
//        if (num == 4 || num == 7 || num == 13 || num == 16 || num == 20 || num == 21 ||
//                num == 27 || num == 29 || num == 33 || num == 38 || num == 39 || num == 10) {
//
//        } else if (num == 5 || num == 8 || num == 11 || num == 14 || num == 17 || num == 18 || num == 22 ||
//                num == 25 || num == 30 || num == 31 || num == 34 || num == 37 || num == 40) {
//
//        } else {
//
//        }
//    }

    private String getLetrasMeioPotion(String serverCode, String classeBase) {
        String letrasPot;
        String meio = serverCode.charAt(3) + serverCode.charAt(4) + "";
        String formataStringRaca = (classeBase.charAt(0) + serverCode.charAt(3) + "").toLowerCase();
        if (meio.equals("hp") || meio.equals("fp") || meio.equals("sp")) {
            letrasPot = formataStringRaca;
        } else if (serverCode.contains("csa") || serverCode.contains("bax")
                || serverCode.contains("cha") || serverCode.contains("bco")
                || serverCode.contains("bdx") || serverCode.contains("eex")
                || serverCode.contains("bde") || serverCode.contains("bae")
                || serverCode.contains("qsa")) {
            letrasPot = getPotionRaceSequence(serverCode, classeBase);
        } else if (serverCode.charAt(2) != 'a' && serverCode.charAt(2) != 'b' && serverCode.charAt(2) != 'c') {
            letrasPot = formataStringRaca;
        } else {
            letrasPot = serverCode;
        }
        return letrasPot;
    }

    private String getPotionRaceSequence(String serverCode, String classeBase) {
        String substring = serverCode.substring(5, 7);
        int num = Integer.parseInt(substring);
        int myRaceNum = getCodigoRaca(classeBase);
        int codeRace;

        if (isPotionFromRace(1, num) != 0) {
            codeRace = isPotionFromRace(1, num);
            if (myRaceNum == 2) {
                codeRace = codeRace + 1;
            } else if (myRaceNum == 3) {
                codeRace = codeRace + 2;
            }
        } else if (isPotionFromRace(2, num) != 0) {
            codeRace = isPotionFromRace(2, num);
            if (myRaceNum == 1) {
                codeRace = codeRace - 1;
            } else if (myRaceNum == 3) {
                codeRace = codeRace + 1;
            }
        } else {
            codeRace = isPotionFromRace(3, num);
            if (myRaceNum == 1) {
                codeRace = codeRace - 2;
            } else if (myRaceNum == 2) {
                codeRace = codeRace - 1;
            }
        }
        return serverCode.substring(0, serverCode.length() - 2) + (codeRace < 10 ? "0" + codeRace : codeRace);
    }

    private int isPotionFromRace(int comp, int myRaceNum) {
        for (int i = comp; i < 73; i = i + 3) {
            if (i == myRaceNum) {
                return i;
            }
        }
        return 0;
    }

    private int getCodigoRaca(String classeBase) {
        int comp;
        if (classeBase.charAt(0) == 'A') {
            comp = 3;
        } else if (classeBase.charAt(0) == 'B') {
            comp = 1;
        } else {
            comp = 2;
        }
        return comp;
    }

    private String getLetrasMeioArmor(String serverCode, String classeAtual, String classeBase) {
        String letrasArma;
        if (serverCode.charAt(2) != 'a' && serverCode.charAt(2) != 'b' && serverCode.charAt(2) != 'c') {
            letrasArma = (serverCode.charAt(2) + serverCode.charAt(3) + "").toLowerCase();
        } else {
            if (classeAtual.equals("ARS1")) {
                letrasArma = (classeBase.charAt(0) + "").toLowerCase() + "f";
            } else {
                letrasArma = classeBase.substring(0, 1).toLowerCase();
            }
        }
        return letrasArma;
    }

    private String getLetrasMeioArma(String serverCode, String classeBase, String classeAtual) {

        String novasLetras;
        String nums = serverCode.substring(5, 7);
        String letraMeio = serverCode.substring(2, 4);

        boolean notSpecialItemType = Integer.parseInt(nums) <= 55 &&
                serverCode.charAt(4) != 'd' && serverCode.charAt(4) != 's';

        if (classeAtual.equals("ARS1")) {
            if (serverCode.charAt(4) == 'd') {
                if (Stream.of(LAUNCH_CODES_DS.split(";"))
                        .anyMatch(l -> l.split(",")[0].equals(letraMeio))) {
                    novasLetras = serverCode;
                } else {
                    novasLetras = "iwlud54";
                }
            } else if (notSpecialItemType) {
                novasLetras = "lu";
            } else {
                novasLetras = serverCode;
            }
        } else {
            if (classeBase.charAt(1) == 'F') {
                if (serverCode.charAt(4) == 'd') {
                    if (Stream.of(FORCE_CODES_DS.split(";"))
                            .anyMatch(l -> l.split(",")[0].equals(letraMeio))) {
                        novasLetras = serverCode;
                    } else {
                        novasLetras = "iwstd52";
                    }
                } else if (notSpecialItemType) {
                    novasLetras = "st";
                } else {
                    novasLetras = serverCode;
                }
            } else if (classeBase.charAt(1) == 'W') {
                if (serverCode.charAt(4) == 'd') {
                    if (Stream.of(WAR_CODES_DS.split(";"))
                            .anyMatch(l -> l.split(",")[0].equals(letraMeio))) {
                        novasLetras = serverCode;
                    } else {
                        novasLetras = "iwspd54";
                    }
                } else if (notSpecialItemType) {
                    novasLetras = "sp";
                } else {
                    novasLetras = serverCode;
                }
            } else {
                if (serverCode.charAt(4) == 'd') {
                    if (Stream.of(RANGE_CODES_DS.split(";"))
                            .anyMatch(l -> l.split(",")[0].equals(letraMeio))) {
                        novasLetras = serverCode;
                    } else {
                        novasLetras = "iwfid54";
                    }
                } else if (notSpecialItemType) {
                    novasLetras = "bo";
                } else {
                    novasLetras = serverCode;
                }
            }
        }
        return novasLetras;
    }

    private boolean isWeapon(String serverCode) {
        return serverCode.startsWith("iw");
    }

    private boolean isPotion(String serverCode) {
        return serverCode.startsWith("ip");
    }

    private boolean isScrolls(String serverCode) {
        return serverCode.startsWith("iq");
    }

    private boolean isArmor(String serverCode) {
        return serverCode.startsWith("iu") || serverCode.startsWith("il") ||
                serverCode.startsWith("is") || serverCode.startsWith("ig") ||
                serverCode.startsWith("ih") || serverCode.startsWith("id");
    }

    private GiveItem sortGiveItem() throws IOException {
        BATTLE_STARTED = false;
        ChipBreakerConfig cbConfig = getChipBreakerConfig();
        List<GiveItem> giveItems = cbConfig.getGiveItems();
        SecureRandom random = new SecureRandom();
        double numeroSorteado = random.nextDouble();
        for (int i = 0; i < giveItems.size(); i++) {
            GiveItem itemAtual = giveItems.get(i);
            if (i <= 0) {
                if (numeroSorteado <= (itemAtual.getChance() / 100)) {
                    return itemAtual;
                }
            } else {
                GiveItem itemAnterior = giveItems.get(i - 1);
                if (numeroSorteado <= ((itemAtual.getChance() / 100) + (itemAnterior.getChance() / 100))) {
                    return itemAtual;
                }
            }
        }
        return null;
    }


    private boolean chipHasBreak(long chipBreaker) {
        return chipBreaker > 1 && chipBreaker < 4294967295L;
    }

    private List<String> serverStateFileLines() {
        File serverStateFile = Paths.get(SERVER_STATE_URL).toFile();
        try (BufferedReader bis = new BufferedReader(new FileReader(serverStateFile))) {
            return bis.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            return new ArrayList<>();
        } catch (UncheckedIOException ignored) {}
        return new ArrayList<>();
    }

}

