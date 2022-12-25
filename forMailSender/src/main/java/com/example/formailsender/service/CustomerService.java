package com.example.formailsender.service;

import com.example.formailsender.entity.Customer;
import com.example.formailsender.payload.ApiResponse;
import com.example.formailsender.payload.CustomerDTO;
import com.example.formailsender.projection.CustomerProjection;
import com.example.formailsender.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerProjection {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JavaMailSender javaMailSender;

//    @Autowired
//    private JavaMailSender mailSender;


    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    public ApiResponse addCustomer(CustomerDTO customerDTO,
                                   String toMail,
                                   String subject,
                                   String body) {

        Customer customer = new Customer();
        customer.setFullName(customerDTO.getFullName());
        customer.setMail(customerDTO.getMail());
        customer.setComment(customerDTO.getComment());
        customerRepository.save(customer);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("jabborovegamberdi2002@gmail.com");
        mailMessage.setTo(customer.getMail());
        mailMessage.setSubject("Sheet! Success!");

        javaMailSender.send(mailMessage);
        return new ApiResponse("success", true);


    }

    @Override
    public ApiResponse addCustomer(CustomerDTO customerDTO) {

        Customer customer = new Customer();
        customer.setFullName(customerDTO.getFullName());
        customer.setMail(customerDTO.getMail());
        customer.setComment(customerDTO.getComment());
        customerRepository.save(customer);

        sendMail(customerDTO.getMail(), customerDTO.getFullName());
        return new ApiResponse("Success", true);
    }

    @Override
    public ApiResponse deleteCustomer(Integer id) {
        return null;
    }

    @Override
    public ApiResponse updateCustomer(Integer id, CustomerDTO customerDTO) {
        return null;
    }

    public void sendMail(String sendingMail, String comment) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("jabborovegamberdi2002@gmail.com");
            message.setTo(sendingMail);
            message.setSubject("Message's header");
            message.setText(comment);
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
