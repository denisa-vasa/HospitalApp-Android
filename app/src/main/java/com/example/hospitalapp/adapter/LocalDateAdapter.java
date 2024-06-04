package com.example.hospitalapp.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import java.io.IOException;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        if (localDate != null) {
            jsonWriter.value(formatter.format(localDate));
        } else {
            jsonWriter.nullValue();
        }
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        String dateStr = jsonReader.nextString();
        if (dateStr != null && !dateStr.isEmpty()) {
            return LocalDate.parse(dateStr, formatter);
        }
        return null;
    }
}
