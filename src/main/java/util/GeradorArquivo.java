package util;

import java.io.FileWriter;
import java.io.IOException;

public class GeradorArquivo {

    public static void salvarJson(String sJson, int iPeriodoDias) {
        String sNomeArquivo = "estatisticas_json_" + iPeriodoDias + "dias_" + java.time.LocalDate.now();
        try (FileWriter writer = new FileWriter(sNomeArquivo)) {
            writer.write(sJson);
            System.out.println("JSON salvo em: " + sNomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarXml(String sXml, int iPeriodoDias) {
        String sNomeArquivo = "estatisticas_xml_" + iPeriodoDias + "dias_" + java.time.LocalDate.now();
        try (FileWriter writer = new FileWriter(sNomeArquivo)) {
            writer.write(sXml);
            System.out.println("XML salvo em: " + sNomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
