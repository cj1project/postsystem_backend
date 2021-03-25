package com.esp.converters;

import org.apache.http.client.methods.HttpPost;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

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

    public static InputStream convertImgFileToInputStream(File img, HttpServletResponse response) throws IOException {

        response.setContentLength((int)img.length());

        var in = new FileInputStream(img);
        var out = response.getOutputStream();

        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();

        return in;
    }
}
