package com.esp;

import com.esp.converters.ConvertInputStreamToText;
import com.esp.models.User;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LoggedINLoggedOutUserTest {

    private WebClient client;

    private ClientConfig clientHelper;

    @Test
    public void testUserLogin() throws IOException, URISyntaxException {
       //var response = clientHelper.loginWithUserNameAndPassword("user", "pass");
        var baseUrl = "http://localhost:8080/";
        HttpUriRequest request = new HttpGet(baseUrl);
        System.out.println("request frame header: " + Arrays.toString(request.getHeaders("Authorization")));
        String encoding = Base64.getEncoder().encodeToString(("user" + ":" + "pass").getBytes(StandardCharsets.UTF_8));
        request.setHeader("Authorization", "Basic " + encoding);
        request.setHeader("Content-Type", "application/json");
        System.out.println("request frame header: " + Arrays.toString(request.getHeaders("Authorization"))
        + "content-type: " + Arrays.toString(request.getHeaders("Content-Type")));
        URIBuilder exampleUri = new URIBuilder(baseUrl + "login");
        HttpPost httpPost = new HttpPost(String.valueOf(exampleUri));

        //request.setHeader("path", "http://localhost:8080/login");
        System.out.println("request: " + request + ", httpPost: " + httpPost);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost );

        var content = (User) httpResponse.getEntity();
        System.out.println("Logged in content: " + content);
        assert ("user").equals(content.getLastname());
        /*BufferedReader in = new BufferedReader (new InputStreamReader(content));
        String line = null;
        int i;
        while ((i = in.read()) != -1) {
            line = in.readLine();
            System.out.println("Logged in content: " + line);
        }
        System.out.println("line: " + line);
        if(line != null)
        assert (line).equals("this is the home page for logged in users");*/

    /*   request = new HttpGet( "http://localhost:8080/user/api/homePage");
       httpResponse = HttpClientBuilder.create().build().execute(request);


        while ((i = in.read()) != -1) {
            line = in.readLine();
            System.out.println("Logged in content: " + line);
        }

        assertThat("this is the user home after  authentication").isEqualTo(line);*/
    }

    @Test
    public void testCreateUser() throws IOException, URISyntaxException {
        //var response = clientHelper.loginWithUserNameAndPassword("user", "pass");
        var baseUrl = "http://localhost:8080/";
        HttpPost request = new HttpPost(baseUrl);
        String encoding = Base64.getEncoder().encodeToString(("user1" + ":" + "pass1").getBytes(StandardCharsets.UTF_8));
        request.setHeader("Authorization", "Basic " + encoding);
        request.setHeader("Content-Type", "application/json");
        System.out.println("content-type: " + Arrays.toString(request.getHeaders("Content-Type")));
        StringEntity params = new StringEntity(" User={\"user_id\":\"ier34ff\",\"username\":\"user\",\"password\":\"pass\"," +
                "\"firstname\":\"fname\",\"lastname\":\"lname\", \"email\":\"email\",\"imageEntityId\":\"imgId\",}");
        Gson gson = new Gson();
        var paramsToJson= new StringEntity(gson.toJson(params));
        //var paramsToJson = gson.toJson(params);
        request.setEntity(paramsToJson);

        URIBuilder exampleUri = new URIBuilder(baseUrl + "user/api/register");
        HttpPost httpPost = new HttpPost(String.valueOf(exampleUri));

        System.out.println("httpPost: " + httpPost);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);

        var content =  httpResponse.getEntity().getContent(); //usercreatedId
        var contentToString = ConvertInputStreamToText.convertStreamToString(content);
        /*BufferedReader in = new BufferedReader (new InputStreamReader(content));
        String line = null;
        int i;
        while ((i = in.read()) != -1) {
            line = in.readLine();
            System.out.println("Logged in content: " + line);
        }
        System.out.println("line: " + line);
        assert line == null || (line).equals("ier34ff");
        System.out.println("Logged in content: " + content);*/

        assert ("ier34ff").equals(contentToString);

        /*HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("http://yoururl");
            StringEntity params = new StringEntity("details={\"name\":\"xyz\",\"age\":\"20\"} ");
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
        } finally {
            // @Deprecated httpClient.getConnectionManager().shutdown();
}*/
    }

    @Test
    public void testHomePage() throws IOException {
        HttpUriRequest request = new HttpGet( "http://localhost:8080/user/api/homePage");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        var content =  httpResponse.getEntity().getContent();
        BufferedReader in = new BufferedReader (new InputStreamReader(content));
        String line = null;

        int i;
        while ((i = in.read()) != -1) {
            line = in.readLine();
            System.out.println("Logged in content: " + line);
        }
        assert false;
        assertThat("this is the user home after  authentication").isEqualTo(line);
    }

}
