package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateAdapter;
import util.LocalDateXmlAdapter;

import java.time.LocalDate;

@XmlRootElement(name = "transacao")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transacao {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate date;

    private long qtdTransacoes;

    protected Transacao() {
    }

    public Transacao(LocalDate date, long qtdTransacoes) {
        this.date = date;
        this.qtdTransacoes = qtdTransacoes;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getQtdTransacoes() {
        return qtdTransacoes;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();

        return gson.toJson(this);
    }
}