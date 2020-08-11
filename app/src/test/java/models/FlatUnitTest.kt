package models

import org.junit.Test

import org.junit.Assert.*

class FlatUnitTest {

    @Test
    fun getFlatID() {
        val flat = Flat()
        flat.flatID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", flat.flatID)
    }

    @Test
    fun getAddress() {
        val flat = Flat()
        flat.address = "606 Union Street, North Dunedin, Dunedin"
        assertEquals("606 Union Street, North Dunedin, Dunedin", flat.address)
    }


    @Test
    fun getBedrooms() {
        val flat = Flat()
        flat.bedrooms = "5"
        assertEquals("5", flat.bedrooms)
    }


    @Test
    fun getBathrooms() {
        val flat = Flat()
        flat.bathrooms = "3"
        assertEquals("3", flat.bathrooms)
    }

    @Test
    fun setFlatID() {
        val flat = Flat()
        flat.flatID = "-M8DFB82RND0DIO"
        assertEquals("-M8DFB82RND0DIO", flat.flatID)
    }

    @Test
    fun setAddress() {
        val flat = Flat()
        flat.address = "606 Union Street, North Dunedin, Dunedin"
        assertEquals("606 Union Street, North Dunedin, Dunedin", flat.address)
    }

    @Test
    fun setBedrooms() {
        val flat = Flat()
        flat.bedrooms = "5"
        assertEquals("5", flat.bedrooms)
    }

    @Test
    fun setBathrooms() {
        val flat = Flat()
        flat.bathrooms = "3"
        assertEquals("3", flat.bathrooms)
    }
}