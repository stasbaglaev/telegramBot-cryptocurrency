//package telegrambot.ability;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//
//public class PriceCrypts {
//    private static final List<String> listCrypts = new LinkedList<>();
//    private static final LinkedHashMap<String, String> cryptsPriceUsdMap = new LinkedHashMap<>();
//    private static final LinkedHashMap<String, String> cryptsPriceEurMap = new LinkedHashMap<>();
//    private static final LinkedHashMap<String, String> cryptsPriceRubMap = new LinkedHashMap<>();
//
//    public String getPriceCryptUsd(String nameCrypt) {
//        Elements table = getTable();
//        getListCrypts(table);
//        getPriceUsd(table);
//        return cryptsPriceUsdMap.get(nameCrypt);
//    }
//
//    public String getPriceCryptEur(String nameCrypt) {
//        Elements table = getTable();
//        getListCrypts(table);
//        getPriceUer(table);
//        return cryptsPriceEurMap.get(nameCrypt);
//    }
//
//    public String getPriceCryptRub(String nameCrypt) {
//        Elements table = getTable();
//        getListCrypts(table);
//        getPriceRub(table);
//        return cryptsPriceRubMap.get(nameCrypt);
//    }
//
//    private Document connect() {
//        String URL_WEB = "https://www.vbr.ru/crypto/";
//        Document document = null;
//        try {
//            document = Jsoup.connect(URL_WEB).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return document;
//    }
//
//    private Elements getTable() {
//        return connect().select("table[class=common-table detail_table_highlight currency-new-table]");
//    }
//
//    private static void getListCrypts(Elements table) {
//        Elements crypts = table.select("[class=crypt-link]");
//        for (Element elementCrypt : crypts) {
//            String crypt = elementCrypt.text();
//            crypt = crypt.substring(crypt.indexOf(" ", 0)).trim();
//            listCrypts.add(crypt);
//        }
//    }
//
//    private static void getPriceUsd(Elements table) {
//        Elements priceUsd = table.select("[id=price-usd]");
//        int indexInListCrypt = 0;
//        for (Element elementPriceUsd : priceUsd) {
//            String priceUsdCrypt = elementPriceUsd.text();
//            cryptsPriceUsdMap.put(listCrypts.get(indexInListCrypt), priceUsdCrypt);
//            indexInListCrypt++;
//        }
//    }
//
//    private static void getPriceUer(Elements table) {
//        Elements priceEur = table.select("[id=price-eur]");
//        int indexInListCrypt = 0;
//        for (Element elementPriceEur : priceEur) {
//            String priceEurCrypt = elementPriceEur.text();
//            cryptsPriceEurMap.put(listCrypts.get(indexInListCrypt), priceEurCrypt);
//            indexInListCrypt++;
//        }
//    }
//
//    private static void getPriceRub(Elements table) {
//        Elements priceRub = table.select("[id=price-rub]");
//        int indexInListCrypt = 0;
//        for (Element elementPriceRub : priceRub) {
//            String priceRubCrypt = elementPriceRub.text();
//            cryptsPriceRubMap.put(listCrypts.get(indexInListCrypt), priceRubCrypt);
//            indexInListCrypt++;
//        }
//    }
//}
