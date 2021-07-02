package telegrambot.service;

import java.io.*;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsCodeSave {
    public static void saveHtml() throws Exception {
        String httpsURL = "https://ru.investing.com/crypto/currencies";

        BufferedWriter bw = new BufferedWriter(new FileWriter("filename.html"));
        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setRequestProperty ( "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" );
        InputStream ins = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins, "Windows-1252");
        BufferedReader in = new BufferedReader(isr);
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            bw.write(inputLine);
        }
        in.close();
        bw.close();
    }
}