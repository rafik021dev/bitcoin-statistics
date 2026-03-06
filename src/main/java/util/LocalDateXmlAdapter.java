package util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * Corrige o erro de conversão do LocalDate igual o adapter do JSON
 */
public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) {
        return (v == null || v.isBlank()) ? null : LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) {
        return v != null ? v.toString() : null;
    }
}