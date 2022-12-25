package com.example.formailsender.projection;

import com.example.formailsender.entity.Customer;
import com.example.formailsender.payload.ApiResponse;
import com.example.formailsender.payload.CustomerDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CustomerProjection {

    List<Customer> getAll();

    Customer getById(Integer id);

    ApiResponse addCustomer(CustomerDTO customerDTO);

    ApiResponse updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO);

    ApiResponse deleteCustomer(@PathVariable Integer id);
}
