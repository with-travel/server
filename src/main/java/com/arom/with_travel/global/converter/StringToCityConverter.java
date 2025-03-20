package com.arom.with_travel.global.converter;

import com.arom.with_travel.domain.accompanies.model.City;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCityConverter implements Converter<String, City> {
    @Override
    public City convert(String source) {
        return City.fromCity(source);
    }
}