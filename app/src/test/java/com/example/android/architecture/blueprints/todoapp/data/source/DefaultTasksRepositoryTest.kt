package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.hamcrest.Matchers.`is`
import org.hamcrest.core.IsEqual

class DefaultTasksRepositoryTest {
    private val task0 = Task("t0", "d0", false)
    private val task1 = Task("t1", "d1", true)
    private val task2 = Task("t2", "d2")

    private val remoteTasks = listOf(task0, task1).sortedBy(Task::id)
    private val localTasks = listOf(task2).sortedBy(Task::id)
    private val newTasks = listOf(task2).sortedBy(Task::id)

    private lateinit var tasksRemoteDataSource: TasksDataSource
    private lateinit var tasksLocalDataSource: TasksDataSource

    // Class under test
    private lateinit var tasksRepository: DefaultTasksRepository

    @Before
    fun setupRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        tasksRepository = DefaultTasksRepository(tasksRemoteDataSource, tasksLocalDataSource, Dispatchers.Unconfined)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getTasks_forceUpdateTrue_returnsFromLocal() {
        runBlockingTest {
            val taskListResult = tasksRepository.getTasks(true) as Result.Success
            assertThat(taskListResult.data, IsEqual(remoteTasks))
        }
    }


}