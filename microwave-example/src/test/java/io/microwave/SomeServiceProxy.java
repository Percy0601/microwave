package io.microwave;

import io.training.thrift.api.SomeService;
import io.training.thrift.api.User;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class SomeServiceProxy implements SomeService.Iface {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private volatile GenericObjectPool<TServiceClient> pool = null;

    private GenericObjectPool<TServiceClient> getPool() {
        if(null != pool) {
            return pool;
        }
        synchronized (this) {
            GenericObjectPool.Config config = getPoolConfig();
            MySomeServicePoolFactory clientPool = new MySomeServicePoolFactory();
            this.pool = new GenericObjectPool<>(clientPool, config);
            return pool;
        }
    }

    private GenericObjectPool.Config getPoolConfig() {
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.maxActive = 10;
        poolConfig.maxIdle = 1;
        poolConfig.minIdle = 0;
        poolConfig.minEvictableIdleTimeMillis = 10000;
        poolConfig.timeBetweenEvictionRunsMillis = 10000 * 2L;
        poolConfig.testOnBorrow=true;
        poolConfig.testOnReturn=false;
        poolConfig.testWhileIdle=false;
        return poolConfig;
    }

    @Override
    public String echo(String s) throws TException {
        boolean flag = true;
        SomeService.Client client = null;
        try {
            client = (SomeService.Client)getPool().borrowObject();
            String result = client.echo(s);
            return result;
        } catch (Exception e) {
            flag = false;
            log.warn("SomeServiceProxy.echo invoke error: exception:", e);
        } finally {
            try {
                if(flag){
                    pool.returnObject(client);
                }else{
                    pool.invalidateObject(client);
                }
            } catch (Exception e) {
                log.warn("SomeServiceProxy.echo close error: exception:", e);
            }
        }
        return null;
    }

    @Override
    public int addUser(User user) throws TException {
        return 0;
    }

    @Override
    public List<User> findUserByIds(List<Integer> list) throws TException {
        return null;
    }

}
