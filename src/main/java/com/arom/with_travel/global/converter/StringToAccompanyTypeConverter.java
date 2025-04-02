package com.arom.with_travel.global.converter;

import com.arom.with_travel.domain.accompanies.model.AccompanyType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAccompanyTypeConverter implements Converter<String, AccompanyType> {
    @Override
    public AccompanyType convert(String source) {
        return AccompanyType.fromAccompanyType(source);
    }
}
