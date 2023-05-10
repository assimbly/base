package org.assimbly.util.domain;

import java.io.Serializable;
import java.util.Map;

public class AssimblyEvent implements Serializable {

    private static final long serialVersionUID = -414325906494469280L;

    private final String timestamp;
    private final String expiryDate;
    private final String bundleId;
    private final int flowVersion;
    private final String component;
    private final String body;
    private final Map<String, Object> headers;

    public AssimblyEvent(String timestamp, String expiryDate, String bundleId, int flowVersion, String component, String body, Map<String, Object> headers) {
        this.timestamp = timestamp;
        this.expiryDate = expiryDate;
        this.bundleId = bundleId;
        this.flowVersion = flowVersion;
        this.component = component;
        this.body = body;
        this.headers = headers;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBundleId() {
        return bundleId;
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
        return headers;
    }
}
