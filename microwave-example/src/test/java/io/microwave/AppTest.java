package io.microwave;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;


import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void testReflect() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        try {
            Method[] methods = ClassUtils.getClass("io.training.thrift.api.SomeService.Iface").getMethods();
            log.info("==============={}", JSON.toJSONString(methods));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
