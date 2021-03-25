package com.esp.image;

import com.esp.models.ImageEntity;
import com.esp.user.LoggedUserImpl;
import com.esp.user.UserController;
import com.esp.user.UserService;
import javassist.NotFoundException;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/user/api/image")
public class ImageController {
    //public static String LOGGED_USER_ID;
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageServiceUpdated updatedImageService;


    //Gets direct from esp and save to db: WORKS WELL
    @GetMapping(value="/getEspImgOutput/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<OutputStream> getImgDirectFromEspAsOutputStream(HttpServletResponse response, HttpServletRequest request,
            @PathVariable String name) throws Exception {
       if(UserController.LOGGED_USER_ID == null){
            System.out.println("user is not logged in");
            throw new NotFoundException("User is not Logged");
        }

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        //response.setCharacterEncoding("UTF-8");
        var input = updatedImageService.getOutputStream(name);
        StreamUtils.copy(input, response.getOutputStream());

        var id = updatedImageService.getImgCreatedId();
        System.out.println("id: " + id);
        var imgFile = imageService.getImgFileFromDb(id);

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

    //gets image from esp and saves to Database with the given name, load the file from DB and return it as inputStream
    @RequestMapping(value = "/get-img-inputStream/{name}", method = RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
    public InputStream getImage(HttpServletResponse response, HttpServletRequest request, @PathVariable String name) throws Exception {
        if(UserController.LOGGED_USER_ID == null){
            System.out.println("user is not logged in");
            throw new NotFoundException("User is not Logged");
        }

        InputStream byteArray = imageService.getInputStreamFromFile(name);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(byteArray, response.getOutputStream()); //working but throws exception
        return byteArray;
    }

    @RequestMapping(value = "/get-img-inputStream-without-save/{id}", method = RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
    public InputStream getStreamNoSave(HttpServletResponse response, HttpServletRequest request, @PathVariable String id) throws Exception {
     //   if(UserController.LOGGED_USER_ID == null){
     //       System.out.println("user is not logged in");
     //       throw new NotFoundException("User is not Logged");
      //  }

        InputStream byteArray = imageService.getInputStreamWithoutSave(id);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(byteArray, response.getOutputStream()); //working but throws exception
        return byteArray;
    }

    @RequestMapping(value = "/get-img-inputStream-by-name/{id}", method = RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
    public InputStream getStreamByName(HttpServletResponse response, HttpServletRequest request, @PathVariable String id) throws Exception {
        //   if(UserController.LOGGED_USER_ID == null){
        //       System.out.println("user is not logged in");
        //       throw new NotFoundException("User is not Logged");
        //  }

        InputStream byteArray = imageService.getInputStreamByName(id);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(byteArray, response.getOutputStream()); //working but throws exception
        return byteArray;
    }

    //Gets direct from esp and save to db
    @GetMapping(value="/getEspImg/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public InputStream getImgDirectFromEsp(HttpServletResponse response, HttpServletRequest request, @PathVariable String name) throws IOException, NotFoundException {
        //loggedUser.loggedUserWithSpring(); //if it passed
        if(UserController.LOGGED_USER_ID == null){
            System.out.println("user is not logged in");
            throw new NotFoundException("User is not Logged");
        }

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setCharacterEncoding("UTF-8");
        var obj = updatedImageService.getImgAsInputStreamFromEsp(name);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(obj, response.getOutputStream());
        return obj;
    }

    @RequestMapping(value = "/img-file/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public OutputStream getImageFileWithId(HttpServletResponse response, @PathVariable String id) throws Exception {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        if(id == null)
            throw new Exception("Id cannot be less than or equal to 0");

        var file = imageService.getImgFileFromDb(id);
        response.setContentLength((int)file.length());

        var in = new FileInputStream(file);
        var out = response.getOutputStream();

        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();

        return out;
    }



}
