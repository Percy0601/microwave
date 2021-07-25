package io.microwave.compiler.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MetaHolder {

    private static Queue<String> exportServices = new ConcurrentLinkedQueue<>();

    private MetaHolder() {

    }

    public static void addExportService(String exportService) {
        exportServices.add(exportService);
    }



}
