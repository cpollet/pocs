package net.cpollet.pocs.jersey.rest.providers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Christophe Pollet
 */
public class JsonHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHandler.class);

    private static final String UTF_8 = "UTF-8";

    private Gson gson;

    private Gson getGson() {
        if (gson == null) {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.disableHtmlEscaping()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .setPrettyPrinting()
                    .create();
        }
        return gson;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                           MultivaluedMap<String, String> httpHeaders, InputStream entityStream) {
        try (InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8)) {
            Type jsonType;
            if (type.equals(genericType)) {
                jsonType = type;
            } else {
                jsonType = genericType;
            }
            return getGson().fromJson(streamReader, jsonType);
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {

        try (OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8)) {
            Type jsonType;
            if (type.equals(genericType)) {
                jsonType = type;
            } else {
                jsonType = genericType;
            }
            getGson().toJson(object, jsonType, writer);
        }
    }
}
