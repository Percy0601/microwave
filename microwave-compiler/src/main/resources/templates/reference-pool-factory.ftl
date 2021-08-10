package ${packageName};

import io.microwave.client.pool.ThriftClientPoolFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;

public class _${simpleClassName}PoolFactory extends ThriftClientPoolFactory {
    protected _${simpleClassName}PoolFactory() {
    }
    @Override
    protected TServiceClient createServiceClient(TProtocol protocol) {
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, ${className}.class.getName() + "$");
        ${className}.Client client = new ${className}.Client(multiplexedProtocol);
        return client;
    }
}
