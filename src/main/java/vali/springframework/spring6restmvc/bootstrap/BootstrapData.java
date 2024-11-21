package vali.springframework.spring6restmvc.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vali.springframework.spring6restmvc.entities.Beer;
import vali.springframework.spring6restmvc.entities.Customer;
import vali.springframework.spring6restmvc.model.BeerDto;
import vali.springframework.spring6restmvc.model.BeerStyle;
import vali.springframework.spring6restmvc.model.CustomerDto;
import vali.springframework.spring6restmvc.repositories.BeerRepository;
import vali.springframework.spring6restmvc.repositories.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();

    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(675)
                    .createdData(LocalDateTime.now())
                    .updatedData(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("12345656")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(345)
                    .createdData(LocalDateTime.now())
                    .updatedData(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12345678")
                    .price(new BigDecimal("10.99"))
                    .quantityOnHand(349)
                    .createdData(LocalDateTime.now())
                    .updatedData(LocalDateTime.now())
                    .build();
            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }
    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("klienti 1")
                    .createdData(LocalDateTime.now())
                    .updatedData(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("klienti 2")
                    .createdData(LocalDateTime.now())
                    .updatedData(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("klienti 3")
                    .createdData(LocalDateTime.now())
                    .updatedData(LocalDateTime.now())
                    .build();
            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }

}
