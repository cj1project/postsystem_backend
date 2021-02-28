package com.esp;

import com.esp.esp32.EspService;
import com.esp.image.ImageService;
import com.esp.image.ImageServiceUpdated;
import com.esp.models.User;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.time.Duration;

@RestController
@RequestMapping("/user/api/automate")
public class Automate {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);
    private final WebClient localApiClient;

    @Autowired
    private ImageServiceUpdated updatedImageService;

    @Autowired
    private EspService espService;

    @Autowired
    private ImageService imageService;

    @Autowired
    public Automate(WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    @GetMapping(value="/auto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User homeA(@PathVariable long id){

        var user = localApiClient
                .get()
                .uri("http://localhost:8080/user/api/user/get-user/" + id)
                .retrieve()
                .bodyToMono(User.class)
                .block(REQUEST_TIMEOUT);

        return user;
    }

    @GetMapping(value="/on", produces = MediaType.IMAGE_JPEG_VALUE)
    public HttpServletResponse automate(HttpServletResponse response) throws Exception {

        /*return a live image from Esp with name and timestamp set*/
        //capture and confirm that it was successful
        var capture = localApiClient
                .get()
                .uri("http://localhost:8080/user/api/esp/capture")
                .retrieve()
                .bodyToMono(String.class)
                .block(REQUEST_TIMEOUT);

        if(capture == null){
            throw new Exception("Esp 32 unable to capture");
        }

        //get the captured Img, save to file and db and return the img
        var id = espService.getCapturedAndSavedImgId("auto_take");
        var getCapturedImg = localApiClient
                .get()
                .uri("/user/api/image/getEspImgOutput/on_img")
                .retrieve()
                .bodyToMono(OutputStream.class)
                .block(REQUEST_TIMEOUT);
        if(getCapturedImg == null){
            throw new Exception("Unable to fetch Captured Image");
        }
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        return response;
    }

    @GetMapping(value = "/auto", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<OutputStream> autoMode(HttpServletResponse response) throws Exception {
        //try
        CloseableHttpClient httpInstance1 = HttpClientBuilder.create().build();
        var takePhoto =  httpInstance1.execute(new HttpGet("http://localhost:8080/user/api/esp/capture"));

        if(takePhoto == null){
            throw new Exception("Error capturing picture");
        }
        httpInstance1.close();

        CloseableHttpClient client = HttpClientBuilder.create().build();
        //var resp = client.execute(new HttpGet("http://localhost:8080/api/image/getEspImgOutput/auto_img"));
        HttpGet request = new HttpGet("http://localhost:8080/user/api/image/getEspImgOutput/auto_img");
        var res =  client.execute(request);
        var responseEntity = res.getEntity().getContent();
        StreamUtils.copy(responseEntity, response.getOutputStream());

        var id = updatedImageService.getImgCreatedId();
        System.out.println("id: " + id);
        File imgFile = imageService.getImgFileFromDb(id);

        response.setContentLength((int)imgFile.length());

        var in = new FileInputStream(imgFile);
        var out = response.getOutputStream();

        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();

        return new ResponseEntity<OutputStream>(out, HttpStatus.OK);

    }
}
