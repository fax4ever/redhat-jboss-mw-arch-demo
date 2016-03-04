/*
 *
 */
package com.redhat.demo.arch.microservices.reader.web.constants;

/**
 * The Enum AppInfo.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public enum AppInfo {

    /** The name. */
    NAME("READER Service"),

    /** The version. */
    VERSION("1.0.0");

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
