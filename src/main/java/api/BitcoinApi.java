package api;

import model.Transacao;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BitcoinApi {

    private static final String API_URL_BASE =
            "https://api.blockchain.info/charts/n-transactions";

    public List<Transacao> buscaTransacoes(int periodoDias) {

        LocalDate dataFinal = LocalDate.now();
        LocalDate dataInicial = dataFinal.minusDays(periodoDias);

        String sUrl = API_URL_BASE + "?timespan=" + periodoDias + "days&format=json";

        List<Transacao> dados = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(sUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonArray values = root.getAsJsonArray("values");

            for (int i = 0; i < values.size(); i++) {

                JsonObject obj = values.get(i).getAsJsonObject();

                long timestamp = obj.get("x").getAsLong();
                long quantidade = obj.get("y").getAsLong();

                LocalDate date = Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                dados.add(new Transacao(date, quantidade));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dados;
    }
}
