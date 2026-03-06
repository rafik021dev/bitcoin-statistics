package util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Adaptador pra converter obj LocalDate para JSON e vice-versa.
 * Class necessária pra corrigir o erro de conversão de data q tava dando no bglh do gson
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());
        }
    }

    public LocalDate read(JsonReader in) throws IOException {
        return LocalDate.parse(in.nextString());
    }
}