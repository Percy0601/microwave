package io.microwave.client.pool;

import io.microwave.core.protocol.AttachableBinaryProtocol;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ThriftClientPoolFactory extends BasePoolableObjectFactory<TServiceClient> {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void destroyObject(TServiceClient client) {
        log.info("destroyObject:{}", client);
        TTransport pin = client.getInputProtocol().getTransport();
        pin.close();
        TTransport pout = client.getOutputProtocol().getTransport();
        pout.close();
    }

    @Override
    public void activateObject(TServiceClient client) {
    }

    @Override
    public void passivateObject(TServiceClient client) {
    }

    @Override
    public boolean validateObject(TServiceClient client) {
        TTransport pin = client.getInputProtocol().getTransport();
        //log.info("validateObject input:{}", pin.isOpen());
        TTransport pout = client.getOutputProtocol().getTransport();
        //log.info("validateObject output:{}", pout.isOpen());
        return pin.isOpen() && pout.isOpen();
    }

    @Override
    public TServiceClient makeObject() throws Exception {
        // TODO 注册中心获取ID地址
        TSocket tsocket = new TSocket("127.0.0.1", 8761);
        TTransport transport = new TFramedTransport(tsocket);
        TProtocol protocol = new AttachableBinaryProtocol(transport);
        TServiceClient client = createServiceClient(protocol);
        tsocket.open();
        return client;
    }

    protected abstract TServiceClient createServiceClient(TProtocol protocol);

}
