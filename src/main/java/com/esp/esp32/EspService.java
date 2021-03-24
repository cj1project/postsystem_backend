package com.esp.esp32;

import com.esp.converters.ConvertInputStreamToText;
import com.esp.image.ImageService;
import com.esp.models.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EspService {
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    private ImageService imageService;

    public EntityManager getEmf(){
        return emf.createEntityManager();
    }

    //user should carry some information from userFor
    @Transactional
    public Esp createNewEsp() throws Exception {
        Esp esp = new Esp();
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            em.merge(esp);  ////em.merge(u); for updates
            //TypedQuery<Esp> hql = em.createQuery("INSERT INTO user (id, name, date, time) VALUES(?1, ?2, ?3, ?4 )", Esp.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            //esp = hql.getSingleResult();
            em.getTransaction().commit();
            em.close();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        return esp;
    }

    public File saveImgFromEspToFileAndToDb(String name) throws Exception {
        URL url = new URL("http://192.168.0.101/saved-photo");
        BufferedImage img = ImageIO.read(url);
        //String fileName = LocalDate.now() + "ESP32" +  name + ".jpg";
        var id = ThreadLocalRandom.current().nextLong(2000000,5000000L);
        var fileName = id + "_" + name;
        var file = new File("src/main/resources/espImgDir/" +  fileName + ".jpg");
        ImageIO.write(img, "jpg", file);
        ImageEntity m =  new ImageEntity(fileName, file);
        imageService.savePictureFile(m);
        //return file;  //target/classes/espImgDir
        return new File("target/classes/espImgDir/" + fileName);
    }

    public ImageEntity saveImgFromEspToFileAndToDbEntity(String name) throws Exception {
        URL url = new URL("http://192.168.0.101/saved-photo");
        BufferedImage img = ImageIO.read(url);
        //String fileName = LocalDate.now() + "ESP32" +  name + ".jpg";
        var id = ThreadLocalRandom.current().nextLong(2000000,5000000L);
        var fileName = id + "_" + name;
        File file = new File("src/main/resources/espImgDir/" +  fileName + ".jpg");
        ImageIO.write(img, "jpg", file);
        ImageEntity m =  new ImageEntity(fileName, file);
        imageService.savePictureFile(m);
        //return file;  //target/classes/espImgDir
        return m;
    }

    //returns the Id of Just created Image
    public long getCapturedAndSavedImgId(String name) throws Exception {
        var ent = saveImgFromEspToFileAndToDbEntity(name);
        return Long.parseLong(ent.getId()); //just return the String
    }

    public String saveImgFromEspToDbAsFile(String name) throws Exception {
        var id = ThreadLocalRandom.current().nextLong(2000000,5000000L);
        var fileName = id + "_" + name; //var fileName = name + ".jpg";
        var fileOutput = new File("src/main/resources/espImgDir/" +  id + "_" + name + ".jpg"); //var createdFile = this.getClass().getResourceAsStream(fileOutput.getAbsolutePath());

        try{
            URL url = new URL("http://192.168.0.101/saved-photo");
            var  img = ImageIO.read(url);
            ImageIO.write(img, "jpg", fileOutput);
        }catch(Exception e){
           throw new PrinterException("saveImgFromEspToDbAsFile() in EspService Throws: " + e.getMessage());
        }

        ImageEntity m =  new ImageEntity(fileName, fileOutput);
        var ent = imageService.savePictureFile(m);
        return ent.getId(); //Or just return the String
    }

    public void captureImage() throws IOException {
        URL url = new URL("http://192.168.0.101/capture");
        BufferedImage img = ImageIO.read(url);
    }

    public String deleteImageInEsp() throws IOException {
        var url = "http://192.168.0.101/delete";
        HttpPost post = new HttpPost(url);
        var httpResponse = HttpClientBuilder.create().build().execute(post);
        var in = httpResponse.getEntity().getContent();

        String content = ConvertInputStreamToText.convertStreamToString(in);
        System.out.println("content should be: photo-deleted: " + content);
        if(content == null){
            throw new IOException("Content from ESP is Null");
        }
        return content;
    }

    @Transactional
    public Object createEntity(Object obj) {
        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
