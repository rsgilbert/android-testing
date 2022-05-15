package com.example.android.architecture.blueprints.todoapp.tasks

import FakeAndroidTestRepository
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource.saveTask
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskFragment
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskFragmentDirections
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class TasksFragmentTest {
    private lateinit var tasksRepository: TasksRepository

    @Before
    fun initRepository() = runTest {
        tasksRepository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = tasksRepository
        tasksRepository.saveTask(Task("T0", "D0", false, "id0"))
        tasksRepository.saveTask(Task("T1", "D1", true, "id1"))
        tasksRepository.saveTask(Task("T2", "D2", true, "id2"))
    }

    @After
    fun cleanUpDb()  {
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() = runTest {
        // given
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        // when
        onView(withId(R.id.tasks_list))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            hasDescendant(withText("T1")), click()))

        // then. verify that we navigate to correct detail screen
        verify(navController).navigate(TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment("id1"))
    }

    @Test
    fun navigationCorrectness_clickAddTaskButton_navigateToAddEditFragment() {
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(withId(R.id.add_task_fab))
            .perform(click())

        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
        verify(navController).navigate(TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(null, applicationContext.getString(R.string.add_task)))
    }


}