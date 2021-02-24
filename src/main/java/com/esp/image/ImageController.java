package com.esp.image;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterException;
import java.io.*;

@RestController
@RequestMapping("/user/api/image")
public class ImageController {
    @Autowired
    private ImageService service;

    @Autowired
    private ImageServiceUpdated updatedService;

    //Gets direct from esp and save to db: WORKS WELL
    @GetMapping(value="/getEspImgOutput/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<OutputStream> getImgDirectFromEspAsOutputStream(HttpServletResponse response, @PathVariable String name) throws Exception {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        //response.setCharacterEncoding("UTF-8");
        var input = updatedService.getOutputStream(name);
        StreamUtils.copy(input, response.getOutputStream());

        var id = updatedService.getImgCreatedId();
        System.out.println("id: " + id);
        File imgFile = service.getImgFileFromDb(id);

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
    public InputStream getImage(HttpServletResponse response, @PathVariable String name) throws Exception {
        InputStream byteArray = service.getInputStreamFromFile(name);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(byteArray, response.getOutputStream()); //working but throws exception
        return byteArray;
    }

    //Gets direct from esp and save to db
    @GetMapping(value="/getEspImg/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public InputStream getImgDirectFromEsp(HttpServletResponse response, @PathVariable String name) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setCharacterEncoding("UTF-8");
        var obj = updatedService.getImgAsInputStreamFromEsp(name);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(obj, response.getOutputStream());
        return obj;
    }

    @RequestMapping(value = "/img-file/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public OutputStream getImageFileWithId(HttpServletResponse response, @PathVariable long id) throws Exception {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        if(id < 0)
            throw new Exception("Id cannot be less than or equal to 0");

        var file = service.getImgFileFromDb(id);
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
