package vali.springframework.spring6restmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vali.springframework.spring6restmvc.model.Customer;
import vali.springframework.spring6restmvc.services.CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
public class CustomerController {
    public static final String CUSTOMER_URI = "/api/v1/customer";
    public static final String CUSTOMER_BY_ID_URI = "/api/v1/customer" + "/{customerId}";

    @Autowired
    CustomerService customerService;

    @PatchMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity<Void> updateCustomerPatchById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity<Void> updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
        customerService.updateCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_URI)
    public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_URI + "/" + savedCustomer.getId());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_URI)
    public List<Customer> listCustomers() {
        return customerService.listCustomer();
    }

    @GetMapping(value = CUSTOMER_BY_ID_URI)
    public Customer getCustomer(@PathVariable("customerId") UUID customerId) {
        log.debug("Get customer by id");
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
