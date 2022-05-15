package com.example.android.architecture.blueprints.todoapp.taskdetail

import FakeAndroidTestRepository
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.content.pm.ApplicationInfoBuilder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*


@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {
    lateinit var tasksRepository : TasksRepository


    @Before
    fun setUp() {
        tasksRepository = FakeAndroidTestRepository()
        // put a fake tasks repository
        ServiceLocator.tasksRepository = tasksRepository
    }

    @After
    fun tearDown() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() = runTest {
        // Given - Add active (incomplete) task to the db
        val activeTask = Task("Active1", "AndroidX Rocks", false)
        tasksRepository.saveTask(activeTask)

        // When - Details fragment launched to display task
        val bundle = TaskDetailFragmentArgs(taskId = activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        // then
        // make sure that the title and description are both shown and correct
        onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_title_text)).check(matches(withText(activeTask.title)))
        onView(withId(R.id.task_detail_description_text)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText(activeTask.description)))
    }

    @Test
    fun activeTaskDetails_completedTask_checkboxChecked() = runTest {
        // given
        val task = Task("t0", "D0", true)
        tasksRepository.saveTask(task)

        // when
        val bundle = TaskDetailFragmentArgs(taskId=task.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        // then
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isChecked()))
    }
    @Test
    fun activeTaskDetails_inCompleteTask_checkboxUnChecked() = runTest {
        // given
        val task = Task("t0", "D0", false)
        tasksRepository.saveTask(task)

        // when
        val bundle = TaskDetailFragmentArgs(taskId=task.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        // then
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isNotChecked()))
    }

}