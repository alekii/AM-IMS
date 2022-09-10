package org.am.commons.utils;

public interface Converter<S, R> {

    R convert(S source);

    interface WithTwoSources<S1, S2, R> extends Converter<S1, R> {

        default R convert(S1 source) {

            return convert(source, null);
        }

        R convert(S1 source, S2 source2);
    }
}

