package ${packageName};

import io.microwave.client.pool.ThriftClientPoolFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;

public class ${simpleClientClass}PoolFactory extends ThriftClientPoolFactory {
    @Override
    protected TServiceClient createServiceClient(TProtocol protocol) {
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, ${clientClass}.class.getName() + "$");
        ${clientClass}.Client client = new ${clientClass}.Client(multiplexedProtocol);
        return client;
    }
}
