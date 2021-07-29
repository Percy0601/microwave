package io.microwave.compiler.util;

import io.microwave.compiler.model.ServerProcessorEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MetaHolder {

    private static ConcurrentHashMap<String, List<? extends TypeMirror>> exportServices = new ConcurrentHashMap<>();

    private MetaHolder() {

    }

    public static void addExportService(String exportService, List<? extends TypeMirror> types) {
        exportServices.put(exportService, types);
    }

    public static String getThriftInterfaceName(String exportService) {
        List<?> types = exportServices.get(exportService);
        if(null == types || types.size() < 1) {
            return null;
        }
        String interfaceNameIface = types.stream()
                .map(String::valueOf)
                .map(String::trim)
                .filter(it -> it.contains("Iface"))
                .findFirst()
                .get();
        int lastDot = interfaceNameIface.lastIndexOf('.');
        String interfaceName = interfaceNameIface.substring(0, lastDot);
        return interfaceName;
    }

    public static List<ServerProcessorEntry> getConverterEntries() {
        List<ServerProcessorEntry> entries = new ArrayList<>();
        if(exportServices.isEmpty()) {
            return entries;
        }
        exportServices.forEach((k, v) -> {
            ServerProcessorEntry entry = new ServerProcessorEntry();
            entry.setImplClassName(k);
            String interfaceName = getThriftInterfaceName(k);
            entry.setInterfaceName(interfaceName);
            String simpleClassName = ClassNameUtil.getSimpleClassName(k);
            String paramName = ClassNameUtil.getParamName(simpleClassName);
            entry.setParamName(paramName + "_");
            entry.setRegisterName(interfaceName + "$");
            entries.add(entry);
        });
        return entries;
    }


}
