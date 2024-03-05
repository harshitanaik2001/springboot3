package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Customer;
import com.csi.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/customer")

public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @PostMapping("signup")
    public ResponseEntity<Customer> signUp(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerServiceImpl.signUp(customer), HttpStatus.CREATED);
    }

    @GetMapping("/signin/{custEmailId}/{custPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String custEmailId, @PathVariable String custPassword) {
        return ResponseEntity.ok(customerServiceImpl.signIn(custEmailId, custPassword));
    }

    @GetMapping("/findbyid/{custId}")
    public ResponseEntity<Optional<Customer>> findById(@PathVariable int custId) {
        return ResponseEntity.ok(customerServiceImpl.findById(custId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerServiceImpl.findAll());
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Customer>> sortByName() {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().sorted(Comparator.comparing(Customer::getCustName)).toList());
    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Customer>> sortById() {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().sorted(Comparator.comparingInt(Customer::getCustId)).toList());
    }

    @GetMapping("/sortbyaccountbalance")
    public ResponseEntity<List<Customer>> sortByAccountBalance() {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().sorted(Comparator.comparingDouble(Customer::getCustAccountBalance)).toList());
    }

    @GetMapping("/filterbyaccountbalance/{custAccountBalance}")
    public ResponseEntity<List<Customer>> filterByAccountBalance(@PathVariable double custAccountBalance) {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(cust -> cust.getCustAccountBalance() >= 50000).toList());
    }

    @PutMapping("/update/{custId}")
    public ResponseEntity<Customer> update(@Valid @RequestBody Customer customer, @PathVariable int custId) {
        Customer customer1 = customerServiceImpl.findById(custId).orElseThrow(() -> new RecordNotFoundException("CUST ID DOESNT EXIST"));
        customer1.setCustName(customer.getCustName());
        customer1.setCustAddress(customer.getCustAddress());
        customer1.setCustAccountBalance(customer.getCustAccountBalance());
        customer1.setCustContactNumber(customer.getCustContactNumber());
        customer1.setCustDOB(customer.getCustDOB());
        customer1.setCustGender(customer.getCustGender());
        customer1.setCustEmailId(customer.getCustEmailId());
        customer1.setCustPassword(customer.getCustPassword());

        return new ResponseEntity<>(customerServiceImpl.update(customer1), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletebyid/{custId}")
    public ResponseEntity<String> deleteById(@PathVariable int custId) {
        customerServiceImpl.deleteById(custId);
        return ResponseEntity.ok("DELETED");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        customerServiceImpl.deleteAll();
        return ResponseEntity.ok("ALL DATA DELETED");
    }

}
