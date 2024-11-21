package vali.springframework.spring6restmvc.services;

import org.springframework.stereotype.Service;
import vali.springframework.spring6restmvc.model.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface CustomerService {

    List<CustomerDto> listCustomer();

    Optional<CustomerDto> getCustomerById(UUID id);

    CustomerDto saveNewCustomer(CustomerDto customer);

    Optional<CustomerDto>  updateCustomerById(UUID customerId, CustomerDto customer);

    void deleteCustomerById(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDto customer);
}

