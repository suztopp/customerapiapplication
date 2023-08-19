package com.example.CustomerApplication.controller;

import com.example.CustomerApplication.model.Customer;
import com.example.CustomerApplication.repository.CustomerRepository;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customerapi")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
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

    @GetMapping("/getCustomerByEitherName/{searchName}")
    public ResponseEntity<List<Customer>> getCustomerByEitherName(@PathVariable String searchName) {
        List<Customer> customerList = customerRepository.findByFirstNameOrLastName(searchName, searchName);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        }
    }

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

    @PostMapping("/updateCustomerById/{id}/{customerToUpdate}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable Long id, @RequestBody Customer customerToUpdate) {
        //retrieve existing customer - need to add error handling here - could add custom error messages if time permits?
        try {
            Optional<Customer> customerSearched = customerRepository.findById(id);
            if (customerSearched.isPresent()) {
                Customer updatedCustomer = customerSearched.get();
                //set new updated customer data
                updatedCustomer.setFirstName(customerToUpdate.getFirstName());
                updatedCustomer.setLastName(customerToUpdate.getLastName());
                updatedCustomer.setBirthDate(customerToUpdate.getBirthDate());
                //need to save the updated details against existing customer
                Customer savedCustomer = customerRepository.save(updatedCustomer);
                return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(customerToUpdate, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(customerToUpdate, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCustomerById/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable Long id) {
        //need to delete if they exist, but error if doesn't exist, will need an error message here
        try {
            Optional<Customer> customerToDelete = customerRepository.findById(id);
            if (customerToDelete.isPresent()) {
//                customerRepository.delete(Objects.requireNonNull(customerToDelete.stream().findFirst().orElse(null)));
                customerRepository.delete(customerToDelete.get());
                return new ResponseEntity<>(customerToDelete.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
