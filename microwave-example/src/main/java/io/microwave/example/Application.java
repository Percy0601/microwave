package io.microwave.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import io.microwave.example.impl.SomeServiceImpl;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = Micronaut.run(Application.class, args);
        SomeServiceImpl someService = context.getBean(SomeServiceImpl.class);
    }
}
