package org.assimbly.util.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AssimblyEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = -414325906494469280L;

    private final String timestamp;
    private final String expiryDate;
    private final String flowId;
    private final int flowVersion;
    private final String component;
    private final String body;
    private transient Map<String, Object> headers;

    public AssimblyEvent(String timestamp, String expiryDate, String flowId, int flowVersion, String component, String body, Map<String, Object> headers) {
        this.timestamp = timestamp;
        this.expiryDate = expiryDate;
        this.flowId = flowId;
        this.flowVersion = flowVersion;
        this.component = component;
        this.body = body;
        this.headers = new HashMap<>(headers);
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getFlowId() {
        return flowId;
    }

    public int getFlowVersion() {
        return flowVersion;
    }

    public String getComponent() {
        return component;
    }

    public String getBody() {
        return body;
    }

    public Map<String, Object> getHeaders() {
        return new HashMap<>(headers);
    }
}
