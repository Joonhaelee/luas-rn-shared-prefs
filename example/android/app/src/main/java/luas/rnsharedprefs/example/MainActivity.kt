package luas.rnsharedprefs.example

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate

class MainActivity : ReactActivity() {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "RnSharedPrefsExample"

  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)


  var sharedPreferences: SharedPreferences? = null
  // 리스너를 멤버 변수로 저장 (중요: 약한 참조 방지)
  var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.sharedPreferences = this.getSharedPreferences("PrefsName", Context.MODE_PRIVATE);
    this.listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
      Log.d("RNSharedPrefsExample", "PrefsName:${key} changed: ${sharedPreferences.getString(key, "")}")
    }
    sharedPreferences?.registerOnSharedPreferenceChangeListener(listener)
  }

  override fun onDestroy() {
    sharedPreferences?.unregisterOnSharedPreferenceChangeListener(listener)

    super.onDestroy()
  }


}
