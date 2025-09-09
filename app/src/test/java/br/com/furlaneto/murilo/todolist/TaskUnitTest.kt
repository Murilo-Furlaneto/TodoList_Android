package br.com.furlaneto.murilo.todolist

import br.com.furlaneto.murilo.todolist.model.Task
import org.junit.Test
import org.junit.Assert.*

class TaskUnitTest {
    @Test
    fun testTaskCreation() {
        val task = Task("Buy groceries", "Milk, eggs, bread")
        assertEquals("Buy groceries", task.title)
        assertEquals("Milk, eggs, bread", task.description)
        assertFalse(task.isCompleted)
    }
    @Test
    fun testTaskCompletion() {
        val task = Task("Complete project", "Finish the app development")
        assertFalse(task.isCompleted)
        task.isCompleted = true
        assertTrue(task.isCompleted)
    }
    @Test
    fun testEmptyTitle() {
        val task = Task("", "Description")
        assertEquals("", task.title)
    }

    @Test
    fun shouldAllowVeryLongTitleAndDescription() {
        val title = "a".repeat(100)
        val description = "b".repeat(1000)
        val task = Task(title, description)
        assertEquals(title, task.title)
        assertEquals(description, task.description)
    }

}