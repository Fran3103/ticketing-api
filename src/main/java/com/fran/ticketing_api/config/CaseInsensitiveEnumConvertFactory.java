package com.fran.ticketing_api.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Locale;

public class CaseInsensitiveEnumConvertFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum>Converter<String, T> getConverter(Class<T> targetType){
        return source ->{
            if (source == null) return null;

            String normalized = source
                    .trim()
                    .toUpperCase(Locale.ROOT)
                    .replace('-','_');

            return (T) Enum.valueOf(targetType, normalized);

        };
    }
}
