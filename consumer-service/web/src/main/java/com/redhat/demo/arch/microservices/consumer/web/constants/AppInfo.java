/*
 *
 */
package com.redhat.demo.arch.microservices.consumer.web.constants;

/**
 * The Enum AppInfo.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public enum AppInfo {

    /** The name. */
    NAME("CONSUMER Service"),

    /** The version. */
    VERSION("2.0.0");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new app info.
     *
     * @param value the value
     */
    private AppInfo(String value) {
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }


}
