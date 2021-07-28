package io.microwave.server;

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

@Slf4j
public class MicrowaveServerFactory {
    private int port;
    private Thread thread;
    private TMultiplexedProcessor processor;
    // TODO 注册中心等

    public MicrowaveServerFactory(TMultiplexedProcessor processor) {
        port = 8761;
        this.processor = processor;
    }

    public MicrowaveServerFactory(TMultiplexedProcessor processor, Integer port) {
        this.port = port;
        this.processor = processor;
    }

    public void start() {
        Runnable runnable = getServerRunnable();
        thread = new Thread(runnable, "microwave-server");
        thread.start();
    }

    private Runnable getServerRunnable() {
        Runnable runnable = () -> {
            try {
                TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
                //异步IO，需要使用TFramedTransport，它将分块缓存读取。
                TTransportFactory transportFactory = new TFramedTransport.Factory();
                //使用高密度二进制协议
                TProtocolFactory proFactory = new TBinaryProtocol.Factory();
                TServer server = new TThreadedSelectorServer(new
                        TThreadedSelectorServer.Args(serverTransport)
                        .transportFactory(transportFactory)
                        .protocolFactory(proFactory)
                        .processor(processor)
                );
                server.serve();
            } catch (TTransportException e) {
                log.error("Microwave-Server start exception, port bind, port:{}, Exception:", port, e);
            } catch (Exception e) {
                log.error("Microwave-Server start exception, Exception:", e);
            }
        };

        return runnable;
    }

}
