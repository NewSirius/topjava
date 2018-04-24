package ru.javawebinar.topjava.util.annotations;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public final class LocalDateAnnotationFormatFactory implements AnnotationFormatterFactory<LocalDateAnnotation> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Collections.singletonList(LocalDate.class));
    }

    @Override
    public Printer<?> getPrinter(LocalDateAnnotation annotation, Class<?> fieldType) {
        return new LocalDateFormatter();
    }

    @Override
    public Parser<?> getParser(LocalDateAnnotation annotation, Class<?> fieldType) {
        return new LocalDateFormatter();
    }
}
