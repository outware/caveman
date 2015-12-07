package au.com.outware.caveman.domain.repository;

import android.support.annotation.NonNull;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public interface EnvironmentRepository {
    <T> T getProperty(@NonNull final String key, T defaultValue);
}
