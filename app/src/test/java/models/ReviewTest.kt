package models

import org.junit.Test

import org.junit.Assert.*

class ReviewTest {

    @Test
    fun getFlatID() {
        val rev = Review()
        rev.flatID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", rev.flatID)
    }

    @Test
    fun setFlatID() {
        val rev = Review()
        rev.flatID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", rev.flatID)
    }

    @Test
    fun getName() {
        val rev = Review()
        rev.name = "John"
        assertEquals("John", rev.name)
    }

    @Test
    fun setName() {
        val rev = Review()
        rev.name = "John"
        assertEquals("John", rev.name)
    }

    @Test
    fun getAnonymous() {
        val rev = Review()
        rev.anonymous = false
        assertEquals(false, rev.anonymous)
    }

    @Test
    fun setAnonymous() {
        val rev = Review()
        rev.anonymous = false
        assertEquals(false, rev.anonymous)
    }

    @Test
    fun getDate() {
        val rev = Review()
        rev.date = "12/03/2020"
        assertEquals("12/03/2020", rev.date)
    }

    @Test
    fun setDate() {
        val rev = Review()
        rev.date = "12/03/2020"
        assertEquals("12/03/2020", rev.date)
    }

    @Test
    fun getComment() {
        val rev = Review()
        rev.comment = "Hello World"
        assertEquals("Hello World", rev.comment)
    }

    @Test
    fun setComment() {
        val rev = Review()
        rev.comment = "Hello World"
        assertEquals("Hello World", rev.comment)
    }
}