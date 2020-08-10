package models

import org.junit.Test

import org.junit.Assert.*

class UsersTest {

    @Test
    fun getUserID() {
        val user = Users()
        user.userID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", user.userID)
    }

    @Test
    fun setUserID() {
        val user = Users()
        user.userID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", user.userID)
    }

    @Test
    fun getFirstNameUsers() {
        val user = Users()
        user.firstNameUsers = "John"
        assertEquals("John", user.firstNameUsers)
    }

    @Test
    fun setFirstNameUsers() {
        val user = Users()
        user.firstNameUsers = "John"
        assertEquals("John", user.firstNameUsers)
    }

    @Test
    fun getLastNameUsers() {
        val user = Users()
        user.lastNameUsers = "Smith"
        assertEquals("Smith", user.lastNameUsers)
    }

    @Test
    fun setLastNameUsers() {
        val user = Users()
        user.lastNameUsers = "Smith"
        assertEquals("Smith", user.lastNameUsers)
    }

    @Test
    fun getEmailUsers() {
        val user = Users()
        user.emailUsers = "john@gmail.com"
        assertEquals("john@gmail.com", user.emailUsers)
    }

    @Test
    fun setEmailUsers() {
        val user = Users()
        user.emailUsers = "john@gmail.com"
        assertEquals("john@gmail.com", user.emailUsers)
    }
}