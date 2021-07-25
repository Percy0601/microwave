package io.microwave.example.listener;

import io.microwave.annotation.MicrowaveServer;
import io.microwave.server.AbstractServerEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TMultiplexedProcessor;

import javax.inject.Singleton;

@Slf4j
@Singleton
@MicrowaveServer
public class DefaultServerEventListener extends AbstractServerEventListener {

    @Override
    protected void handleProcessor(TMultiplexedProcessor processor, String beanName, Object bean) {
//        processor.registerProcessor(beanDefinition.getName(), new SomeService.Processor<>(i));
    }



}
