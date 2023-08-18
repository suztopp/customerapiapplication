package com.example.CustomerApplication.controller;

import com.example.CustomerApplication.model.Customer;
import com.example.CustomerApplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customerapi")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            //try to create a new list of customers, hitting the customer repo with findAll command
            //return this response as a response entitle with OK if successful
            List<Customer> customerList = new ArrayList<>(customerRepository.findAll());
            if (customerList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        } catch (Exception e) {
            //alternatively return an exception with a 500 - expand on this later TODO
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    TODO getCustomerByName() - get by partial name first or last whichever is given

    @GetMapping("/getCustomerByFirstName/{firstName}")
    public ResponseEntity<List<Customer>> getCustomerByFirstName(@PathVariable String firstName) {
        List<Customer> customerList = customerRepository.findByFirstName(firstName);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        }
    }

    @GetMapping("/getCustomerByLastName/{lastName}")
    public ResponseEntity<List<Customer>> getCustomerByLastName(@PathVariable String lastName) {
        List<Customer> customerList = customerRepository.findByLastName(lastName);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        }
    }

    @GetMapping("/getCustomerByBirthDate/{birthDate}")
    public ResponseEntity<List<Customer>> getCustomerByBirthDate(@PathVariable LocalDate birthDate) { //should this come in as an int?
        List<Customer> customerList = customerRepository.findByBirthDate(birthDate);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        }
    }

    @PostMapping("/addNewCustomer")
    public ResponseEntity<Customer> addNewCustomer(@RequestBody Customer customer) {
        try {
            Customer customerSaved = customerRepository.save(customer);
            return new ResponseEntity<>(customerSaved, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateCustomerById/{id}")
    public void updateCustomerById(@PathVariable int id) {

    }

    @DeleteMapping("/deleteCustomerById/{id}")
    public void deleteCustomerById(@PathVariable int id) {

    }
}
