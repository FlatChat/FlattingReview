package models

import org.junit.Test

import org.junit.Assert.*

/**
 * Tests the user model class.
 * @author Ryan
 */
class UserUnitTest {

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getUserID() {
        val id = "-M8DFB82RND0DIO"
        val user = Users()
        user.userID = id
        assertEquals(id, user.userID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setUserID() {
        val id = "-M8DFB82RND0DIO"
        val user = Users()
        user.userID = id
        assertEquals(id, user.userID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getFirstNameUsers() {
        val user = Users()
        user.firstNameUsers = "John"
        assertEquals("John", user.firstNameUsers)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setFirstNameUsers() {
        val user = Users()
        user.firstNameUsers = "John"
        assertEquals("John", user.firstNameUsers)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getLastNameUsers() {
        val user = Users()
        user.lastNameUsers = "Smith"
        assertEquals("Smith", user.lastNameUsers)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setLastNameUsers() {
        val user = Users()
        user.lastNameUsers = "Smith"
        assertEquals("Smith", user.lastNameUsers)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getEmailUsers() {
        val user = Users()
        user.emailUsers = "john@gmail.com"
        assertEquals("john@gmail.com", user.emailUsers)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setEmailUsers() {
        val user = Users()
        user.emailUsers = "john@gmail.com"
        assertEquals("john@gmail.com", user.emailUsers)
    }
}