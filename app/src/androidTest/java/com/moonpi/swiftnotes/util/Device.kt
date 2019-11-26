package com.moonpi.swiftnotes.util

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
val targetContext: Context
    get() = InstrumentationRegistry.getInstrumentation().targetContext

val device: UiDevice
    get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
