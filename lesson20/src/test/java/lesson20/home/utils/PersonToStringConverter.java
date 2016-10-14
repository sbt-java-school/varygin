package lesson20.home.utils;

import lesson20.home.converter.values.ConvertValue;

public class PersonToStringConverter implements ConvertValue<String> {
    @Override
    public String convert(Object valueFrom) {
        Person value = (Person) valueFrom;
        return value.getName() + ":" + value.getAge();
    }
}
