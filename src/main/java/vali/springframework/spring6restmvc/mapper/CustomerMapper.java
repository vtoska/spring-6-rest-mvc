package vali.springframework.spring6restmvc.mapper;

import org.mapstruct.Mapper;
import vali.springframework.spring6restmvc.entities.Customer;
import vali.springframework.spring6restmvc.model.CustomerDto;

@Mapper
public interface CustomerMapper {

Customer customerDtoToCustomer(CustomerDto dto);
CustomerDto customerToCustomerDto(Customer customer);

}
