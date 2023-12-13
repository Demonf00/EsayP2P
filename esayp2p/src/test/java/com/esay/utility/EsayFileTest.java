package com.esay.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EsayFileTest {
    @Test
    void testFile(){
        EsayFile file = new EsayFile();
        file.readFile("C:\\Users\\bruce\\Documents\\Projects\\EsayP2P\\temp");
        file.writeFile("C:\\Users\\bruce\\Documents\\Projects\\EsayP2P\\temp2");
        assertEquals(1, 1);
    }
}
