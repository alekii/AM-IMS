package org.am.commons.utils;

public interface Converter<S, R> {

    R convert(S source);
}

