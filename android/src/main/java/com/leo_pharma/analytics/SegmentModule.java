package com.leo_pharma.analytics;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;
import com.segment.analytics.android.integrations.adjust.AdjustIntegration;
import com.segment.analytics.android.integrations.amplitude.AmplitudeIntegration;
import com.segment.analytics.android.integrations.appboy.AppboyIntegration;
import com.segment.analytics.android.integrations.appsflyer.AppsflyerIntegration;
import com.segment.analytics.android.integrations.bugsnag.BugsnagIntegration;
import com.segment.analytics.android.integrations.comscore.ComScoreIntegration;
import com.segment.analytics.android.integrations.countly.CountlyIntegration;
import com.segment.analytics.android.integrations.crittercism.CrittercismIntegration;
import com.segment.analytics.android.integrations.firebase.FirebaseIntegration;
import com.segment.analytics.android.integrations.google.analytics.GoogleAnalyticsIntegration;
import com.segment.analytics.android.integrations.intercom.IntercomIntegration;
import com.segment.analytics.android.integrations.localytics.LocalyticsIntegration;
import com.segment.analytics.android.integrations.mixpanel.MixpanelIntegration;
import com.segment.analytics.android.integrations.nielsendcr.NielsenDCRIntegration;
import com.segment.analytics.android.integrations.quantcast.QuantcastIntegration;
import com.segment.analytics.android.integrations.tapstream.TapstreamIntegration;
import com.segment.analytics.android.integrations.branch.BranchIntegration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SegmentModule extends ReactContextBaseJavaModule {
    private static final String PROPERTY_FLUSH_AT = "flushAt";
    private static final String PROPERTY_RECORD_SCREEN_VIEWS = "recordScreenViews";
    private static final String PROPERTY_TRACK_APPLICATION_LIFECYCLE_EVENTS = "trackApplicationLifecycleEvents";
    private static final String PROPERTY_TRACK_ATTRIBUTION_DATA = "trackAttributionData";
    private static final String PROPERTY_DEBUG = "debug";

    public SegmentModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SegmentModule";
    }

    @ReactMethod
    public void setup(@Nullable String key, @Nullable ReadableMap options, Promise promise) {
        Analytics.Builder analyticsBuilder = new Analytics.Builder(getReactApplicationContext(), key);

        if (options != null) {
            if (options.hasKey(PROPERTY_FLUSH_AT)) {
                analyticsBuilder.flushQueueSize(options.getInt(PROPERTY_FLUSH_AT));
            }

            if (options.hasKey(PROPERTY_RECORD_SCREEN_VIEWS) && options.getBoolean(PROPERTY_RECORD_SCREEN_VIEWS)) {
                analyticsBuilder.recordScreenViews();
            }

            if (options.hasKey(PROPERTY_TRACK_APPLICATION_LIFECYCLE_EVENTS)
                    && options.getBoolean(PROPERTY_TRACK_APPLICATION_LIFECYCLE_EVENTS)) {
                analyticsBuilder.trackApplicationLifecycleEvents();
            }

            if (options.hasKey(PROPERTY_TRACK_ATTRIBUTION_DATA)
                    && options.getBoolean(PROPERTY_TRACK_ATTRIBUTION_DATA)) {
                analyticsBuilder.trackAttributionInformation();
            }

            if (options.hasKey(PROPERTY_DEBUG) && options.getBoolean(PROPERTY_DEBUG)) {
                analyticsBuilder.logLevel(Analytics.LogLevel.VERBOSE);
            }
            else {
                analyticsBuilder.logLevel(Analytics.LogLevel.NONE);
            }
        }

        setupIntegrations(analyticsBuilder);

        try {
            Analytics.setSingletonInstance(analyticsBuilder.build());
            promise.resolve(true);
        } catch (IllegalStateException e) {
            promise.reject("IllegalStateException", "Analytics is already set up, cannot perform setup twice.");
        }
    }

    /**
     * Sets up integrations from https://github.com/segment-integrations plus AppsFlyer, if their SDK is present
     *
     * @param analyticsBuilder
     */
    private void setupIntegrations(Analytics.Builder analyticsBuilder) {
        if (isClassAvailable("com.segment.analytics.android.integrations.adjust.AdjustIntegration")) {
            analyticsBuilder.use(AdjustIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.amplitude.AmplitudeIntegration")) {
            analyticsBuilder.use(AmplitudeIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.appboy.AppboyIntegration")) {
            analyticsBuilder.use(AppboyIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.appsflyer.AppsflyerIntegration")) {
            analyticsBuilder.use(AppsflyerIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.bugsnag.BugsnagIntegration")) {
            analyticsBuilder.use(BugsnagIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.comscore.ComScoreIntegration")) {
            analyticsBuilder.use(ComScoreIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.countly.CountlyIntegration")) {
            analyticsBuilder.use(CountlyIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.crittercism.CrittercismIntegration")) {
            analyticsBuilder.use(CrittercismIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.firebase.FirebaseIntegration")) {
            analyticsBuilder.use(FirebaseIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.google.analytics.GoogleAnalyticsIntegration")) {
            analyticsBuilder.use(GoogleAnalyticsIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.intercom.IntercomIntegration")) {
            analyticsBuilder.use(IntercomIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.localytics.LocalyticsIntegration")) {
            analyticsBuilder.use(LocalyticsIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.mixpanel.MixpanelIntegration")) {
            analyticsBuilder.use(MixpanelIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.nielsendcr.NielsenDCRIntegration")) {
            analyticsBuilder.use(NielsenDCRIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.quantcast.QuantcastIntegration")) {
            analyticsBuilder.use(QuantcastIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.tapstream.TapstreamIntegration")) {
            analyticsBuilder.use(TapstreamIntegration.FACTORY);
        }

        if (isClassAvailable("com.segment.analytics.android.integrations.branch.BranchIntegration")) {
            analyticsBuilder.use(BranchIntegration.FACTORY);
        }
    }

    /**
     * Checks if a certain class is available.
     *
     * @param className Including the full package name
     * @return True if the class is available. False if it cannot be found.
     */
    private boolean isClassAvailable(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Adds the toHashMap function
     * 
     * @param properties a ReadableMap
     * @return a new HashMap containing the properties from the ReadableMap
     */
    private HashMap<String, Object> toHashMap(@Nullable ReadableMap properties) {
        ReadableMapKeySetIterator iterator = properties.keySetIterator();
        HashMap<String, Object> hashMap = new HashMap<>();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (properties.getType(key)) {
            case Null:
                hashMap.put(key, null);
                break;
            case Boolean:
                hashMap.put(key, properties.getBoolean(key));
                break;
            case Number:
                hashMap.put(key, properties.getDouble(key));
                break;
            case String:
                hashMap.put(key, properties.getString(key));
                break;
            case Map:
                hashMap.put(key, toHashMap(properties.getMap(key)));
                break;
            case Array:
                hashMap.put(key, toArrayList(properties.getArray(key)));
                break;
            default:
                throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
            }
        }
        return hashMap;
    }

    /**
     * Adds the toArrayList function
     * 
     * @param array a ReadableArray
     * @return a new ArrayList containing the properties from the ReadableArray
     */
    private ArrayList<Object> toArrayList(ReadableArray array) {
        ArrayList<Object> arrayList = new ArrayList<>();
    
        for (int i = 0; i < array.size(); i++) {
          switch (array.getType(i)) {
            case Null:
              arrayList.add(null);
              break;
            case Boolean:
              arrayList.add(array.getBoolean(i));
              break;
            case Number:
              arrayList.add(array.getDouble(i));
              break;
            case String:
              arrayList.add(array.getString(i));
              break;
            case Map:
              arrayList.add(toHashMap(array.getMap(i)));
              break;
            case Array:
              arrayList.add(toArrayList(array.getArray(i)));
              break;
            default:
              throw new IllegalArgumentException("Could not convert object at index: " + i + ".");
          }
        }
        return arrayList;
      }

    @ReactMethod
    public void identify(@Nullable String userId, @Nullable ReadableMap properties) {
        Traits traits = new Traits();

        if (properties != null) {
            traits.putAll(toHashMap(properties));
        }

        Analytics.with(getReactApplicationContext()).identify(userId, traits, null);
    }

    @ReactMethod
    public void track(@Nullable String event, @Nullable ReadableMap properties) {
        Properties segmentProperties = new Properties();

        if (properties != null) {
            segmentProperties.putAll(toHashMap(properties));
        }

        Analytics.with(getReactApplicationContext()).track(event, segmentProperties);
    }

    @ReactMethod
    public void screen(@Nullable String name, @Nullable ReadableMap properties) {
        Properties segmentProperties = new Properties();

        if (properties != null) {
            segmentProperties.putAll(toHashMap(properties));
        }

        Analytics.with(getReactApplicationContext()).screen("", name, segmentProperties);
    }

    @ReactMethod
    public void group(@Nullable String groupId, @Nullable ReadableMap properties) {
        Traits traits = new Traits();

        if (properties != null) {
            traits.putAll(toHashMap(properties));
        }

        Analytics.with(getReactApplicationContext()).group(groupId, traits, null);
    }

    @ReactMethod
    public void alias(@Nullable String newId) {
        Analytics.with(getReactApplicationContext()).alias(newId);
    }

    @ReactMethod
    public void reset() {
        Analytics.with(getReactApplicationContext()).reset();
    }

    @ReactMethod
    public void flush() {
        Analytics.with(getReactApplicationContext()).flush();
    }

    @ReactMethod
    public void enable() {
        Analytics.with(getReactApplicationContext()).optOut(false);
    }

    @ReactMethod
    public void disable() {
        Analytics.with(getReactApplicationContext()).optOut(true);
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(PROPERTY_FLUSH_AT, PROPERTY_FLUSH_AT);
        constants.put(PROPERTY_RECORD_SCREEN_VIEWS, PROPERTY_RECORD_SCREEN_VIEWS);
        constants.put(PROPERTY_TRACK_APPLICATION_LIFECYCLE_EVENTS, PROPERTY_TRACK_APPLICATION_LIFECYCLE_EVENTS);
        constants.put(PROPERTY_TRACK_ATTRIBUTION_DATA, PROPERTY_TRACK_ATTRIBUTION_DATA);
        constants.put(PROPERTY_DEBUG, PROPERTY_DEBUG);
        return constants;
    }
}