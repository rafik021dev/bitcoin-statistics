package service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.Transacao;

public class HtmlEstatisticasBitcoin {

    public void gerarHtml(int periodoDias, List<Transacao> dados, EstatisticasBitcoin estatisticas, List<Long> valores) {

        StringBuilder html = new StringBuilder();

        html.append("""
                
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Transações Bitcoin</title>

                    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            margin: 40px;
                            background-color: #f9f9f9;
                        }

                        h1 {
                            text-align: center;
                            margin-bottom: 40px;
                        }

                        .container {
                            display: flex;
                            gap: 30px;
                            align-items: flex-start;
                        }

                        .box {
                            background: #ffffff;
                            padding: 20px;
                            border-radius: 8px;
                            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                        }

                        .tabela {
                            width: 35%;
                        }

                        .grafico {
                            width: 65%;
                        }

                        table {
                            border-collapse: collapse;
                            width: 100%;
                        }

                        th, td {
                            border: 1px solid #ffa200;
                            padding: 10px;
                            text-align: center;
                        }

                        th {
                            background-color: #ffa200;
                            color: #000;
                        }
                    </style>

                    <script type="text/javascript">
                        google.charts.load('current', {'packages':['corechart']});
                        google.charts.setOnLoadCallback(drawChart);

                        function drawChart() {
                            var data = google.visualization.arrayToDataTable([
                                ['Data', 'Transações'],
                """);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dados.stream().limit(periodoDias).forEach(d ->
                html.append("['")
                        .append(d.getDate().format(formatter))
                        .append("', ")
                        .append(d.getQtdTransacoes())
                        .append("],\n")
        );

        html.append("""
                            ]);

                            var options = {
                                title: 'Transações de Bitcoin',
                                curveType: 'function',
                                legend: { position: 'bottom' },
                                colors: ['#ffa200']
                            };

                            var chart = new google.visualization.LineChart(
                                document.getElementById('chart')
                            );

                            chart.draw(data, options);
                        }
                    </script>
                </head>

                <body>

                    <h1>Estatísticas de Transações Bitcoin</h1>

                    <div class="container">

                        <div class="box tabela">
                            <h2>Resumo Estatístico</h2>
                            <table>
                                <tr><th>Métrica</th><th>Valor</th></tr>
                """);

        html.append("<tr><td>Média</td><td>").append(estatisticas.getMedia()).append("</td></tr>");
        html.append("<tr><td>Mediana</td><td>").append(estatisticas.getMediana()).append("</td></tr>");
        html.append("<tr><td>Moda</td><td>").append(estatisticas.getModa()).append("</td></tr>");
        html.append("<tr><td>Máximo</td><td>").append(estatisticas.getMaximo()).append("</td></tr>");
        html.append("<tr><td>Mínimo</td><td>").append(estatisticas.getMinimo()).append("</td></tr>");
        html.append("<tr><td>Variância</td><td>").append(estatisticas.getVariancia()).append("</td></tr>");
        html.append("<tr><td>Desvio Padrão</td><td>").append(estatisticas.getDesvioPadrao()).append("</td></tr>");

        html.append("""
                            </table>
                        </div>

                        <div class="box grafico">
                            <div id="chart" style="width: 100%; height: 500px;"></div>
                        </div>

                    </div>

                </body>
                </html>
                """);

        try (FileWriter writer = new FileWriter("bitcoin.html")) {
            writer.write(html.toString());
            System.out.println("Arquivo bitcoin.html gerado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}