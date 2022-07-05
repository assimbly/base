package org.assimbly.util.helper;

public final class ContextHelper {

    private static final String FLOW_ID_PREFIX = "ID_";

    public ContextHelper() {}

    public static String getFlowId(String contextName){
        return contextName.split(FLOW_ID_PREFIX)[1];
    }
}
