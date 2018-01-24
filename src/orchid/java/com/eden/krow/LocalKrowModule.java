package com.eden.krow;

import com.eden.orchid.api.registration.OrchidModule;
import com.vladsch.flexmark.util.options.MutableDataSet;

public class LocalKrowModule extends OrchidModule {

    @Override
    protected void configure() {
        addToSet(MutableDataSet.class);
    }

}
