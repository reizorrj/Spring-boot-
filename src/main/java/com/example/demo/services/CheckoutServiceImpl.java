package com.example.demo.services;

import com.example.demo.dao.CartRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.dao.VacationRepository;
import com.example.demo.dao.ExcursionRepository;
import com.example.demo.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;
    private final VacationRepository vacationRepository;
    private final ExcursionRepository excursionRepository;

    public CheckoutServiceImpl(CartRepository cartRepository,
                               CustomerRepository customerRepository,
                               DivisionRepository divisionRepository,
                               VacationRepository vacationRepository,
                               ExcursionRepository excursionRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
        this.vacationRepository = vacationRepository;
        this.excursionRepository = excursionRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        System.out.println("\n=== CHECKOUT REQUEST RECEIVED ===");

        if (purchase == null) {
            throw new IllegalArgumentException("Purchase cannot be null");
        }

        Cart cart = purchase.getCart();
        Customer customer = purchase.getCustomer();
        Set<CartItem> cartItems = purchase.getCartItems();

        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        System.out.println("Package Price received: " + cart.getPackagePrice());

        if (cart.getPackagePrice() == null || cart.getPackagePrice().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("WARNING: Invalid package price, attempting to calculate from cart items...");

            BigDecimal calculatedPrice = BigDecimal.ZERO;

            if (cartItems != null && !cartItems.isEmpty()) {
                for (CartItem item : cartItems) {
                    if (item.getVacation() != null && item.getVacation().getTravelPrice() != null) {
                        calculatedPrice = calculatedPrice.add(item.getVacation().getTravelPrice());
                        System.out.println("Added vacation price: " + item.getVacation().getTravelPrice());
                    }

                    if (item.getExcursions() != null) {
                        for (Excursion excursion : item.getExcursions()) {
                            if (excursion.getExcursionPrice() != null) {
                                calculatedPrice = calculatedPrice.add(excursion.getExcursionPrice());
                                System.out.println("Added excursion price: " + excursion.getExcursionPrice());
                            }
                        }
                    }
                }
            }

            if (calculatedPrice.compareTo(BigDecimal.ZERO) > 0) {
                cart.setPackagePrice(calculatedPrice);
                System.out.println("Calculated package price: " + calculatedPrice);
            } else {
                cart.setPackagePrice(new BigDecimal("99.99"));
                System.out.println("WARNING: Could not calculate price, using minimum: 99.99");
            }
        }

        if (cart.getId() != null && cart.getId() == 0) {
            cart.setId(null);
            System.out.println("WARNING: Cart ID was 0, reset to null for new cart");
        }

        if (cart.getPartySize() == null || cart.getPartySize() <= 0) {
            cart.setPartySize(1);
            System.out.println("WARNING: No party size, using default: 1");
        }

        if (customer.getDivision() == null || customer.getDivision().getId() == null) {
            Division defaultDivision = divisionRepository.findById(1L)
                    .orElseGet(() -> divisionRepository.findAll().stream()
                            .findFirst()
                            .orElse(null));

            if (defaultDivision != null) {
                customer.setDivision(defaultDivision);
                System.out.println("WARNING: No division, using default: " + defaultDivision.getDivisionName());
            } else {
                throw new IllegalArgumentException("No divisions available in database");
            }
        }


        if (customer.getPostalCode() == null || customer.getPostalCode().trim().isEmpty()) {
            customer.setPostalCode("00000");
            System.out.println("WARNING: No postal code, using default: 00000");
        }


        if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone is required");
        }

        Date now = new Date();
        if (customer.getCreateDate() == null) {
            customer.setCreateDate(now);
        }
        customer.setLastUpdate(now);

        if (customer.getId() == null) {
            customer = customerRepository.save(customer);
            System.out.println("Customer saved with ID: " + customer.getId());
        }

        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setStatus(StatusType.ordered);

        if (cart.getCreateDate() == null) {
            cart.setCreateDate(now);
        }
        cart.setLastUpdate(now);

        cart.setCustomer(customer);

        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                if (item.getCreateDate() == null) {
                    item.setCreateDate(now);
                }
                item.setLastUpdate(now);
                item.setCart(cart);
                cart.getCartItems().add(item);
            }
            System.out.println("Added " + cartItems.size() + " cart items");
        }

        cartRepository.save(cart);

        System.out.println("✓ Order completed successfully!");
        System.out.println("✓ Tracking number: " + orderTrackingNumber);
        System.out.println("✓ Customer: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("✓ Total: $" + cart.getPackagePrice());
        System.out.println("=================================\n");

        return new PurchaseResponse(orderTrackingNumber);
    }
}