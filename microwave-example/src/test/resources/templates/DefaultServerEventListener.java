package io.microwave.example.listener;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.microwave.annotation.ExportService;
import io.microwave.annotation.MicrowaveServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@MicrowaveServer
public class DefaultServerEventListener implements ApplicationEventListener<StartupEvent> {
    private AtomicBoolean status = new AtomicBoolean(false);

    @Inject
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(StartupEvent event) {
        if(status.get()) {
            return;
        }
        // 防止重复注册
        this.status.set(true);
        Collection<BeanDefinition<?>> beanDefinitions = applicationContext.getBeanDefinitions(Qualifiers.byStereotype(ExportService.class));
        register(beanDefinitions);
        String s = applicationContext.getBean(String.class);

    }

    public void register(Collection<BeanDefinition<?>> beanDefinitions) {
        try {
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(8761);
            //异步IO，需要使用TFramedTransport，它将分块缓存读取。
            TTransportFactory transportFactory = new TFramedTransport.Factory();
            //使用高密度二进制协议
            TProtocolFactory proFactory = new TBinaryProtocol.Factory();
            //发布多个服务
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            for(BeanDefinition<?> beanDefinition: beanDefinitions) {
                Class<?> type = beanDefinition.getBeanType();
                String name = beanDefinition.getName();
                //Class<?> bean = applicationContext.getBean(type);
                //Class<?>[] cs = bean.getClass().getClasses();

//                System.out.println("" + cs.length);
//                handleProcessor(processor, name, bean);
//                processor.registerProcessor(beanDefinition.getName(), name, new SomeService.Processor<>(bean));
            }

            TServer server = new TThreadedSelectorServer(new
                    TThreadedSelectorServer.Args(serverTransport)
                    .transportFactory(transportFactory)
                    .protocolFactory(proFactory)
                    .processor(processor)
            );
            server.serve();
        } catch (TTransportException e) {
            log.warn("server start error:{}" + e.getMessage());
        }
    }


    protected void handleProcessor(TMultiplexedProcessor processor, String beanName, Object bean) {

//        processor.registerProcessor(beanDefinition.getName(), new SomeService.Processor<>(i));
    }



}
