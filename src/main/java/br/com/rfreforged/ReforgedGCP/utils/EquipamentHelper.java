package br.com.rfreforged.ReforgedGCP.utils;

import br.com.rfreforged.ReforgedGCP.model.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class EquipamentHelper {

    private final DataFormatter cellDataFormatter = new DataFormatter();
    private static final String EXTENSAO = "xlsx";

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

    public String getRaca(int num) {
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
        for (int i = 0; i < 4; i++) {
            int tipo = i > 1 ? 9 : 10;
            int itemCode = resultSet.getInt("GK" + i);
            if (itemCode == -1) {
                continue;
            }
            int indicePlanilha = Math.round((itemCode - (tipo * 256)) / 65536F);
            ItemTipo itemTipo = getItemTipoByCode(tipo);
            Equipamento acessorio = getDetalhesItemFromPlanilha(itemTipo.name() + EXTENSAO, indicePlanilha);
            if (i > 1) {
                p.getAneis().add(acessorio);
            } else {
                p.getAmuletos().add(acessorio);
            }
        }
    }

    private ItemTipo getItemTipoByCode(int codigo) {
        return Stream.of(ItemTipo.values()).filter(i -> i.getCodeItem() == codigo).findFirst().orElseThrow();
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

    private Equipamento extraiEquipamento(int vlk, int vlU, int tipo) {
        Item item = getDetalhesItem(vlk, vlU, tipo);
        ItemTipo itemTipo = getItemTipoByCode(item.getTipo());
        Equipamento equipamento = getDetalhesItemFromPlanilha(itemTipo.name() + EXTENSAO, item.getCodigo());
        equipamento.setMelhoria(item.getMelhoria());
        return equipamento;
    }

    private Equipamento getDetalhesItemFromPlanilha(String nomeArquivo, int index) {
        Sheet dataTypeSheet = getPlanilhaFromFile(nomeArquivo);

        Row header = dataTypeSheet.getRow(0);
        int cellNum = header.getPhysicalNumberOfCells();
        ArrayList<String> fileHeaders = new ArrayList<>();

        for (int i = 0; i < cellNum; i++) {
            fileHeaders.add(header.getCell(i).getStringCellValue());
        }

        Equipamento equipamento = new Equipamento();
        Row currentRow = dataTypeSheet.getRow(index + 1);

        for (Cell currentCell : currentRow) {
            String coluna = fileHeaders.get(currentCell.getColumnIndex());
            if (coluna.equalsIgnoreCase("Index")) {
                equipamento.setCodigo(Integer.parseInt(cellDataFormatter.formatCellValue(currentCell)));
            } else if (coluna.equalsIgnoreCase("English Name")) {
                equipamento.setNome(cellDataFormatter.formatCellValue(currentCell));
            } else if (coluna.equalsIgnoreCase("Image ID")) {
                equipamento.setImgId(Integer.parseInt(cellDataFormatter.formatCellValue(currentCell)));
            }
        }
        return equipamento;
    }

    private Sheet getPlanilhaFromFile(String nomeArquivo) {
        try {
            OPCPackage excelFile = OPCPackage.open(new ClassPathResource(nomeArquivo).getFile());
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            return workbook.getSheetAt(0);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Item getDetalhesItem(int kValor, int uValor) {
        return this.getDetalhesItem(kValor, uValor, 0);
    }

    private Item getDetalhesItem(int kValor, int uValor, int equipTipo) {
        Item i = new Item();

        if (equipTipo == 0) {
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
        i.setCodigo((codigo & 0xffff0000) >> (2 * 4));
        i.setTipo((codigo & 0x0000ff00) >> (2 * 4));
        i.setSlot(codigo & 0x000000ff);
        return i;
    }

    private Melhoria getMelhoria(int uValue) {
        Melhoria melhoria = new Melhoria();
        String hexString = Integer.toHexString(uValue);
        int slots = Integer.parseInt(hexString.charAt(0)+"");
        String ultimoChar = String.valueOf(hexString.charAt(hexString.length() - 1));
        if (!ultimoChar.equals("F")) {
            for (int i = slots, j = 0; i >= 1; i--, j++) {
                int finalI = i;
                melhoria.getTalica()[j] = Stream.of(Talica.values())
                        .filter(t -> t.getNum().equals(hexString.charAt(finalI)+""))
                        .findFirst().orElseThrow();
            }
        }
        return melhoria;
    }

}
