package models

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests the Review model class.
 * @author Ryan
 */
class ReviewUnitTest {

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getFlatID() {
        val rev = Review()
        rev.flatID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", rev.flatID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setFlatID() {
        val rev = Review()
        rev.flatID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", rev.flatID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getName() {
        val rev = Review()
        rev.name = "John"
        assertEquals("John", rev.name)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setName() {
        val rev = Review()
        rev.name = "John"
        assertEquals("John", rev.name)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getAnonymous() {
        val rev = Review()
        rev.anonymous = false
        assertEquals(false, rev.anonymous)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setAnonymous() {
        val rev = Review()
        rev.anonymous = false
        assertEquals(false, rev.anonymous)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getDate() {
        val rev = Review()
        rev.date = "12/03/2020"
        assertEquals("12/03/2020", rev.date)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setDate() {
        val rev = Review()
        rev.date = "12/03/2020"
        assertEquals("12/03/2020", rev.date)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getComment() {
        val rev = Review()
        rev.comment = "Hello World"
        assertEquals("Hello World", rev.comment)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setComment() {
        val rev = Review()
        rev.comment = "Hello World"
        assertEquals("Hello World", rev.comment)
    }
}