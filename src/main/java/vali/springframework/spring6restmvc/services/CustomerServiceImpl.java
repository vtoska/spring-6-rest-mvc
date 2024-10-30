package vali.springframework.spring6restmvc.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import vali.springframework.spring6restmvc.model.Customer;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Primary
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> pojoMap ;

    public CustomerServiceImpl() {
        this.pojoMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .name("klienti 1")
                .id(UUID.randomUUID())
                .version(1)
                .createdData(LocalDateTime.now())
                .updatedData(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .name("klienti 2")
                .id(UUID.randomUUID())
                .version(1)
                .createdData(LocalDateTime.now())
                .updatedData(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .name("klienti 3")
                .id(UUID.randomUUID())
                .version(1)
                .createdData(LocalDateTime.now())
                .updatedData(LocalDateTime.now())
                .build();

        pojoMap.put(UUID.randomUUID(), customer1);
        pojoMap.put(UUID.randomUUID(), customer2);
        pojoMap.put(UUID.randomUUID(), customer3);

    }


    @Override
    public List<Customer> listCustomer() {
        return new ArrayList<>(pojoMap.values());
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        log.debug("getPOJOById");

        return Optional.of(pojoMap.get(id));
    }
    @Override
    public void deleteCustomerById(UUID customerId) {
        pojoMap.remove(customerId);

    }
    @Override
    public void patchCustomerById(UUID customerId, Customer customer) {
        pojoMap.put(customerId, customer);

    }


    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {
        Customer existingCustomer = pojoMap.get(customerId);
        if (existingCustomer == null) {

            Customer newCustomer = Customer.builder()
                    .id(customerId)
                    .name(customer.getName())
                    .version(customer.getVersion())
                    .createdData(customer.getCreatedData())
                    .updatedData(customer.getUpdatedData())
                    .build();

            pojoMap.put(customerId, newCustomer);
        } else {
            existingCustomer.setName(customer.getName());
            existingCustomer.setVersion(customer.getVersion());
            existingCustomer.setCreatedData(customer.getCreatedData());
            existingCustomer.setUpdatedData(customer.getUpdatedData());

            pojoMap.put(existingCustomer.getId(), existingCustomer);
        }
    }



    @Override
    public Customer saveNewCustomer(Customer customer) {

        Customer savedCustomer = Customer.builder()
                .name(customer.getName())
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .createdData(LocalDateTime.now())
                .updatedData(LocalDateTime.now())
                .build();

        pojoMap.put(customer.getId(), savedCustomer);

        return savedCustomer;
    }

}
