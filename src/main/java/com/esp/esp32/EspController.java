package com.esp.esp32;

import com.esp.converters.ConvertInputStreamToText;
import com.esp.image.ImageService;
import com.esp.models.Esp;
import com.esp.models.ImageEntity;
import com.esp.user.UserController;
import com.sun.istack.Nullable;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.awt.print.PrinterException;
import java.io.*;

@RestController
@RequestMapping("/user/api/esp")
public class EspController {

    @Autowired
    private EspService service;
    @Autowired
    private ImageService imageservice;

    @PostMapping(name="/createEsp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Esp createNewEsp() throws Exception {
        return service.createNewEsp();
    }

    @GetMapping("/capture")
    public String capturePix() throws IOException {
        service.captureImage();
        return "Picture taken";
    }

    @GetMapping(value ="/saveImgToFileAndDatabase/{name}", produces = MediaType.ALL_VALUE) //gets from Esp and saves to file and the file to Db
    public File saveImgFromEspToFileAndDb(@PathVariable @Nullable String name) throws Exception {
        //return "Picture Saved to Both File and Database";
        return service.saveImgFromEspToFileAndToDb(name);
    }

    @GetMapping(value ="/saveImgToFileAndDbFile/{name}", produces = MediaType.ALL_VALUE) //gets from Esp and saves to file and the file to Db
    public InputStream saveImgFromEspToFileAndDbEntity(@PathVariable @Nullable String name, HttpServletResponse response) throws Exception {
        //return "Picture Saved to Both File and Database";
        var entity = service.saveImgFromEspToFileAndToDbEntity(name);
        return ConvertInputStreamToText.convertImgFileToInputStream(entity.getFile(), response);
    }

    //Saves image from esp to designated file directory
   @GetMapping(value = "/saved-to-file", produces = MediaType.IMAGE_JPEG_VALUE) //saved to file, not so important
   public String saveImgFromEspToFile() throws IOException {
       imageservice.espGetPic(); //working
       return "image saved";
   }

   @PostMapping(value = "/acceptImgFromEsp", consumes = MediaType.IMAGE_JPEG_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
   public ResponseEntity<OutputStream> acceptImgFromEsp(HttpServletResponse response) throws IOException {
       var responseOutputStream =  response.getOutputStream();
       //response.sendRedirect("/user/api/auto");
        /*
       byte[] buf = new byte[1024];
       int count = 0;
       while ((count = in.read(buf)) >= 0) {
           responseOutputStream.write(buf, 0, count);
       }
       responseOutputStream.close();
       in.close();*/
       var file = new FileOutputStream("outputFromEsp");

        //imageservice.savePictureFile();
       var resp = new HttpServletResponseWrapper(response);
       resp.sendRedirect("/user/api/auto");
       var respPayLoad = resp.getOutputStream();

       return new ResponseEntity<>(respPayLoad, HttpStatus.OK);
   }

   //Deletes all Images in ESP
    @GetMapping(value = "/delete-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public String espDeleteAll() throws IOException, NotFoundException {
        if(UserController.LOGGED_USER_ID == null){
            System.out.println("user is not logged in");
            throw new NotFoundException("User is not Logged");
        }
        return service.deleteImageInEsp();
    }

}