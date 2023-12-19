package com.esay.esayurl;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class esayurlTest {
    @Test
    void testIp(){
        try {
            String ip = esayurl.getIp();
            System.out.println(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
