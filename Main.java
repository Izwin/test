import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Random;


class ImageDownloader {
    private static final String BASE_URL = "https://monitoring.e-kassa.gov.az/pks-monitoring/2.0.0/documents/";
    private static final int MIN_SIZE = 4000; // минимальный размер изображения в байтах

    public static void main(String[] args) throws IOException {
        while(true){
            String imageUrl = BASE_URL + getRandomString(12);
            System.out.println(imageUrl);
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader i2n = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = i2n.readLine(); //you get the IP as a String
            System.out.println(ip);

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream in = new BufferedInputStream(url.openStream());

            BufferedImage image = ImageIO.read(url);
            if (image.getHeight() > 164) {

                System.out.println(image.getHeight());
                byte[] data = in.readAllBytes();
                in.close();

                File outputfile = new File("downloaded_image" + getRandomString(12) + ".jpeg");
                ImageIO.write(image, "jpg", outputfile);
            } else {
                System.out.println("The image is too small to download.");
            }
        }

    }

    private static String getRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}


