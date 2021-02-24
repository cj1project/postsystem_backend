package com.esp.esp32;

import com.esp.image.ImageService;
import com.esp.models.Esp;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public File saveImgFromEspToFileAndDb(@PathVariable @Nullable String name) throws IOException, PrinterException {
        //return "Picture Saved to Both File and Database";
        return service.saveImgFromEspToFileAndToDb(name);
    }

    @GetMapping(value ="/saveImgToFileAndDbFile/{name}", produces = MediaType.ALL_VALUE) //gets from Esp and saves to file and the file to Db
    public File saveImgFromEspToFileAndDbEntity(@PathVariable @Nullable String name) throws IOException, PrinterException {
        //return "Picture Saved to Both File and Database";
        var entity = service.saveImgFromEspToFileAndToDbEntity(name);
        return entity.getFile();
    }

    //Saves image from esp to designated file directory
   @GetMapping(value = "/saved-to-file", produces = MediaType.IMAGE_JPEG_VALUE) //saved to file, not so important
   public String saveImgFromEspToFile() throws IOException {
       imageservice.espGetPic(); //working
       return "image saved";
   }

   //Deletes all Images in ESP
    @GetMapping(value = "/delete-all", produces = MediaType.IMAGE_JPEG_VALUE)
    public String espDeleteAll()  {
        return "Esp is emptied";
    }

}
