package io.microwave;


import io.microwave.compiler.util.FreemarkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    void test() {
        String aa = FreemarkerUtil.getFilePath("microwave-server.ftl");

        log.info("========={}", aa);
        FreemarkerUtil.handleServer("javax.inject.Inject", null);
    }
}
