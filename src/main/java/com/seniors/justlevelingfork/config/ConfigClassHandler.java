package com.seniors.justlevelingfork.config;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.seniors.justlevelingfork.JustLevelingFork;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

// Custom implementation for version that YetAnotherConfigLib doesn't support.
public class ConfigClassHandler<T> {

    private T _instance;

    public T instance(){
        return _instance;
    }

    private final JsonFactory _factory;
    private final ObjectMapper _objectMapper;

    private final File _filePath;

    public ConfigClassHandler(Class<T> configClass, String fileName){
        _factory = JsonFactory.builder()
                .enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES)
                .enable(JsonReadFeature.ALLOW_TRAILING_COMMA)
                .enable(JsonReadFeature.ALLOW_SINGLE_QUOTES)
                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                .enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS)
                .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS)
                .enable(JsonReadFeature.ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS)
                .enable(JsonReadFeature.ALLOW_TRAILING_DECIMAL_POINT_FOR_NUMBERS)
                .build();
        _objectMapper = new ObjectMapper();
        _objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);

        _filePath = FMLPaths.CONFIGDIR.get().resolve("JLFork").resolve(fileName).toFile();
        try {
            _instance = configClass.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            JustLevelingFork.getLOGGER().error(">> Error while getting constructor or config class.");
        }

        load();
    }

    public void save(){
        try {
            JsonGenerator generator = _factory.createGenerator(_filePath, JsonEncoding.UTF8);
            _objectMapper.writerWithDefaultPrettyPrinter().writeValue(generator, _instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(){
        if(!_filePath.exists()){
            save();
        }
        try {
            JsonParser parser = _factory.createParser(_filePath);
            _instance = (T)_objectMapper.readValue(parser, _instance.getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
