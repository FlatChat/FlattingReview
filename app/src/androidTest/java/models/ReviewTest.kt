package models

import org.junit.Test

import org.junit.Assert.*

/**
 * Tests the Review model class.
 * @author Ryan
 */
class ReviewTest {

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getFlatID() {
        val id = "-M8DFB82RND0DIO"
        val rev = Review()
        rev.flatID = id
        assertEquals(id, rev.flatID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setFlatID() {
        val id = "-M8DFB82RND0DIO"
        val rev = Review()
        rev.flatID = id
        assertEquals(id, rev.flatID)
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
        val date = "12/03/2020"
        val rev = Review()
        rev.date = date
        assertEquals(date, rev.date)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setDate() {
        val date = "12/03/2020"
        val rev = Review()
        rev.date = date
        assertEquals(date, rev.date)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getComment() {
        val comment = "Hello World!"
        val rev = Review()
        rev.comment = comment
        assertEquals(comment, rev.comment)
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

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getReviewID() {
        val id = "-M983R9UH"
        val rev = Review()
        rev.reviewID = id
        assertEquals(id, rev.reviewID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setReviewID() {
        val id = "-M983R9UH"
        val rev = Review()
        rev.reviewID = id
        assertEquals(id, rev.reviewID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getUserID() {
        val rev = Review()
        rev.userID = "-M983R9UH"
        assertEquals("-M983R9UH", rev.userID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setUserID() {
        val rev = Review()
        rev.userID = "-M983R9UH"
        assertEquals("-M983R9UH", rev.userID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getCleanliness() {
        val rev = Review()
        rev.cleanliness = 0.1
        assertEquals("", 0.1, rev.cleanliness, 0.0)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setCleanliness() {
        val rev = Review()
        rev.cleanliness = 0.1
        assertEquals("", 0.1, rev.cleanliness, 0.0)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getLandlord() {
        val rev = Review()
        rev.landlord = 0.1
        assertEquals("", 0.1, rev.landlord, 0.0)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setLandlord() {
        val rev = Review()
        rev.landlord = 0.1
        assertEquals("",0.1, rev.landlord, 0.0)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getLocation() {
        val rev = Review()
        rev.location = 0.1
        assertEquals("", 0.1, rev.location,0.0)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setLocation() {
        val rev = Review()
        rev.location = 0.1
        assertEquals("", 0.1, rev.location, 0.0)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getValue() {
        val rev = Review()
        rev.value = 0.1
        assertEquals("", 0.1, rev.value, 0.0)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setValue() {
        val rev = Review()
        rev.value = 0.1
        assertEquals("", 0.1, rev.value, 0.0)
    }
}