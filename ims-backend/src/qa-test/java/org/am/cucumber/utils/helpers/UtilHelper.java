package org.am.cucumber.utils.helpers;

import org.am.cucumber.utils.Util;

// Singleton design pattern
public enum UtilHelper {
    INSTANCE(new Util());

    private Util util;

    UtilHelper(final Util utilInstance) {

        this.util = utilInstance;
    }

    public static Util getUtil() {

        return INSTANCE.util;
    }
}
