package com.moonpi.swiftnotes.page

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.uiautomator.By
import android.support.test.uiautomator.Until
import android.widget.ImageButton
import com.moonpi.swiftnotes.R
import com.moonpi.swiftnotes.element.DropDownListView
import com.moonpi.swiftnotes.util.device
import org.hamcrest.CoreMatchers.allOf
import ru.tinkoff.allure.android.deviceScreenshot
import ru.tinkoff.allure.step

class NewNote {

    private val toolbarCancelActionSelector = By.clazz(ImageButton::class.java)
    private val toolbarMoreActionSelector = By.descContains("Ещё")

    fun tapBack() = step("Тапаем назад, пока не появится попап сохранения") {
        device.wait(Until.findObject(toolbarCancelActionSelector), 1).click()
        deviceScreenshot("tap_back")
    }

    fun tapMoreBtn(block: DropDownListView.() -> Unit) = step("Открываем менюшку по кнопке Еще") {
        device.wait(Until.findObject(toolbarMoreActionSelector), 1).click()
        DropDownListView { block() }
        deviceScreenshot("tap_more_btn")
    }

    fun tapNoToSave() = step("Тапаем NO на попапе сохранения") {
        onView(allOf(withId(android.R.id.button2), isDisplayed())).perform(click())
        deviceScreenshot("tap_no_to_save")
    }

    fun tapYesToSave() = step("Тапаем YES на попапе сохранения") {
        onView(allOf(withId(android.R.id.button1), isDisplayed())).perform(click())
        deviceScreenshot("tap_yes_to_save")
    }

    fun fillTitleWithText(text: String = "Title") = step("Заполняем поле заголовка текстом '$text'") {
        onView(allOf(withId(R.id.titleEdit), isDisplayed())).perform(typeText(text))
        deviceScreenshot("fill_title_with_text")
    }

    fun fillNoteWithText(text: String = "Text") = step("Заполняем поле текста заметки текстом '$text") {
        onView(allOf(withId(R.id.bodyEdit), isDisplayed())).perform(typeText(text))
        deviceScreenshot("fill_note_with_text")
    }

    inline fun assert(block: Assert.() -> Unit) = Assert().block()

    inner class Assert {
        fun titleHint(hint: String = "Title") = step("Проверка хинта заголовка на форме создания") {
            onView(allOf(withId(R.id.titleEdit), isDisplayed())).check(matches(withHint(hint)))
            deviceScreenshot("edit_page_title_hint")
        }

        fun textHint(hint: String = "Note") = step("Проверка хинта текста на форме создания")  {
            onView(allOf(withId(R.id.bodyEdit), isDisplayed())).check(matches(withHint(hint)))
            deviceScreenshot("edit_page_text_hint")
        }

        fun popupText() = step("Проверка текста в попапе сохранения") {
            onView(allOf(withId(android.R.id.message), isDisplayed())).check(matches(withText("Save changes?")))
            deviceScreenshot("save_popup_text")
        }

        fun popupButtons() = step("Проверка кнопок в попапе сохранения") {
            onView(allOf(withId(android.R.id.button1), isDisplayed())).check(matches(withText("YES")))
            onView(allOf(withId(android.R.id.button2), isDisplayed())).check(matches(withText("NO")))
            deviceScreenshot("save_popup_buttons")
        }

        fun filledTitle(text: String) = step("Проверка заполнения заголовка '$text") {
            onView(allOf(withId(R.id.titleEdit), isDisplayed())).check(matches(withText(text)))
            deviceScreenshot("filled_title")
        }

        fun filledNote(text: String) = step("Проверка заполнения текста '$text") {
            onView(allOf(withId(R.id.bodyEdit), isDisplayed())).check(matches(withText(text)))
            deviceScreenshot("filled_note")
        }
    }

    companion object {
        inline operator fun <T> invoke(block: NewNote.() -> T) = NewNote().block()
    }
}