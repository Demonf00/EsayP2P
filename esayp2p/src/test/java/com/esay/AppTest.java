package com.esay;

import org.junit.jupiter.api.Test;

import com.esay.client.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    void testApp() {
        assertEquals(1, 1);
    }

    @Test
    void testclass() {
        Client.printa();
    }
}
