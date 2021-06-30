package telegrambot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


public class PriceCrypts {
    private Document document;
    private static final List<String> listCrypts = new LinkedList<>();
    private static final LinkedHashMap<String, String> cryptsPriceUsdMap = new LinkedHashMap<>();
    private static final LinkedHashMap<String, String> cryptsPriceEurMap = new LinkedHashMap<>();
    private static final LinkedHashMap<String, String> cryptsPriceRubMap = new LinkedHashMap<>();

    private final String url = "https://www.vbr.ru/crypto/";


    private void connect() {
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getPriceCrypt(String nameCrypt, String currency) {
        connect();
        Elements table = document.select("table[class=common-table detail_table_highlight currency-new-table]");
        getListCrypts(table);

        switch (currency) {
            case "USD":
                getPriceUsd(table);
                return cryptsPriceUsdMap.get(nameCrypt);
            case "EUR":
                getPriceUer(table);
                return cryptsPriceEurMap.get(nameCrypt);
            case "RUB":
                getPriceRub(table);
                return cryptsPriceRubMap.get(nameCrypt);
            default:
                return null;
        }
    }

    private static void getListCrypts(Elements table) {
        Elements crypts = table.select("[class=crypt-link]");
        for (Element elementCrypt : crypts) {
            String crypt = elementCrypt.text();
            crypt = crypt.substring(crypt.indexOf(" ", 0)).trim();
            listCrypts.add(crypt);
        }
    }

    private static void getPriceUer(Elements table) {
        Elements priceEur = table.select("[id=price-eur]");
        int indexInListCrypt = 0;
        for (Element elementPriceEur : priceEur) {
            String priceEurCrypt = elementPriceEur.text();
            cryptsPriceEurMap.put(listCrypts.get(indexInListCrypt), priceEurCrypt);
            indexInListCrypt++;
        }
    }


    private static void getPriceUsd(Elements table) {
        Elements priceUsd = table.select("[id=price-usd]");
        int indexInListCrypt = 0;
        for (Element elementPriceUsd : priceUsd) {
            String priceUsdCrypt = elementPriceUsd.text();
            cryptsPriceUsdMap.put(listCrypts.get(indexInListCrypt), priceUsdCrypt);
            indexInListCrypt++;
        }
    }


    private static void getPriceRub(Elements table) {
        Elements priceRub = table.select("[id=price-rub]");
        int indexInListCrypt = 0;
        for (Element elementPriceRub : priceRub) {
            String priceRubCrypt = elementPriceRub.text();
            cryptsPriceRubMap.put(listCrypts.get(indexInListCrypt), priceRubCrypt);
            indexInListCrypt++;
        }
    }
}
