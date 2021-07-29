package io.microwave;


import io.microwave.compiler.util.ClassNameUtil;
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
        String packageName = ClassNameUtil.getPackageName("io.microwave.AppTest");
        String simpleClassName = ClassNameUtil.getSimpleClassName("io.microwave.AppTest");

        log.info("=========packageName:{}, simpleClassName:{}", packageName, simpleClassName);
    }

    @Test
    void testParamName() {
        String paramName = ClassNameUtil.getParamName("MetaHolder");
        log.info("paramName:{}", paramName);
    }

}
