package io.microwave.compiler.util;

import io.microwave.compiler.model.ServerProcessorEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MetaHolder {

    private static ConcurrentHashMap<String, List<?>> exportServices = new ConcurrentHashMap<>();

    private MetaHolder() {

    }

    public static void addExportService(String exportService, List<?> types) {
        exportServices.put(exportService, types);
    }

    public static String getThriftInterfaceName(String exportService) {
        List<?> types = exportServices.get(exportService);
        if(null == types || types.size() < 1) {
            return null;
        }
        String interfaceName = types.stream()
                .map(String::valueOf)
                .map(String::trim)
                .filter(it -> it.contains("Iface"))
                .findFirst()
                .get();
        return interfaceName;
    }

    public static List<ServerProcessorEntry> getConverterEntries() {
        List<ServerProcessorEntry> entries = new ArrayList<>();
        if(MapUtils.isEmpty(exportServices)) {
            return entries;
        }

        exportServices.forEach((k, v) -> {
            ServerProcessorEntry entry = new ServerProcessorEntry();
            entry.setImplClassName(k);
            String interfaceName = getThriftInterfaceName(k);
            entry.setInterfaceName(interfaceName);
            String simpleClassName = ClassNameUtil.getSimpleClassName(k);
            //getParamName(simpleClassName);
            entries.add(entry);
        });


        return entries;
    }


}
