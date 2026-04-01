package com.luas.rnsharedprefs

import com.facebook.react.bridge.ReactApplicationContext

class RnSharedPrefsModule(reactContext: ReactApplicationContext) :
  NativeRnSharedPrefsSpec(reactContext) {

  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  companion object {
    const val NAME = NativeRnSharedPrefsSpec.NAME
  }
}
