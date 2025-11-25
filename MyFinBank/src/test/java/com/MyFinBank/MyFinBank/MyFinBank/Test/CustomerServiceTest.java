package com.MyFinBank.MyFinBank.MyFinBank.Test;

import com.MyFinBank.MyFinBank.Repo.CustomerRepository;
import com.MyFinBank.MyFinBank.model.Customer;
import com.MyFinBank.MyFinBank.service.imp.CustomerServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    public CustomerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer() {
        Customer c = new Customer();
        c.setName("Praveen");

        when(customerRepository.save(c)).thenReturn(c);

        Customer result = customerService.register(c);

        assertEquals("Praveen", result.getName());
        verify(customerRepository, times(1)).save(c);
    }

    @Test
    void testGetCustomerByEmail() {
        Customer c = new Customer();
        c.setEmail("test@test.com");

        when(customerRepository.findByEmail("test@test.com")).thenReturn(Optional.of(c));

        Customer result = customerService.getByEmail("test@test.com");

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
    }
}
