package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "customer_first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "customer_last_name")
    private String lastName;

    @NotBlank(message = "Address is required")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @NotBlank(message = "Phone number is required")
    @Column(name = "phone")
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts = new HashSet<>();

    public Customer() {}

    public Customer(String firstName, String lastName, String address, String postalCode, String phone,
                    Date createDate, Date lastUpdate, Division division) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.division = division;
    }


    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getPostalCode() { return postalCode; }
    public String getPhone() { return phone; }
    public Date getCreateDate() { return createDate; }
    public Date getLastUpdate() { return lastUpdate; }
    public Division getDivision() { return division; }
    public Set<Cart> getCarts() { return carts; }


    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAddress(String address) { this.address = address; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setDivision(Division division) { this.division = division; }
    public void setCarts(Set<Cart> carts) { this.carts = carts; }
}