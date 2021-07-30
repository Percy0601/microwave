package io.microwave.example.listener;

import io.microwave.annotation.MicrowaveServer;
import io.microwave.annotation.Reference;
import io.training.thrift.api.SomeService;

@MicrowaveServer
public interface MicrowaveServerStarter {

    @Reference
    SomeService.Iface someService();


}
