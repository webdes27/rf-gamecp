package br.com.rfreforged.ReforgedGCP.utils;

import br.com.rfreforged.ReforgedGCP.model.equipamento.*;
import br.com.rfreforged.ReforgedGCP.model.personagem.Personagem;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class EquipamentHelper {

    private static final String EXTENSAO = ".csv";

    public void setEquipamentosNulos(Personagem p) {
        Stream.of(p.getClass().getDeclaredMethods())
                .filter(m ->
                        m.getName().startsWith("set")
                                && (Stream.of(m.getParameterTypes()).allMatch(par -> par.isAssignableFrom(Equipamento.class))
                                || Stream.of(m.getParameterTypes()).allMatch(par -> par.isAssignableFrom(List.class)))
                )
                .forEach(m -> {
                    String fieldName = m.getName().substring(3);
                    try {
                        Object invoke = p.getClass().getMethod("get" + fieldName).invoke(p);
                        if (invoke == null) {
                            if (m.getParameterTypes()[0].isAssignableFrom(List.class)) {
                                m.invoke(p, Arrays.asList(new Equipamento(), new Equipamento()));
                            } else {
                                m.invoke(p, new Equipamento());
                            }
                        } else if (m.getParameterTypes()[0].isAssignableFrom(List.class)) {
                            List<Equipamento> list = ((List<Equipamento>) invoke);
                            if (list.size() == 2) {
                                if (list.get(0) == null) {
                                    list.add(new Equipamento());
                                }
                                if (list.get(1) == null) {
                                    list.add(new Equipamento());
                                }
                            } else if (list.size() == 1) {
                                list.add(new Equipamento());
                            } else {
                                list.addAll(Arrays.asList(new Equipamento(),
                                        new Equipamento()));
                            }
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static String getRaca(int num) {
        switch (num) {
            case 0:
            case 1:
                return "Bellato";
            case 2:
            case 3:
                return "Cora";
            case 4:
                return "Accretia";
            default:
                return "Raça Bugado.";
        }
    }

    public String getGenero(int num) {
        switch (num) {
            case 0:
            case 2:
                return "Masculino";
            case 1:
            case 3:
                return "Feminino";
            case 4:
                return "Robô";
            default:
                return "Gênero Bugado.";
        }
    }

    public void setaAcessorios(Personagem p, ResultSet resultSet) throws SQLException {

        String classe = resultSet.getString("classeBase");
        int inicio;
        if (classe.contains("R")) {
            inicio = 2;
        } else {
            inicio = 0;
        }

        for (int i = 0; i < 4; i++, inicio++) {
            int tipo = i > 1 ? 8 : 9;
            int itemCode = resultSet.getInt("GK" + inicio);
            if (itemCode == -1) {
                continue;
            }

            int indicePlanilha = Math.round((itemCode - (tipo * 256F)) / 65536F);
            ItemTipo itemTipo = getItemTipoByCode(tipo);

            Equipamento acessorio = getDetalhesItemFromPlanilhaT("codescsv/" + itemTipo.name() + EXTENSAO, indicePlanilha);
            acessorio.setMelhoria(null);
            if (i > 1) {
                p.getAneis().add(acessorio);
            } else {
                p.getAmuletos().add(acessorio);
            }
        }
    }

    public ItemTipo getItemTipoByCode(int codigo) {
        return Stream.of(ItemTipo.values()).filter(i -> i.getCodeItem() == codigo).findFirst().orElseThrow();
    }

    public Equipamento extraiEquipamentoInventario(int kVal, int uVal, int slot) {
        if (kVal == -1) {
            return new Equipamento();
        }
        Item item = getDetalhesItem(kVal, uVal);
        ItemTipo itemTipo = getItemTipoByCode(item.getTipo());
        try {
            Equipamento equipamento = getDetalhesItemFromPlanilhaT("codescsv/" + itemTipo.name() + EXTENSAO, item.getCodigo());
            equipamento.setMelhoria(item.getMelhoria());
            return equipamento;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
    }

    public void setaEquipamentos(Personagem p, ResultSet resultSet) throws SQLException {
        for (int j = 0; j < 8; j++) {
            if (j == 5) {
                continue;
            }
            int vlU = resultSet.getInt("EU" + j);
            int vlK = resultSet.getInt("EK" + j);
            if (vlK == -1) {
                continue;
            }
            Equipamento equipamento = extraiEquipamento(vlK, vlU, j);
            switch (j) {
                case 0:
                    p.setPeito(equipamento);
                    break;
                case 1:
                    p.setCalca(equipamento);
                    break;
                case 2:
                    p.setLuva(equipamento);
                    break;
                case 3:
                    p.setBota(equipamento);
                    break;
                case 4:
                    p.setElmo(equipamento);
                    break;
                case 6:
                    p.setArma(equipamento);
                    break;
                case 7:
                    p.setCapa(equipamento);
                    break;
            }
        }
    }

    public Equipamento extraiEquipamento(int vlk, int vlU, int tipo) {
        Item item = getDetalhesItem(vlk, vlU, tipo);
        ItemTipo itemTipo = getItemTipoByCode(item.getTipo());
        Equipamento equipamento = getDetalhesItemFromPlanilhaT("codescsv/" + itemTipo.name() + EXTENSAO, item.getCodigo());
        equipamento.setMelhoria(item.getMelhoria());
        return equipamento;
    }

    private Equipamento getDetalhesItemFromPlanilhaT(String nomeArquivo, int index) throws ArrayIndexOutOfBoundsException {
        Equipamento equipamento = new Equipamento();

        String divisor = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource(nomeArquivo).getFile()))) {

            String linhaColunas = br.readLine();
            List<String> colunas = Arrays.asList(linhaColunas.replaceAll("\"", "").split(divisor));

            String linhaSelecionada = (String) br.lines().toArray()[index];

            String[] valores = linhaSelecionada.replaceAll("\"", "").split(divisor);
            for (int i = 0; i < valores.length; i++) {
                String colunaFormatada = colunas.get(i);
                if (colunaFormatada.equalsIgnoreCase("Index")) {
                    equipamento.setCodigo(!valores[i].isEmpty() ? Integer.parseInt(valores[i]) : 0);
                } else if (colunaFormatada.equalsIgnoreCase("English Name")) {
                    equipamento.setNome(valores[i]);
                } else if (colunaFormatada.equalsIgnoreCase("Image ID")) {
                    equipamento.setImgId(!valores[i].isEmpty() ? Integer.parseInt(valores[i]) : 0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return equipamento;
    }

    public long getIndexFromPlanilha(String nomeArquivo, String serverCode) throws IOException {
        String divisor = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource("codescsv/" + nomeArquivo).getFile()))) {
            String linhaSelecionada = br.lines().filter(l -> l.split(divisor)[1].equals(serverCode)).findFirst().orElseThrow();
            return Long.parseLong(linhaSelecionada.split(divisor)[0]);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Item getDetalhesItem(int kValor, int uValor) {
        return this.getDetalhesItem(kValor, uValor, -1);
    }

    public Item getDetalhesItem(int kValor, int uValor, int equipTipo) {
        Item i = new Item();

        if (equipTipo >= 0) {
            i.setCodigo(kValor);
            i.setTipo(equipTipo);
            i.setSlot(-1);
        } else {
            i = getCodigoItem(kValor);
        }

        i.setSlot(-1);
        if (uValor > -1 && kValor > -1) {
            i.setMelhoria(getMelhoria(uValor));
        }

        return i;
    }

    private Item getCodigoItem(Integer codigo) {
        Item i = new Item();
        i.setCodigo((codigo & 0xffff0000) >> (4 * 4));
        i.setTipo((codigo & 0x0000ff00) >> (2 * 4));
        i.setSlot(codigo & 0x000000ff);
        return i;
    }

    public long serverCodeToDbCode(String nomePlanilha, String serverCode, int tipo) throws IOException {
        return getItemDBCode(getIndexFromPlanilha(nomePlanilha, serverCode), tipo);
    }

    public long getItemDBCode(long index, int type) {
        return Math.round((index * 65536L) + (type * 256));
    }

    public long getDbUpgradeCode(int slots, int grade, Talica talica) {
        StringBuilder upgrades = new StringBuilder(slots == 0 ? "0" : slots + "");
        int sobra = slots - grade;
        if (slots > 0) {
            upgrades.append("F".repeat(Math.max(0, sobra)));
            if (sobra < 7) {
                upgrades.append(
                        Integer.toHexString(
                                Integer.parseInt(talica.getNum())
                        ).repeat(Math.max(0, grade)));
            }
        } else {
            upgrades.append("F".repeat(Math.max(0, 7)));
        }
        return Long.parseLong(upgrades.toString(), 16);
    }

    private Melhoria getMelhoria(int uValue) {
        Melhoria melhoria = new Melhoria();
        String hexString = Integer.toHexString(uValue);
        int slots = hex2Int(hexString.charAt(0));
        if (slots > 7) {
            return null;
        }
        String ultimoChar = String.valueOf(hexString.charAt(hexString.length() - 1));
        if (!ultimoChar.equals("F")) {
            for (int i = slots, j = 0; i >= 1; i--, j++) {
                int finalI = i;
                String talica = hexString.charAt(finalI) + "";
                melhoria.getTalica()[j] = Stream.of(Talica.values())
                        .filter(t -> t.getNum().equals(hex2Int(talica.charAt(0)) + ""))
                        .findFirst().orElseThrow();
            }
        }
        return melhoria;
    }

    private int hex2Int(char charAt) {
        try {
            return Integer.parseInt(charAt + "");
        } catch (NumberFormatException e) {
            return charAt == 'a' ? 10 : charAt == 'b' ? 11 : charAt == 'c' ? 12
                    : charAt == 'd' ? 13 : charAt == 'e' ? 14
                    : charAt == 'f' ? 15 : 0;
        }
    }
}
