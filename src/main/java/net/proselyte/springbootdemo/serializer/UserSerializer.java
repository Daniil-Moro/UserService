package net.proselyte.springbootdemo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.proselyte.springbootdemo.model.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User>
{
    private static final long serialVersionUID = 1L;

    public UserSerializer() {
        this(null);
    }

    protected UserSerializer(Class<User>
                                        t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
//        provider.defaultSerializeField("id", user.getId(), gen);
        provider.defaultSerializeField("username", user.getName(), gen);
        provider.defaultSerializeField("first_name", user.getFirstName(), gen);
        provider.defaultSerializeField("last_name", user.getLastName(), gen);
        provider.defaultSerializeField("email", user.getEmail(), gen);
        gen.writeEndObject();
    }
}