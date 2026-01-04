package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;

    @Column(name = "package_price")
    private BigDecimal packagePrice;

    @Column(name = "party_size")
    private Integer partySize;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    public Cart() {}

    public Cart(Long id, String orderTrackingNumber, BigDecimal packagePrice, Integer partySize,
                StatusType status, Date createDate, Date lastUpdate, Customer customer, Set<CartItem> cartItem) {
        this.id = id;
        this.orderTrackingNumber = orderTrackingNumber;
        this.packagePrice = packagePrice;
        this.partySize = partySize;
        this.status = status;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.customer = customer;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderTrackingNumber() { return orderTrackingNumber; }
    public void setOrderTrackingNumber(String orderTrackingNumber) { this.orderTrackingNumber = orderTrackingNumber; }

    public BigDecimal getPackagePrice() { return packagePrice; }
    public void setPackagePrice(BigDecimal packagePrice) { this.packagePrice = packagePrice; }

    public Integer getPartySize() { return partySize; }
    public void setPartySize(Integer partySize) { this.partySize = partySize; }

    public StatusType getStatus() { return status; }
    public void setStatus(StatusType status) { this.status = status; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Set<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(Set<CartItem> cartItems) { this.cartItems = cartItems; }
}






































