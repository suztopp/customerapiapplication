package com.example.CustomerApplication;

import com.example.CustomerApplication.controller.CustomerController;
import com.example.CustomerApplication.model.Customer;
import com.example.CustomerApplication.repository.CustomerRepository;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private CustomerController controller;

    @MockBean
    private CustomerRepository repo;

    Customer mockCustomer = new Customer(1L, "Testing", "McTesterson", LocalDate.parse("2001-10-10"));
    Customer mockCustomerTwo = new Customer(2L, "Adele", "Hello", LocalDate.parse("2005-12-15"));
    List<Customer> mockedList = new ArrayList<>(Arrays.asList(mockCustomer, mockCustomerTwo));

    @Test
    public void testGetAllCustomers() {
        Mockito.when(repo.findAll()).thenReturn(mockedList);

        ResponseEntity<List<Customer>> response = controller.getAllCustomers();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllCustomers_500_INTERNAL_SERVER_ERROR() {
        Mockito.when(repo.findAll()).thenThrow(HttpServerErrorException.InternalServerError.class);

        ResponseEntity<List<Customer>> response = controller.getAllCustomers();
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCustomerById() {
        Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockCustomer));
        Long testId = mockCustomer.getId();

        ResponseEntity<Optional<Customer>> response = controller.getCustomerById(testId);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockCustomer.getFirstName(), Objects.requireNonNull(response.getBody()).get().getFirstName());
    }

    @Test
    public void testGetCustomerById_404_NOT_FOUND() {
        Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Long testId = mockCustomer.getId();

        ResponseEntity<Optional<Customer>> response = controller.getCustomerById(testId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCustomerByFirstName() {
        Mockito.when(repo.findByFirstName(Mockito.anyString())).thenReturn(Collections.singletonList(mockCustomerTwo));
        String testFirstName = "Adele";

        ResponseEntity<List<Customer>> response = controller.getCustomerByFirstName(testFirstName);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockCustomerTwo.getFirstName(), (Objects.requireNonNull(response.getBody()).get(0).getFirstName()));
    }

    @Test
    public void testGetCustomerByFirstName_404_NOT_FOUND() {
        Mockito.when(repo.findByFirstName(Mockito.anyString())).thenReturn(Collections.emptyList());
        String testFirstName = "Suz";

        ResponseEntity<List<Customer>> response = controller.getCustomerByFirstName(testFirstName);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCustomerByLastName() {
        Mockito.when(repo.findByLastName(Mockito.anyString())).thenReturn(Collections.singletonList(mockCustomerTwo));
        String testLastName = "Hello";

        ResponseEntity<List<Customer>> response = controller.getCustomerByLastName(testLastName);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockCustomerTwo.getLastName(), (Objects.requireNonNull(response.getBody()).get(0).getLastName()));
    }

    @Test
    public void testGetCustomerByLastName_404_NOT_FOUND() {
        Mockito.when(repo.findByLastName(Mockito.anyString())).thenReturn(Collections.emptyList());
        String testLastName = "Topp";

        ResponseEntity<List<Customer>> response = controller.getCustomerByLastName(testLastName);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCustomerByEitherName() {
        Mockito.when(repo.findByFirstNameOrLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(Collections.singletonList(mockCustomerTwo));
        String searchName = "Hello";

        ResponseEntity<List<Customer>> response = controller.getCustomerByEitherName(searchName);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockCustomerTwo.getLastName(), (Objects.requireNonNull(response.getBody()).get(0).getLastName()));
    }

    @Test
    public void testGetCustomerByEitherName_404_NOT_FOUND() {
        Mockito.when(repo.findByFirstNameOrLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(Collections.emptyList());
        String searchName = "Topp";

        ResponseEntity<List<Customer>> response = controller.getCustomerByEitherName(searchName);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCustomerById() {
        Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockCustomer));
        Mockito.when(repo.save(Mockito.any())).thenReturn(mockCustomerTwo); //returns updated new customer
        Long testId = 1L;

        ResponseEntity<Customer> response = controller.updateCustomerById(testId, mockCustomer);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockCustomerTwo.getFirstName(), (Objects.requireNonNull(response.getBody()).getFirstName()));
    }

    @Test
    public void testUpdateCustomerById_404_ID_NOT_FOUND() {
        Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Long testId = 1L;

        ResponseEntity<Customer> response = controller.updateCustomerById(testId, mockCustomer);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCustomerById_500_INTERNAL_SERVER_ERROR() {
        Mockito.when(repo.findById(Mockito.anyLong())).thenThrow(HttpServerErrorException.InternalServerError.class);
        Long testId = 1L;

        ResponseEntity<Customer> response = controller.updateCustomerById(testId, mockCustomer);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerById() {
        Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockCustomer));
        Long testId = 1L;
        ResponseEntity<Customer> response = controller.deleteCustomerById(testId);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerById_404_NOT_FOUND() {
        Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Long testId = 1L;
        ResponseEntity<Customer> response = controller.deleteCustomerById(testId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
