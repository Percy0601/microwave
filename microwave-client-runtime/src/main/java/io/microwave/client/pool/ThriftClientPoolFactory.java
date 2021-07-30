package io.microwave.client.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftClientPoolFactory extends BasePoolableObjectFactory<TServiceClient> {
    private Logger log = LoggerFactory.getLogger(getClass());
    private final TServiceClientFactory<TServiceClient> clientFactory;
    private PoolOperationCallBack callback;

    protected ThriftClientPoolFactory(TServiceClientFactory<TServiceClient> clientFactory){
        this.clientFactory = clientFactory;
    }

    protected ThriftClientPoolFactory(TServiceClientFactory<TServiceClient> clientFactory,
                                      PoolOperationCallBack callback){
        this.clientFactory = clientFactory;
        this.callback = callback;
    }

    static interface PoolOperationCallBack {
        // 销毁client之前执行
        void destroy(TServiceClient client);

        // 创建成功是执行
        void make(TServiceClient client);
    }

    @Override
    public void destroyObject(TServiceClient client) {
        if (callback != null) {
            callback.destroy(client);
        }
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
        TSocket tsocket = new TSocket("127.0.0.7", 8761);
        TTransport transport = new TFramedTransport(tsocket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TServiceClient client = this.clientFactory.getClient(protocol);
        transport.open();
        if (callback != null) {
            callback.make(client);
        }
        return client;
    }

}
