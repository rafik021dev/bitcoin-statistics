package conversor;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import model.EstatisticasTransacoes;

import java.io.StringWriter;

public class ConversorXmlEstatisticas {

    public static String converter(EstatisticasTransacoes estatisticasTransacoes) {
        try {
            JAXBContext context = JAXBContext.newInstance(EstatisticasTransacoes.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            marshaller.marshal(estatisticasTransacoes, writer);

            return writer.toString();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter para XML", e);
        }
    }
}
