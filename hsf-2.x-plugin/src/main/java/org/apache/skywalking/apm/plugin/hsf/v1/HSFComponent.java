package org.apache.skywalking.apm.plugin.hsf.v1;

import org.apache.skywalking.apm.network.trace.component.Component;

public class HSFComponent implements Component {
    @Override public int getId() {
        return 90000;
    }

    @Override public String getName() {
        return "HSF";
    }
}
