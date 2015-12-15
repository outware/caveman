package au.com.outware.cavemansample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GetEnvironmentProperty getEnvironmentProperty = new GetEnvironmentProperty(this);

        TextView baseUrlText = (TextView) findViewById(R.id.base_url);
        baseUrlText.setText(String.format("Base URL: %s", getEnvironmentProperty.getBaseUrl()));
        TextView logsEnabledText = (TextView) findViewById(R.id.logs_enabled);
        logsEnabledText.setText(String.format("Logging Enabled: %s", String.valueOf(getEnvironmentProperty.isLoggingEnabled())));
        TextView timeoutText = (TextView) findViewById(R.id.timeout);
        timeoutText.setText(String.format("Timeout: %s", String.valueOf(getEnvironmentProperty.getTimeout())));
    }
}
