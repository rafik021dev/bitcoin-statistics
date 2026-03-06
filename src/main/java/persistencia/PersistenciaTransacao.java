package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersistenciaTransacao {

    public void salvar(int periodoDias, String json, String xml) {

        String sSql =
                "INSERT INTO estatisticas " +
                        "(periodo_dias, dados_json, dados_xml) " +
                        "VALUES (?, ?::JSONB, ?::XML)";

        try (Connection oConexao = Conexao.conectar();
             PreparedStatement oPrepStat = oConexao.prepareStatement(sSql)) {

            oPrepStat.setInt(1, periodoDias);
            oPrepStat.setString(2, json);
            oPrepStat.setString(3, xml);

            oPrepStat.executeUpdate();

            System.out.println("Dados salvos no Banco de Dados");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean existeExecucaoHoje(int iPeriodoDias) {

        String sSql =
                " SELECT 1 " +
                   "FROM estatisticas " +
                  "WHERE periodo_dias = ? " +
                    "AND DATE(data_execucao) = CURRENT_DATE " +
                  "LIMIT 1 ";

        try (Connection conn = Conexao.conectar();
             PreparedStatement oPrepStat = conn.prepareStatement(sSql)) {

            oPrepStat.setInt(1, iPeriodoDias);

            ResultSet oResSet = oPrepStat.executeQuery();
            return oResSet.next();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar se já teve uma execução hoje", e);
        }
    }
}