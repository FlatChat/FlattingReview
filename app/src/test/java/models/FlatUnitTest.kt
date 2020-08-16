package models

import org.junit.Test

import org.junit.Assert.*

class FlatUnitTest {

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getFlatID() {
        val flat = Flat()
        flat.flatID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", flat.flatID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getAddress() {
        val flat = Flat()
        flat.address = "606 Union Street, North Dunedin, Dunedin"
        assertEquals("606 Union Street, North Dunedin, Dunedin", flat.address)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getBedrooms() {
        val flat = Flat()
        flat.bedrooms = "5"
        assertEquals("5", flat.bedrooms)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun getBathrooms() {
        val flat = Flat()
        flat.bathrooms = "3"
        assertEquals("3", flat.bathrooms)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setFlatID() {
        val flat = Flat()
        flat.flatID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", flat.flatID)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setAddress() {
        val flat = Flat()
        flat.address = "606 Union Street, North Dunedin, Dunedin"
        assertEquals("606 Union Street, North Dunedin, Dunedin", flat.address)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setBedrooms() {
        val flat = Flat()
        flat.bedrooms = "5"
        assertEquals("5", flat.bedrooms)
    }

    /**
     * Tests the getters and setters.
     *
     */
    @Test
    fun setBathrooms() {
        val flat = Flat()
        flat.bathrooms = "3"
        assertEquals("3", flat.bathrooms)
    }
}