import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import api.BitcoinApi;
import conversor.ConversorJsonEstatisticas;
import conversor.ConversorXmlEstatisticas;
import model.EstatisticasTransacoes;
import model.Transacao;
import persistencia.PersistenciaTransacao;
import service.EstatisticasBitcoin;
import service.HtmlEstatisticasBitcoin;
import util.GeradorArquivo;

public class Main {

    public static void main(String[] args) {

        BitcoinApi BitcoinApi = new BitcoinApi();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o período em dias que deseja buscar as transações: ");
        int iPeriodoDias = scanner.nextInt();

        PersistenciaTransacao PersistenciaTransacao = new PersistenciaTransacao();

        if (PersistenciaTransacao.existeExecucaoHoje(iPeriodoDias)) {
            System.out.println("Já foi gerada uma execução hoje para o período de " + iPeriodoDias + " dias");
            System.out.println("Fim do Programa");
            return;
        }

        List<Transacao> dados = BitcoinApi.buscaTransacoes(iPeriodoDias);

        System.out.println("Registros obtidos: " + dados.size());

        dados.stream().limit(iPeriodoDias).forEach(d ->
                System.out.println(
                        d.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " -> " + d.getQtdTransacoes()
                )
        );

        EstatisticasBitcoin estatisticasBitcoin = new EstatisticasBitcoin();

        List<Long> valores = dados.stream()
                .map(Transacao::getQtdTransacoes)
                .toList();

        estatisticasBitcoin.calcular(valores);

        HtmlEstatisticasBitcoin htmlEstatisticasBitcoin = new HtmlEstatisticasBitcoin();
        htmlEstatisticasBitcoin.gerarHtml(iPeriodoDias, dados, estatisticasBitcoin, valores);

        EstatisticasTransacoes estatisticasTransacoes = new EstatisticasTransacoes(iPeriodoDias, dados, estatisticasBitcoin);

        String sJson = ConversorJsonEstatisticas.converter(estatisticasTransacoes);
        String sXml  = ConversorXmlEstatisticas.converter(estatisticasTransacoes);

        GeradorArquivo.salvarJson(sJson, iPeriodoDias);
        GeradorArquivo.salvarXml(sXml, iPeriodoDias);

        PersistenciaTransacao.salvar(iPeriodoDias, sJson, sXml);
    }
}