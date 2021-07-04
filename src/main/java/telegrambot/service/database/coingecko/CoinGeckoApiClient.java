package telegrambot.service.database.coingecko;

import telegrambot.service.database.coingecko.domain.Coins.*;
import telegrambot.service.database.coingecko.domain.Events.EventCountries;
import telegrambot.service.database.coingecko.domain.Events.EventTypes;
import telegrambot.service.database.coingecko.domain.Events.Events;
import telegrambot.service.database.coingecko.domain.ExchangeRates.ExchangeRates;
import telegrambot.service.database.coingecko.domain.Exchanges.ExchangeById;
import telegrambot.service.database.coingecko.domain.Exchanges.Exchanges;
import telegrambot.service.database.coingecko.domain.Exchanges.ExchangesList;
import telegrambot.service.database.coingecko.domain.Exchanges.ExchangesTickersById;
import telegrambot.service.database.coingecko.domain.Global.Global;
import telegrambot.service.database.coingecko.domain.Ping;
import telegrambot.service.database.coingecko.domain.Status.StatusUpdates;

import java.util.List;
import java.util.Map;

public interface CoinGeckoApiClient {
    Ping ping();

    Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies);

    Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies, boolean includeMarketCap, boolean include24hrVol,
                                              boolean include24hrChange, boolean includeLastUpdatedAt);

    Map<String, Map<String, Double>> getTokenPrice(String id, String contractAddress, String vsCurrencies);

    Map<String, Map<String, Double>> getTokenPrice(String id, String contractAddress, String vsCurrencies, boolean includeMarketCap,
                         boolean include24hrVol, boolean include24hrChange, boolean includeLastUpdatedAt);

    List<String> getSupportedVsCurrencies();

    List<CoinList> getCoinList();

    List<CoinMarkets> getCoinMarkets(String vsCurrency);

    List<CoinMarkets> getCoinMarkets(String vsCurrency,  String ids, String order,  Integer perPage, Integer page,  boolean sparkline, String priceChangePercentage);

    CoinFullData getCoinById(String id);

    CoinFullData getCoinById(String id, boolean localization, boolean tickers, boolean marketData, boolean communityData, boolean developerData, boolean sparkline);

    CoinTickerById getCoinTickerById(String id);

    CoinTickerById getCoinTickerById(String id, String exchangeIds, Integer page, String order);

    CoinHistoryById getCoinHistoryById(String id, String date);

    CoinHistoryById getCoinHistoryById(String id, String data, boolean localization);

    MarketChart getCoinMarketChartById(String id, String vsCurrency, Integer days);

    MarketChart getCoinMarketChartRangeById(String id, String vsCurrency, String from, String to);

    StatusUpdates getCoinStatusUpdateById(String id);

    StatusUpdates getCoinStatusUpdateById(String id, Integer perPage, Integer page);

    CoinFullData getCoinInfoByContractAddress(String id, String contractAddress);

    List<Exchanges> getExchanges();

    List<ExchangesList> getExchangesList();

    ExchangeById getExchangesById(String id);

    ExchangesTickersById getExchangesTickersById(String id);

    ExchangesTickersById getExchangesTickersById(String id, String coinIds, Integer page, String order);

    StatusUpdates getExchangesStatusUpdatesById(String id);

    StatusUpdates getExchangesStatusUpdatesById(String id, Integer perPage, Integer page);

    List<List<String>> getExchangesVolumeChart(String id, Integer days);

    StatusUpdates getStatusUpdates();

    StatusUpdates getStatusUpdates(String category, String projectType, Integer perPage, Integer page);

    Events getEvents();

    Events getEvents(String countryCode, String type, Integer page, boolean upcomingEventsOnly, String fromDate, String toDate);

    EventCountries getEventsCountries();

    EventTypes getEventsTypes();

    ExchangeRates getExchangeRates();

    Global getGlobal();
}
