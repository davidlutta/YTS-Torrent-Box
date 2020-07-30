package com.davidlutta.ytsapp.util;

import androidx.room.TypeConverter;

import com.davidlutta.ytsapp.models.movies.Torrent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static List<Torrent> torrentFromString(String val) {
        Type listType = new TypeToken<List<Torrent>>(){}.getType();
        return new Gson().fromJson(val, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static String torrentFromArrayList(List<Torrent> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
