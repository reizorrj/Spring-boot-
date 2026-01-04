package com.example.demo.config;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public DataLoader(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("===========================================");
        System.out.println("Checking for sample customers...");

        // Find any available division (try ID 1 first, then any other)
        Division division = divisionRepository.findById(1L).orElse(null);

        if (division == null) {
            // Try to get any division from the database
            List<Division> divisions = divisionRepository.findAll();
            if (!divisions.isEmpty()) {
                division = divisions.get(0);
                System.out.println("Using division: " + division.getDivisionName() + " (ID: " + division.getId() + ")");
            } else {
                System.out.println("ERROR: No divisions found in database. Cannot add sample customers.");
                System.out.println("Please ensure your database has divisions before running the application.");
                System.out.println("===========================================");
                return;
            }
        } else {
            System.out.println("Using division: " + division.getDivisionName() + " (ID: " + division.getId() + ")");
        }

        Date now = new Date();

        // Define the 5 required sample customers
        String[][] sampleCustomers = {
                {"John", "Smith", "123 Main St", "30236", "770-555-0101"},
                {"Sarah", "Johnson", "456 Oak Ave", "30238", "770-555-0102"},
                {"Michael", "Williams", "789 Pine Rd", "30240", "770-555-0103"},
                {"Emily", "Brown", "321 Elm Dr", "30241", "770-555-0104"},
                {"David", "Davis", "654 Maple Ln", "30242", "770-555-0105"}
        };

        int addedCount = 0;
        for (String[] customerData : sampleCustomers) {
            String phone = customerData[4];

            // Check if this specific customer already exists by phone
            if (!customerRepository.existsByPhone(phone)) {
                Customer customer = new Customer(
                        customerData[0], // firstName
                        customerData[1], // lastName
                        customerData[2], // address
                        customerData[3], // postalCode
                        customerData[4], // phone
                        now,
                        now,
                        division
                );
                customerRepository.save(customer);
                System.out.println("✓ Added: " + customerData[0] + " " + customerData[1]);
                addedCount++;
            } else {
                System.out.println("• Already exists: " + customerData[0] + " " + customerData[1]);
            }
        }

        if (addedCount > 0) {
            System.out.println("✓ " + addedCount + " sample customer(s) added successfully!");
        } else {
            System.out.println("All 5 sample customers already exist in database");
        }

        System.out.println("Total customers in database: " + customerRepository.count());
        System.out.println("===========================================");
    }
}