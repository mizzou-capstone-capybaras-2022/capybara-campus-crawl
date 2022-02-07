package com.capybara.CapybaraCampusCrawlBackend.Models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PlaceTypeConverter implements AttributeConverter<PlaceType, String> {
    @Override
    public String convertToDatabaseColumn(PlaceType direction) {
        return direction.name();
    }

    @Override
    public PlaceType convertToEntityAttribute(String string) {
        return PlaceType.valueOf(string);
    }
}