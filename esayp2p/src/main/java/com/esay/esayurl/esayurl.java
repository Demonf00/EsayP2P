package com.esay.esayurl;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class esayurl {
    public static String getIp() throws Exception {
        ArrayList<String> data = new ArrayList<String>();

        URL getip = new URL("http://checkip.amazonaws.com/");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(getip.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            data.add(inputLine);
        in.close();
        return data.get(0);
    }
}
