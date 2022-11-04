package org.am.commons.utils;

import java.util.UUID;

public class MappingUtil {

    public static final String GENERATE_RANDOM_UUID = "java(org.am.commons.utils.MappingUtil.generateSid())";

    public static UUID generateSid() {

        return UUID.randomUUID();
    }
}
