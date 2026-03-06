package model;

import jakarta.xml.bind.annotation.*;
import service.EstatisticasBitcoin;

import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "estatisticasTransacoes")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstatisticasTransacoes {

    @XmlTransient
    private LocalDateTime dataExecucao;

    private int periodoDias;

    @XmlElementWrapper(name = "listaTransacoes")
    @XmlElement(name = "transacao")
    private List<Transacao> transacoes;

    @XmlTransient
    private EstatisticasBitcoin estatisticas;

    public EstatisticasTransacoes() {
    }

    public EstatisticasTransacoes(int periodoDias, List<Transacao> transacoes, EstatisticasBitcoin estatisticas) {
        this.periodoDias = periodoDias;
        this.transacoes = transacoes;
        this.estatisticas = estatisticas;
    }

    public LocalDateTime getDataExecucao() {
        return dataExecucao;
    }

    public int getPeriodoDias() {
        return periodoDias;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public EstatisticasBitcoin getEstatisticasBitcoin() {
        return estatisticas;
    }
}