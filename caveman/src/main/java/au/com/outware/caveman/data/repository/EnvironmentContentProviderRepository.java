package au.com.outware.caveman.data.repository;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.caveman.domain.repository.EnvironmentRepository;

/**
 * Access data from Caveman. If Caveman is not installed, then default values are returned
 *
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public final class EnvironmentContentProviderRepository implements EnvironmentRepository {
    private final Environment environment;

    /**
     * @param context the current application {@link Context}.
     * @param providerAuthority the authority of the {@link android.content.ContentProvider}.
     */
    public EnvironmentContentProviderRepository(@NonNull Context context, @NonNull String providerAuthority) {
        environment = getEnvironment(context, providerAuthority);
    }

    /**
     * Looks for an property in the current {@link Environment}.
     *
     * @param key of the property to look for. The key is not CASE-SENSITIVE.
     * @param defaultValue the value to return if key is not found.
     *
     * @return the property with the specified key if found or the default value if
     * the key was not found, the property is empty or the {@link Environment} has not been loaded.
     */
    @NonNull
    public <T> T getProperty(@NonNull final String key, @NonNull T defaultValue) {
        // check for empty key and environment is null
        if (environment == null) {
            return defaultValue;
        }

        return environment.getProperty(key, defaultValue);
    }

    @Nullable
    public <T> T getProperty(@NonNull final String key) {
        if (environment == null) {
            return null;
        }

        return environment.getProperty(key, (T)null);
    }

    private Uri getContentUri(String providerAuthority) {
        return Uri.parse("content://" + providerAuthority + "/");
    }

    /**
     * Contacts the Caveman app in order to get and return the current {@link Environment}. Before
     * accepting and parsing the JSON string containing the {@link Environment} definition it checks
     * that the signature of the Caveman app is equal to the signature of this app.
     *
     * @param context the current application {@link Context}.
     * @param providerAuthority the authority of the {@link android.content.ContentProvider}.
     *
     * @return the currently selected {@link Environment} as retrieved from the Caveman app.
     * <code>null</code> if the currently selected {@link Environment} is the production one, if the
     * Caveman app was not found or if the Caveman app signature didn't match this app
     * signature.
     */
    @Nullable
    private Environment getEnvironment(@NonNull final Context context, @NonNull final String providerAuthority) {
        PackageManager packageManager = context.getPackageManager();
        // Check if Caveman is installed
        ProviderInfo companionInfo = packageManager.resolveContentProvider(providerAuthority, 0);
        if (companionInfo == null) {
            return null;
        }

        // Make sure Caveman is signed with the same key as we are
        if (packageManager.checkSignatures(context.getPackageName(),
                companionInfo.packageName) != PackageManager.SIGNATURE_MATCH) {
            return null;
        }

        // Get settings from Caveman
        Cursor cursor = context.getContentResolver().query(getContentUri(providerAuthority), null, null,
                null, null);
        cursor.moveToFirst();
        // Get the properties json string
        String json = cursor.getString(0);
        cursor.close();

        // De-serialize the environment object from json
        Gson gson = new Gson();
        Environment environment = gson.fromJson(json, Environment.class);

        // Check if production
        if (environment == null || environment.isProduction()) {
            // Ignore this settings
            return null;
        }
        return environment;
    }
}
