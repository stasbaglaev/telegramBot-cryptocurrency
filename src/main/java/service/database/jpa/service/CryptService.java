//package service.database.jpa.service;
//
//import org.springframework.stereotype.Service;
//
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Service
//public class CryptService {
//    private final CryptJpaRepository cryptRepository;
//Crypt
//    public CryptService(CryptJpaRepository cryptRepository) {
//        this.cryptRepository = cryptRepository;
//    }
//
//    public List<Integer> findCurrencyUSDAllCrypts() {
//        return cryptRepository.findAll()
//                .stream()
//                .map(Crypt::getCurrencyUSD)
//                .collect(Collectors.toList());
//    }
//
//}
