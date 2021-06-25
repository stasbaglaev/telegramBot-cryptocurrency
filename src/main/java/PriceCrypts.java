import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PriceCrypts {
    private Document document;

    public PriceCrypts() {
        connect();
    }

    private void connect() {
        try {
            document = Jsoup.connect("https://ru.investing.com/crypto/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getNameCrypto() {
        Elements elements = document.getElementsByClass("float_lang_base_1 relativeAttr");
        return elements.text();
    }


    public String getPriceCrypto() {
        Elements elements = document.getElementsByClass("pid-1057391-last");
        return elements.text();
    }

    /*public String getImage(){
        Elements elements = document.getElementsByClass("flag");
    }*/
}
