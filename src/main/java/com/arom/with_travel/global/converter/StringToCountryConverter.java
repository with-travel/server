package com.arom.with_travel.global.converter;

import com.arom.with_travel.domain.accompanies.model.Country;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCountryConverter implements Converter<String, Country> {
    @Override
    public Country convert(String source) {
        return Country.fromCountry(source);
    }
}