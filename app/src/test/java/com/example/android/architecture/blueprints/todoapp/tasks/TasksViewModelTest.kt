package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//

    // Subject under test
    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setupViewModel() {
        // Given a fresh ViewModel
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

    }

    @Test
    fun addNewTask_setsNewTaskEvent() {

        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        // We assert that the liveDataValue is not null
        val liveDataValue = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(liveDataValue.getContentIfNotHandled(), (not(nullValue())))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the Add task action is visible
        val addViewVisibleLiveDataValue = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertTrue(addViewVisibleLiveDataValue)
    }

    @Test
    fun setFilterCompletedTasks_tasksAddViewNotVisible() {
        // When the filter type is COMPLETED_TASKS
        tasksViewModel.setFiltering(TasksFilterType.COMPLETED_TASKS)

        // Then the Add task action is NOT visible
        val addViewVisibleLiveDataValue = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertFalse(addViewVisibleLiveDataValue)
    }
    @Test
    fun setFilterActiveTasks_tasksAddViewNotVisible() {
        // When the filter type is ACTIVE_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ACTIVE_TASKS)

        // Then the Add task action is NOT visible
        val addViewVisibleLiveDataValue = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(addViewVisibleLiveDataValue, `is`(false))
    }
}