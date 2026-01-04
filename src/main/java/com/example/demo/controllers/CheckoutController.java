package com.example.demo.controllers;

import com.example.demo.services.CheckoutService;
import com.example.demo.services.Purchase;
import com.example.demo.services.PurchaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "http://localhost:4200")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private static final Logger logger = Logger.getLogger(CheckoutController.class.getName());

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody Purchase purchase) {
        try {
            // Log the incoming purchase data
            logger.info("Received purchase request");
            logger.info("Cart package price: " + (purchase.getCart() != null ? purchase.getCart().getPackagePrice() : "null"));
            logger.info("Cart party size: " + (purchase.getCart() != null ? purchase.getCart().getPartySize() : "null"));
            logger.info("Customer: " + (purchase.getCustomer() != null ? purchase.getCustomer().getFirstName() : "null"));

            PurchaseResponse response = checkoutService.placeOrder(purchase);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.severe("Validation error: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            logger.severe("Error processing order: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "An error occurred while processing your order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            logger.warning("Validation error on field '" + fieldName + "': " + errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}