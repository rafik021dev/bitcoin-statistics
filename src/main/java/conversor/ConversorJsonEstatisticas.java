package conversor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.EstatisticasTransacoes;

public class ConversorJsonEstatisticas {

    public static String converter(EstatisticasTransacoes estatisticasTransacoes) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.registerModule(new JavaTimeModule());

            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(estatisticasTransacoes);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter execução para JSON", e);
        }
    }

}
