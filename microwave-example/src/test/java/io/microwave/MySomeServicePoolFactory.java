package io.microwave;

import io.microwave.client.pool.ThriftClientPoolFactory;
import io.training.thrift.api.SomeService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;

public class MySomeServicePoolFactory extends ThriftClientPoolFactory {
    @Override
    protected TServiceClient createServiceClient(TProtocol protocol) {
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, SomeService.class.getName() + "$");
        SomeService.Client client = new SomeService.Client(multiplexedProtocol);
        return client;
    }
}
