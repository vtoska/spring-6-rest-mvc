package vali.springframework.spring6restmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import vali.springframework.spring6restmvc.entities.Beer;
import vali.springframework.spring6restmvc.entities.Customer;
import vali.springframework.spring6restmvc.mapper.CustomerMapper;
import vali.springframework.spring6restmvc.model.CustomerDto;
import vali.springframework.spring6restmvc.repositories.CustomerRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                customerController.deleteCustomerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound(){
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).isEmpty());

    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () ->
                customerController.updateCustomerById(UUID.randomUUID(), CustomerDto.builder().build()));
    }

    @Test
    void updateExistingCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);
        customerDto.setId(null);
        final String updatedName = "UPDATED CUSTOMER";
        customerDto.setName(updatedName);

        ResponseEntity responseEntity = customerController.updateCustomerById(customer.getId(), customerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(updatedName);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewCustomerTest() {
        CustomerDto customerDto = CustomerDto.builder()
                .name("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDto dto = customerController.getCustomerById(customer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testListCustomers() {
        List<CustomerDto> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDto> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(0);
    }
}
