package com.esp.image;

import com.esp.models.ImageEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.awt.print.PrinterException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImageServiceUpdated {
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    private ImageService imageService;

    public static String GLOBAL_ID = "";

    public EntityManager getEmf(){
        return emf.createEntityManager();
    }

    public InputStream getImgAsInputStreamFromEsp(String name) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://192.168.2.124/saved-photo");
        var response =  client.execute(request);
        var entity = response.getEntity();
        return entity.getContent();
    }

    public InputStream getOutputStream(String fileName) throws Exception {
        var id = ThreadLocalRandom.current().nextLong(2000000,5000000L);
        var FILE_NAME = "src/main/resources/espImgDir/" + id + "_" + fileName + ".jpg";
        var FILE_URL = "http://192.168.2.124/saved-photo";
        var outputFile = new File(FILE_NAME);

        var in = new URL(FILE_URL).openStream();
        Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);

        ImageEntity entity1 =  new ImageEntity(fileName, outputFile);
        var savedEntity = imageService.savePictureFile(entity1);
        GLOBAL_ID = savedEntity.getId();
        return in;
    }

    public String getImgCreatedId() throws Exception {
        if(GLOBAL_ID == null || GLOBAL_ID.equals("")){
            throw new Exception("GLOBAL_ID cannot be less than or equal to zero");
        }
        return GLOBAL_ID;
    }

    /*r id = ThreadLocalRandom.current().nextLong(2000000,5000000L);
        var fileName = id + "_" + name; //var fileName = name + ".jpg";
        var fileOutput = new File("src/main/resources/espImgDir/" +  id + "_" + name + ".jpg"); //var createdFile = this.getClass().getResourceAsStream(fileOutput.getAbsolutePath());

        try{
            URL url = new URL("http://192.168.2.124/saved-photo");
            var  img = ImageIO.read(url);
            ImageIO.write(img, "jpg", fileOutput);
        }catch(Exception e){
            throw new PrinterException("saveImgFromEspToDbAsFile() in EspService Throws: " + e.getMessage());
        }

        ImageEntity m =  new ImageEntity(fileName, fileOutput);
        var ent = imageService.savePictureFile(m);
        return ent.getId();




        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://192.168.2.124/saved-photo");
        var response =  client.execute(request);
        var entity = response.getEntity();
        ImageEntity entity1 =  new ImageEntity(fileName, fileOutput);
        var enty = imageService.savePictureFile(entity1);
        return entity.getContent();*/

}
