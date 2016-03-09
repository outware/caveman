package au.com.outware.cavemansample;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import au.com.outware.caveman.data.repository.EnvironmentContentProviderRepository;
import au.com.outware.caveman.domain.repository.EnvironmentRepository;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class GetEnvironmentProperty {
    private static final String PROVIDER_AUTHORITY = "au.com.outware.caveman.contentprovider";
    private static final String BASE_URL_KEY = "BaseURL";
    private static final String LOGS_ENABLED_KEY = "LoggingEnabled";
    private static final String TIMEOUT_KEY = "Timeout";

    private static final String DEFAULT_BASE_URL = "http://base.url";
    private static final boolean DEFAULT_LOGS_ENABLED = false;
    private static final int DEFAULT_TIMEOUT = 2000;

    @VisibleForTesting
    EnvironmentRepository environmentRepository;

    public GetEnvironmentProperty(Context context) {
        environmentRepository = new EnvironmentContentProviderRepository(context, PROVIDER_AUTHORITY);
    }

    public String getBaseUrl() {
        return environmentRepository.getProperty(BASE_URL_KEY, DEFAULT_BASE_URL);
    }

    public boolean isLoggingEnabled() {
        return environmentRepository.getProperty(LOGS_ENABLED_KEY, DEFAULT_LOGS_ENABLED);
    }

    public int getTimeout() {
        return environmentRepository.getProperty(TIMEOUT_KEY, DEFAULT_TIMEOUT);
    }
}
