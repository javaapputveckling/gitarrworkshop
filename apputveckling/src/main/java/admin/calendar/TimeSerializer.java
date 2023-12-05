package admin.calendar;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.sql.Time;

public class TimeSerializer implements JsonbSerializer<Time> {
    @Override
    public void serialize(Time obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj.toString());
    }
}
