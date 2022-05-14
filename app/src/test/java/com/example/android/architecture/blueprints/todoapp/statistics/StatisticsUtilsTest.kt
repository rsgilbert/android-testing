package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.Matchers.`is`

class StatisticsUtilsTest {

    // naming convention
    // subjectUnderTest_actionOrInput_resultState
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // Create an active task
        val tasks = listOf<Task>(
            Task("title0", "desc0", isCompleted = false),
            Task("title1", "desc1", isCompleted = false),
            Task("title2", "desc2", false)
        )

        // Call your function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertEquals(100.0f, result.activeTasksPercent)
        assertEquals(0f, result.completedTasksPercent)

        // using hamcrest
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))


    }


    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros(){
        // given
        val tasks = emptyList<Task>()

        // when
        val result = getActiveAndCompletedStats(tasks)

        // then
        assertThat(result.completedTasksPercent, `is`(0f));
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_null_returnsZeros() {
        // given
        val tasks = null;

        // when
        val result = getActiveAndCompletedStats(tasks);

        // then
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_oneOutOfFourCompleted_returns25_75() {
        val tasks = listOf(
            Task("t0", "d0", true),
            Task("t1", "d1", false),
            Task("t2", "d2",false),
            Task("t3", "d3", false)
        )
        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.completedTasksPercent, `is`(25f))
        assertThat(result.activeTasksPercent, `is`(75f))
    }


}








