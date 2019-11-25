package com.moonpi.swiftnotes.test

import android.support.test.runner.AndroidJUnit4
import com.moonpi.swiftnotes.MainActivity
import com.moonpi.swiftnotes.page.MainPage
import com.moonpi.swiftnotes.rule.SwiftnotesRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.allure.annotations.DisplayName

@RunWith(AndroidJUnit4::class)
@DisplayName("Четыре маленьких теста на это маленькое приложение")
class SwiftnotesTest : AbstractSwiftnotesTest() {

    @get:Rule
    val rule = SwiftnotesRule(MainActivity::class.java, false)

    @Before
    fun setupForCreatePageTests() {
        rule.launchActivity()
    }

    @Test
    @DisplayName("Проверка экрана создания заметки")
    fun newNoteHintsAndSavePopup() {
        MainPage {
            tapNewNote {
                assert {
                    titleHint()
                    textHint()
                }
                tapBack()
                assert {
                    popupText()
                    popupButtons()
                }
                tapNoToSave()
            }
            assert {
                pageOpened()
            }
        }
    }

    @Test
    @DisplayName("Проверка сохранения заметки")
    fun saveNote() {
        val titleText = "Great title"
        val noteText = "Great note"
        MainPage {
            tapNewNote {
                fillTitleWithText(titleText)
                fillNoteWithText(noteText)
                assert {
                    filledNote(noteText)
                    filledTitle(titleText)
                }
                tapBack()
                tapYesToSave()
            }
            assert {
                pageOpened()
                noteShown(titleText, noteText)
            }
        }
    }

//    TODO
//    @Test
//    @DisplayName("Проверка пунктов меню в тулбаре")
//    fun toolbarMenu() {
//        MainPage {
//            tapMoreBtn()
//            assert {
//                btnWithTextOnMenuDisplayed("Backup notes", 0)
//                btnWithTextOnMenuDisplayed("Restore notes", 1)
//                btnWithTextOnMenuDisplayed("Rate app", 2)
//            }
//        }
//    }

    @Test
    @DisplayName("Проверка удаления заметки")
    fun deleteNote() {
        val titleText = "Great title"
        val noteText = "Great note"
        MainPage {
            tapNewNote {
                fillTitleWithText(titleText)
                fillNoteWithText(noteText)
                tapBack()
                tapYesToSave()
            }
            longTapOnNote(titleText)
            tapDeleteNote()
            assert {
                noteNotShown(titleText)
            }
        }
    }
}
