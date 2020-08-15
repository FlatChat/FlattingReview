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

    @Test
    fun getReviewID() {
        val rev = Review()
        rev.reviewID = "-M983R9UH"
        assertEquals("-M983R9UH", rev.reviewID)
    }

    @Test
    fun setReviewID() {
        val rev = Review()
        rev.reviewID = "-M983R9UH"
        assertEquals("-M983R9UH", rev.reviewID)
    }

    @Test
    fun getUserID() {
        val rev = Review()
        rev.userID = "-M983R9UH"
        assertEquals("-M983R9UH", rev.userID)
    }

    @Test
    fun setUserID() {
        val rev = Review()
        rev.userID = "-M983R9UH"
        assertEquals("-M983R9UH", rev.userID)
    }

    @Test
    fun getCleanliness() {
        val rev = Review()
        rev.cleanliness = 0.1
        assertEquals(0.1, rev.cleanliness)
    }

    @Test
    fun setCleanliness() {
        val rev = Review()
        rev.cleanliness = 0.1
        assertEquals(0.1, rev.cleanliness)
    }

    @Test
    fun getLandlord() {
        val rev = Review()
        rev.cleanliness = 0.1
        assertEquals(0.1, rev.cleanliness)
    }

    @Test
    fun setLandlord() {
        val rev = Review()
        rev.landlord = 0.1
        assertEquals(0.1, rev.landlord)
    }

    @Test
    fun getLocation() {
        val rev = Review()
        rev.location = 0.1
        assertEquals(0.1, rev.location)
    }

    @Test
    fun setLocation() {
        val rev = Review()
        rev.location = 0.1
        assertEquals(0.1, rev.location)
    }

    @Test
    fun getValue() {
        val rev = Review()
        rev.value = 0.1
        assertEquals(0.1, rev.value)
    }

    @Test
    fun setValue() {
        val rev = Review()
        rev.value = 0.1
        assertEquals(0.1, rev.value)
    }
}