package com.example.CustomerApplication;

import com.example.CustomerApplication.controller.CustomerController;
import com.example.CustomerApplication.model.Customer;
import com.example.CustomerApplication.repository.CustomerRepository;
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

//    @Autowired
//    private MockMvc mockMvc;

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

}
