package io.microwave.compiler.model;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class ReferenceProcessorEntry {
    private String referApplication;
    private String localApplication;
    private String interfaceName;
    private Integer minConnection;
    private Integer maxConnection;
    private Integer idleConnection;
    private List<Method> methodList;
}
