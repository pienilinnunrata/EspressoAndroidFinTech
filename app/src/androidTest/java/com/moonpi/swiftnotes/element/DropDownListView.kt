package com.moonpi.swiftnotes.element

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import com.moonpi.swiftnotes.R
import org.hamcrest.CoreMatchers.allOf
import ru.tinkoff.allure.android.deviceScreenshot
import ru.tinkoff.allure.step

class DropDownListView {

    inline fun assert(block: Assert.() -> Unit) = Assert().block()

    inner class Assert {
        fun buttonShownWithText(text: String) = step("Проверяем кнопку с текстом $text") {
            onView(allOf(
                    isDescendantOfA(withId(R.id.content)),
                    withText(text),
                    withId(R.id.title)
            )).check(matches(isDisplayed()))
            deviceScreenshot("check_btn_on_menu")
        }
    }

    companion object {
        inline operator fun <T> invoke(block: DropDownListView.() -> T) = DropDownListView().block()
    }
}