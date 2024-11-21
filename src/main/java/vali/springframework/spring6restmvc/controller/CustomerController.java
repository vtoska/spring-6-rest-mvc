package vali.springframework.spring6restmvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vali.springframework.spring6restmvc.model.CustomerDto;
import vali.springframework.spring6restmvc.services.CustomerService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_URI = "/api/v1/customer";
    public static final String CUSTOMER_BY_ID_URI = CUSTOMER_URI + "/{customerId}";

    private final CustomerService customerService;

    @PatchMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customer) {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customer) {
        if (customerService.updateCustomerById(customerId, customer).isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_URI)
    public ResponseEntity handlePost(@RequestBody CustomerDto customer) {
        CustomerDto savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_URI + "/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_URI)
    public List<CustomerDto> listCustomers() {
        return customerService.listCustomer();
    }

    @GetMapping(value = CUSTOMER_BY_ID_URI)
    public CustomerDto getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.debug("Get customer by id - in controller");

        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
