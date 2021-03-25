package com.esp.image;

import com.esp.esp32.EspService;
import com.esp.models.Esp;
import com.esp.models.ImageEntity;
import com.esp.models.User;
import com.esp.user.UserController;
import com.esp.user.UserService;
import net.bytebuddy.utility.RandomString;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImageService {
    @Autowired
    private EspService service;

    @Autowired
    private UserService userService;

    @Autowired
    EntityManagerFactory emf;

    public EntityManager getEmf(){
        return emf.createEntityManager();
    }

    @Transactional
    public ImageEntity savePictureFile(ImageEntity stream) throws Exception {
        Esp esp = new Esp();
        var loggedUser_imageEntity_id = userService.getImageEntity_idFromUserTable();

          //"SELECT u FROM users_imageentity u WHERE u.User_user_id = u.imageEntity_id")  //getUser() from users_imageentity where User_user_id = imageEntity_id;    //SELECT u FROM User u WHERE u.username = :username

        if(loggedUser_imageEntity_id == null){
            throw new Exception("imageEntity_id Not Found from getUseFromImageEntity_UsersTable()");
        }
        esp.setId("esp_id");   //    esp.setId(stream.getId());
        stream.setUser_id(loggedUser_imageEntity_id);
        stream.setId("imageEntity_id" + LocalTime.now()); //i will write class to generate unique Id

        try{
            EntityManager em = getEmf();
            em.getTransaction().begin();
            em.merge(stream);
            //em.persist(esp);
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        return stream;
    }

    private File getImageFileDb(String id) throws FileNotFoundException {
        ImageEntity imgFile = new ImageEntity();
        System.out.println("file retrieved b: , " + imgFile);
        try{
            EntityManager em = service.getEmf();
            em.getTransaction().begin();
            //em.find(ImageEntity.class, id);
            TypedQuery<ImageEntity> hql = em.createQuery("SELECT img FROM ImageEntity img WHERE img.id=?1", ImageEntity.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            hql.setParameter(1, id);
            imgFile = hql.getSingleResult();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        System.out.println("file retrieved: , " + imgFile.toString());
        return imgFile.getFile();
    }

    public File getImageFileDbHalfName(String halfName){
        ImageEntity imgFile = null;
        System.out.println("file retrieved b: , " + imgFile);
        try{
            EntityManager em = service.getEmf();
            em.getTransaction().begin();
            //em.find(ImageEntity.class, id);
            TypedQuery<ImageEntity> hql = em.createQuery("SELECT img FROM ImageEntity img WHERE img.name like CONCAT('%',?1,'%')", ImageEntity.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            hql.setParameter(1, halfName);
            imgFile = hql.getSingleResult();
            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
        System.out.println("file retrieved: , " + imgFile.toString());
        return imgFile.getFile();
    }

    @Transactional
    public File getImgFileFromDb(String id) throws IOException {
        if(id  != null){
            System.out.println("Id cannot be Null"); //throw exception
        }

        var img = getImageFileDb(id);

        if(img == null){
            System.out.println("No Image with the given Id exists");
        }
        return img;
    }

    @Transactional
    public void saveImagAsBlob(ImageEntity imageEntity) throws FileNotFoundException, SQLException {
        try{
            EntityManager em = service.getEmf();
            em.getTransaction().begin();
            //em.find(ImageEntity.class, id);
            //TypedQuery<ImageEntity> hql = em.createQuery("INSERT INTO person (id, first_name, last_name) VALUES (?,?,?)", ImageEntity.class); //@Query(value = "insert into commit_activity_link (commit_id, activity_id) VALUES (?1, ?2)", nativeQuery = true)
            //hql.setParameter(1, id);
            //imgFile = hql.getSingleResult();
            em.createNativeQuery("INSERT INTO imageEntity (id, name, file, date, time) VALUES (?,?,?,?,?)")
                    .setParameter(1, imageEntity.getId())
                    .setParameter(2, imageEntity.getName())
                    .setParameter(3, imageEntity.getFile())
                    .setParameter(4, imageEntity.getDate())
                    .setParameter(5, imageEntity.getTime())
                    .executeUpdate();

            em.createNativeQuery("SELECT + FROM UserHistory JOIN ON User WHERE user.id=?1")
            .setParameter(1, imageEntity.getId());

            em.getTransaction().commit();
        } catch(EntityExistsException e) {
            e.printStackTrace();
        }
    }

    //gets image from esp and saves to Database with the given name, load the file from DB and return it as inputStream
    public InputStream getInputStreamFromFile(String name) throws Exception {
        var id = ThreadLocalRandom.current().nextLong(2000000,5000000L);
        var fileName = id + "_" + name;
        var entityId = service.saveImgFromEspToDbAsFile(fileName);
        var getEntityFile = getImageFileDb(entityId);
        System.out.println("filePath: " + getEntityFile);
        return new FileInputStream(String.valueOf(getEntityFile));
       /* String fileName = LocalDate.now() + "ESP32" +  "name" + ".jpg";
        URL url = new URL("http://192.168.2.124/saved-photo");  // Sample url, replace with yours
        var img = ImageIO.read(url);
        String destinationFile = "src/main/resources/espImgDir/"+ fileName;
        InputStream inputStream = url.openStream();
        return inputStream;*/ //return null;
    }

    //gets image from esp and saves to Database with the given name, load the file from DB and return it as inputStream
    public InputStream getInputStreamWithoutSave(String id) throws Exception {
        var getEntityFile = getImageFileDb(id);
        if(getEntityFile == null){
            getEntityFile = getImageFileDbHalfName(id);
        }
        System.out.println("filePath: " + getEntityFile);
        return new FileInputStream(String.valueOf(getEntityFile));
    }

    public InputStream getInputStreamByName(String id) throws Exception {
        var  getEntityFile = getImageFileDbHalfName(id);

        System.out.println("filePath: " + getEntityFile);
        return new FileInputStream(String.valueOf(getEntityFile));
    }

    public BufferedImage readImageFromFile() throws PrinterException {
        BufferedImage img = null;
        try
        {
            // the line that reads the image file
             img = ImageIO.read(new File("src/main/resources/espImgDir/byteArraySample.jpg"));

            // work with the image here ...
        }
        catch (IOException e)
        {
           throw new PrinterException(e.getMessage());
        }
        return img;

    }

    public void getPicture(){
        Image image = null;
        try {
            URL url = new URL("http://192.168.2.124/saved-photo");
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


       /* JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);*/
    }

    public void espGetPic() throws IOException {
        URL imageUrl = new URL("http://192.168.2.124/saved-photo");  // Sample url, replace with yours

        String destinationFile = "sample.jpg";

        /*******************Multipart Upload Method*********************************
         **              To resources like minio or DB
         ***************************************************************************/

        /********
         * Step 1
         * Create Buffered Image by Reading from Url using ImageIO library
         ********/
        BufferedImage image = ImageIO.read(imageUrl);

        /********
         * Step 2
         * Create ByteArrayOutputStream object to handle Image data
         ********/
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        /********
         * Step 3
         * Write as Image with Jpeg extension to byteArrayOutputStream created in previous step
         ********/
        ImageIO.write(image,"jpg",byteArrayOutputStream);

        /********
         * Step 4
         * Flush image created to byteArrayOutputStream
         ********/
        byteArrayOutputStream.flush();

        /********
         * Step 5
         * Create Random file name but unique by adding timestamp with extension
         ********/
        String fileName = RandomString.make() + new Date().getTime() + ".jpg";

        /********
         * Step 6
         * Now Create MultipartFile using MockMultipartFile by providing
         * @param fileName name of the file
         * @param imageType like "image/jpg"
         * @param ByteArray from ByteArrayOutputStream
         ********/
        MultipartFile multipartFile = new MockMultipartFile(fileName,fileName,"img/jpg",byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.close(); // Close once it is done saving

        /********
         * Step 7
         * Now call Upload/Save method as you want
         ********/

       // fileUpload(multipartFile);   // call your upload/save method here
        //service.savePictureFile(multipartFile);
        /***********file will be uploaded now*****************/

        /*****************File save Method*********************
         ***            To Some file like sample.jpg         ***
         ******************************************************/

        /********
         * Step 1
         * Create Input Stream from Url to store fetched file as stream temporarily
         ********/
        InputStream inputStream = imageUrl.openStream();
        //Mentity m = new Mentity("pioName");
        //service.savePictureFile(m);

        /********
         * Step 2
         * Create Output Stream to write Imput Stream Data to a file locally
         ********/
        OutputStream outputStream = new FileOutputStream(destinationFile);

        /********
         * Step 3
         * Create Helper Variables for handling the Write process
         ********/
        byte[] byteArray = new byte[2048];   // A byte array for checking the end of data stream
        int length;   // length for data stream

        /********
         * Step 4
         * Set While loop to write data from Input Stream to file using Output Stream until the end of stream is finished and exit
         ********/
        while ((length = inputStream.read(byteArray)) != -1) {
            outputStream.write(byteArray, 0, length);   // Will write data to file byte by byte of size 2048
        }

        /********
         * Step 5
         * Close both Output Stream and Input Stream Connections
         ********/
        inputStream.close();
        outputStream.close();
        /************ File is saved now***********************/
    }



    public ImageEntity getEntity(ImageEntity m) {
        return null;
    }
}
