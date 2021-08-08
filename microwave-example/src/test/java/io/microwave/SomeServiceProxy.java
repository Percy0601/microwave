package io.microwave;

import io.training.thrift.api.SomeService;
import io.training.thrift.api.User;
import org.apache.thrift.TException;

import java.util.List;

public class SomeServiceProxy implements SomeService.Iface {
    // TODO ConnectionPoolFactory


    @Override
    public String echo(String s) throws TException {
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
