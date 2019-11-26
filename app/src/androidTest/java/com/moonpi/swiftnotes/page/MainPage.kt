package com.moonpi.swiftnotes.page

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.longClick
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.uiautomator.By
import android.support.test.uiautomator.Until
import com.moonpi.swiftnotes.R
import com.moonpi.swiftnotes.element.DropDownListView
import com.moonpi.swiftnotes.util.device
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.endsWith
import ru.tinkoff.allure.android.deviceScreenshot
import ru.tinkoff.allure.step

class MainPage {

    private val toolbarMoreActionSelector = By.descContains("Ещё")

    fun tapNewNote(block: NewNote.() -> Unit) = step("Открываем создание новой заметки") {
        onView(withId(R.id.newNote)).perform(click())
        deviceScreenshot("tap_new_note")
        NewNote { block() }
    }

    fun tapMoreBtn(block: DropDownListView.() -> Unit) = step("Открываем менюшку по кнопке Еще") {
        device.wait(Until.findObject(toolbarMoreActionSelector), 1).click()
        DropDownListView { block() }
        deviceScreenshot("tap_more_btn")
    }

    fun longTapOnNote(noteTitle: String = "Title") = step("Лонг тап на заметку в списке") {
        onView(allOf(withId(R.id.titleView), isDisplayed(), withText(noteTitle))).perform(longClick())
        deviceScreenshot("long_tap_on_note")
    }

    fun tapDeleteNote() = step("Тап на корзинку чтобы удалить заметку") {
        onView(allOf(withId(R.id.action_delete), isDisplayed())).perform(click())
        onView(allOf(withId(android.R.id.button1), isDisplayed())).perform(click())
        deviceScreenshot("tap_delete_note")
    }

    inline fun assert(block: Assert.() -> Unit) = Assert().block()

    inner class Assert {
        fun pageOpened() = step("Проверяем открытие главной страницы") {
            onView(
                    allOf(
                            withParent(withId(R.id.toolbarMain)),
                            isDisplayed(),
                            withClassName(endsWith("TextView"))
                    )
            ).check(matches(withText("Swiftnotes")))
            deviceScreenshot("main_page_opened")
        }

        fun noteShown(title: String, note: String) = step("Проверяем, что заметка отобразилась") {
            onView(allOf(withId(R.id.titleView), isDisplayed())).check(matches(withText(title)))
            onView(allOf(withId(R.id.bodyView), isDisplayed())).check(matches(withText(note)))
            deviceScreenshot("title_and_note_shown_check")
        }

        fun noteNotShown(title: String) = step("Проверяем, что заметка не отображается") {
            onView((allOf(withId(R.id.titleView), withText(title)))).check(doesNotExist())
            deviceScreenshot("note_not_shown")
        }
    }

    companion object {
        inline operator fun <T> invoke(block: MainPage.() -> T) = MainPage().block()
    }
}