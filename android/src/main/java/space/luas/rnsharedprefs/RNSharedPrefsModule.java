package space.luas.rnsharedprefs;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * TurboModule implementation for Foreground Service
 * <p>
 * This module bridges JavaScript to Android native foreground service functionality. Implements the
 * Spec interface defined in NativeForegroundService.ts
 * <p>
 * Key features: - Android 13+ POST_NOTIFICATIONS permission checking - Android 14+ foreground
 * service type validation - Full error handling and validation - TurboModule architecture for React
 * Native New Architecture
 */
@ReactModule(name = RNSharedPrefsModule.NAME)
public class RNSharedPrefsModule extends NativeRNSharedPrefsSpec {
  // defined in NativeRNSharedPrefsSpec
  public static final String NAME = NativeRNSharedPrefsSpec.NAME; // "NativeRNSharedPrefs";

  @NonNull
  @Override
  public String getName() {
    return NAME;
  }

  private static final String TAG = "RNSharedPrefsModule";
  private final Context context;
  private SharedPreferencesHelper sharedHandler;

  public RNSharedPrefsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.context = reactContext;
  }

  @ReactMethod
  public void init(String prefsName) {
    this.sharedHandler = new SharedPreferencesHelper(this.context, prefsName);
  }

  private void assertInitialized() {
    if (this.sharedHandler == null) {
      throw new RuntimeException("SharedPreferences not initialized");
    }
  }

  @ReactMethod
  @Nullable
  public String getItem(String key) {
    this.assertInitialized();
    return sharedHandler.getItem(key);
  }

  @ReactMethod
  public void setItem(String key, String value) {
    this.assertInitialized();
    this.sharedHandler.setItem(key, value);
  }

  @ReactMethod
  public void deleteItem(String key) {
    this.assertInitialized();
    this.sharedHandler.deleteItem(key);
  }

  @ReactMethod
  public void clear() {
    this.assertInitialized();
    this.sharedHandler.clear();
  }

  @ReactMethod
  public WritableArray getKeys() {
    this.assertInitialized();
    WritableArray array = Arguments.createArray();
    Set<String> keys = this.sharedHandler.keys();
    for (String key : keys) {
      array.pushString(key);
    }
    return array;
  }

  @ReactMethod
  public WritableArray getItems() {
    this.assertInitialized();
    WritableArray array = Arguments.createArray();
    Map<String, ?> values = this.sharedHandler.entries();
    for (Map.Entry<String, ?> entry : values.entrySet()) {
      WritableMap map = Arguments.createMap();
      map.putString("key", entry.getKey());
      map.putString("value", entry.getValue().toString());
      array.pushMap(map);
    }
    return array;
  }

  @ReactMethod
  public void isWidgetPlaced(String pkg, String widgetCls, Promise promise) {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    ComponentName componentName = new ComponentName(pkg, widgetCls);
    int[] ids = appWidgetManager.getAppWidgetIds(componentName);
    promise.resolve(ids.length != 0);
  }

  @ReactMethod
  public void pinWidget(String pkg, String widgetCls, @Nullable String widgetLayoutResouceName, Promise promise) {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    try {
      ComponentName widgetComponentName = new ComponentName(pkg, widgetCls);
      if (!appWidgetManager.isRequestPinAppWidgetSupported()) {
        promise.reject("ERROR", "Widget not support request pining");
      } else {
        Bundle b = new Bundle();
        String layoutName = widgetLayoutResouceName == null ? "widget": widgetLayoutResouceName;
        int widgetLayoutResourceId = context.getResources().getIdentifier(layoutName, "layout", pkg);
        RemoteViews views = new RemoteViews(pkg, widgetLayoutResourceId);
        b.putParcelable(AppWidgetManager.EXTRA_APPWIDGET_PREVIEW, views);
        appWidgetManager.requestPinAppWidget(widgetComponentName, b, null);
        promise.resolve(true);
      }
    }catch (Exception e) {
      promise.reject("ERROR", e.getMessage());
    }
  }
}
