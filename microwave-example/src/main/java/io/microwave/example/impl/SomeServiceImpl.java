package io.microwave.example.impl;

import io.microwave.annotation.Export;
import io.training.thrift.api.SomeService;
import io.training.thrift.api.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Singleton
@Export
public class SomeServiceImpl implements SomeService.Iface {

    @Override
    public String echo(String msg) throws TException {
        log.info("SomeServiceImpl echo: Hello: {}", msg);
        return "Hello " + msg;
    }

    @Override
    public int addUser(User user) throws TException {
        user.setUserId(new Random().nextInt(100));
        log.info("request addUser: user:{}", user);
        return user.getUserId();
    }

    @Override
    public List<User> findUserByIds(List<Integer> idList) throws TException {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserId(new Random().nextInt(1000));
        users.add(user);
        log.info("request findUserByIds, idList:{}, result:{}", idList, users);
        return users;
    }
}
