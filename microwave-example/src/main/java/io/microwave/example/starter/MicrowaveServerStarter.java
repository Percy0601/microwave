package io.microwave.example.starter;

import io.microwave.annotation.MicrowaveServer;
import io.microwave.annotation.Reference;
import io.training.thrift.api.SomeService;

@MicrowaveServer
public interface MicrowaveServerStarter {

    @Reference
    SomeService.Iface someService();

}
