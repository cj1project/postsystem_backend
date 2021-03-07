package com.esp;

import org.apache.catalina.connector.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

public class ClientConfig {

    private WebClient client;
    private HttpClient httpClient;
    private Request request;

    public ClientConfig(WebClient client) {
        this.client = client;
        this.httpClient = HttpClient.newHttpClient();
    }

    public HttpServletResponse getStringResponse(HttpServletResponse response) throws Exception {

        /*return a live image from Esp with name and timestamp set*/
        //capture and confirm that it was successful
        var capture = client
                .get()
                .uri("http://localhost:8080/user/api/homePage")
                .retrieve()
                .bodyToMono(String.class);

        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        response.getWriter().write(String.valueOf(capture));
        return response;
    }

    public WebClient.ResponseSpec getApiPath(String urlPath) {
        var authDetail = "";
        byte[] decodedBytes = Base64.getDecoder().decode(authDetail);
        String decodedString = new String(decodedBytes);
        //request.setAttribute("Authorization", authDetail);
        return client
                .get()
                .uri("http://localhost:8080/" + urlPath)
                .retrieve();
                //.bodyToMono(Employee.class);

    }

    public String loginWithUserNameAndPassword(String username, String password){
        try {
            URL url = new URL ("http://localhost:8080/login");
            String encoding = Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = connection.getInputStream();
            BufferedReader in = new BufferedReader (new InputStreamReader(content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Logged in content: " + line);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "Logged in Successfully";
    }

    protected ResponseEntity<String> executeRequest() {

       /* request.method(HttpMethod.GET);
        request.timeout(5000, TimeUnit.MILLISECONDS);
        ContentResponse response = request.send();
        if (response.getStatus() != HttpStatus.OK_200) {
            XxlJobLogger.log("Http StatusCode({}) Invalid.", response.getStatus());
            return FAIL;
            String responseMsg = response.getContentAsString();
            XxlJobLogger.log(responseMsg);
            return SUCCESS;*/
        return  null;
    }


    public HttpServletResponse logout(String urlPath){
        HttpServletResponse logoutSpecResponse = null;
        try {
            logoutSpecResponse = (HttpServletResponse) getApiPath(urlPath);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(logoutSpecResponse == null){
            System.out.println("logoutSpecResponse is null");
        }
        return logoutSpecResponse;
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
