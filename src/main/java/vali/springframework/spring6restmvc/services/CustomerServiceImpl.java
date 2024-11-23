package vali.springframework.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vali.springframework.spring6restmvc.model.CustomerDto;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDto> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDto customer1 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Klienti 1")
                .createdData(LocalDateTime.now())
                .updatedData(LocalDateTime.now())
                .build();

        CustomerDto customer2 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Klienti 2")
                .createdData(LocalDateTime.now())
                .updatedData(LocalDateTime.now())
                .build();

        CustomerDto customer3 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Klienti 3")
                .createdData(LocalDateTime.now())
                .updatedData(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDto customer) {
        CustomerDto existing = customerMap.get(customerId);

        if (existing != null && StringUtils.hasText(customer.getName())) {
            existing.setName(customer.getName());
            existing.setUpdatedData(LocalDateTime.now());
        }
    }

    @Override
    public List<CustomerDto> listCustomer() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDto> getCustomerById(UUID id) {
        log.debug("Fetching customer by ID: {}", id);
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customer) {
        UUID id = UUID.randomUUID();
        CustomerDto newCustomer = CustomerDto.builder()
                .id(id)
                .version(1)
                .name(customer.getName())
                .createdData(LocalDateTime.now())
                .updatedData(LocalDateTime.now())
                .build();

        customerMap.put(id, newCustomer);
        return newCustomer;
    }

    @Override
    public Optional<CustomerDto> updateCustomerById(UUID customerId, CustomerDto customer) {
        CustomerDto existing = customerMap.get(customerId);

        if (existing != null) {
            existing.setName(customer.getName());
            existing.setVersion(customer.getVersion());
            existing.setUpdatedData(LocalDateTime.now());
        }

        return Optional.ofNullable(existing);
    }
}
