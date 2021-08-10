package io.microwave.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.Qualifier;
import io.micronaut.runtime.Micronaut;
import io.training.thrift.api.SomeService;
import org.apache.thrift.TException;

import java.util.Collection;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = Micronaut.run(Application.class, args);
        Collection<SomeService.Iface> someServices = context.getBeansOfType(SomeService.Iface.class);
        try {
            for(SomeService.Iface ss: someServices) {
                String result = ss.echo("1111");
                System.out.println("=============" + result);
            }
        } catch (TException e) {
            e.printStackTrace();
        }

    }
}
