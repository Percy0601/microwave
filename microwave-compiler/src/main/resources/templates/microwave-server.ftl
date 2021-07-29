package ${packageName};

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.microwave.annotation.ExportService;
import io.microwave.annotation.MicrowaveServer;
import io.microwave.server.MicrowaveServerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TMultiplexedProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Singleton
public class ${simpleClassName} implements ApplicationEventListener<StartupEvent> {
    private AtomicBoolean status = new AtomicBoolean(false);

    @Inject
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(StartupEvent event) {
        log.info("##########################");
        if(status.get()) {
            return;
        }
        // 防止重复注册
        this.status.set(true);
        TMultiplexedProcessor processor = handleProcessor();
        MicrowaveServerFactory microwaveServerFactory = new MicrowaveServerFactory(processor);
        microwaveServerFactory.start();
    }

    private TMultiplexedProcessor handleProcessor() {
        TMultiplexedProcessor processor = new TMultiplexedProcessor();
    <#list entries as entry>
        ${entry.implClassName} ${entry.paramName} = applicationContext.getBean(${entry.implClassName}.class);
        processor.registerProcessor("${entry.registerName}", new ${entry.interfaceName}.Processor<>(${entry.paramName}));
    </#list>
        return processor;
    }
}
