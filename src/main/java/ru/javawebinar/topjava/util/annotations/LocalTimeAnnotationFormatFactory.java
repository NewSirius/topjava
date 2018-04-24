package ru.javawebinar.topjava.util.annotations;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LocalTimeAnnotationFormatFactory implements AnnotationFormatterFactory<LocalTimeAnnotation> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Collections.singletonList(LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(LocalTimeAnnotation annotation, Class<?> fieldType) {
        return new LocalTimeFormatter();
    }

    @Override
    public Parser<?> getParser(LocalTimeAnnotation annotation, Class<?> fieldType) {
        return new LocalTimeFormatter();
    }
}
