package com.adobe.prj.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.adobe.prj.entity.RoomLayout;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomLayoutSerializer extends StdSerializer<List<RoomLayout>> {

    public CustomLayoutSerializer() {
        this(null);
    }

    public CustomLayoutSerializer(Class<List<RoomLayout>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<RoomLayout> roomLayouts,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<RoomLayout> layouts = new ArrayList<>();
        for (RoomLayout rl : roomLayouts) {
            rl.setRooms(null);
            layouts.add(rl);
        }
        generator.writeObject(layouts);
    }
}