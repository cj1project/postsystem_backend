package com.esp.converters;

import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConvertInputStreamToText {

    public static String convertStreamToString(InputStream stream) throws IOException {

        if(stream == null){
            throw new IOException("Stream is null");
        }

        BufferedReader in = new BufferedReader (new InputStreamReader(stream));
        String line = null;
        int i;
        while ((i = in.read()) != -1) {
            line = in.readLine();
            System.out.println("Logged in content: " + line);
        }
        System.out.println("line: " + line);
        return line;
    }
}
