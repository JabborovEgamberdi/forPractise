package com.example.formailsender.controller;

import com.example.formailsender.entity.Customer;
import com.example.formailsender.payload.ApiResponse;
import com.example.formailsender.payload.CustomerDTO;
import com.example.formailsender.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    //    @Autowired
    //    EmailSenderService emailSenderService;

    //    @EventListener(ApplicationReadyEvent.class)


    @GetMapping
    public HttpEntity<?> getAll(){
        List<Customer> customerList = customerService.getAll();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Customer byId = customerService.getById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PostMapping("/add")
    public HttpEntity<?> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        ApiResponse addCustomer = customerService.addCustomer(customerDTO);

        //        emailSenderService.sendSimpleEmail(
        //                customerDTO.getMail(),
        //                "subject",
        //                customerDTO.getComment()
        //        );

        return new ResponseEntity<>(addCustomer, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> updateCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerDTO customerDTO){
        ApiResponse updateCustomer = customerService.updateCustomer(id, customerDTO);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteCustomer(@PathVariable Integer id) {
        ApiResponse deleteCustomer = customerService.deleteCustomer(id);
        return new ResponseEntity<>(deleteCustomer, HttpStatus.OK);
    }

}
