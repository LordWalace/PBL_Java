package com.diariocultural.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

/**
 * Adaptador para ensinar a Gson como converter objetos Duration para JSON e vice-versa.
 * A duração é guardada como um número total de segundos.
 */
public class DurationAdapter extends TypeAdapter<Duration> {

    @Override
    public void write(JsonWriter out, Duration value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        // Guarda a duração como o número total de segundos.
        out.value(value.toSeconds());
    }

    @Override
    public Duration read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        // Lê o número de segundos do JSON e cria o objeto Duration.
        long seconds = in.nextLong();
        return Duration.ofSeconds(seconds);
    }
}