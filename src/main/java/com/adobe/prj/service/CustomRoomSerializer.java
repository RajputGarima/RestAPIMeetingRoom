package com.adobe.prj.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.adobe.prj.entity.Room;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


public class CustomRoomSerializer extends StdSerializer<List<Room>> {

    public CustomRoomSerializer() {
        this(null);
    }

    public CustomRoomSerializer(Class<List<Room>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Room> Rooms,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Room> rooms = new ArrayList<>();
        for (Room r : Rooms) {
            r.setRoomLayouts(null);
            rooms.add(r);
        }
        generator.writeObject(rooms);
    }
}