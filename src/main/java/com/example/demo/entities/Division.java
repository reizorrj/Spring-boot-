package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "divisions")
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @Column(name = "division")
    private String divisionName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @JsonIgnore
    @OneToMany(mappedBy = "division")
    private Set<Customer> customers;

    public Division() {}

    public Division(Long id, String divisionName, Date createDate, Date lastUpdate,
                    Country country, Long countryId, Set<Customer> customers) {
        this.id = id;
        this.divisionName = divisionName;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.country = country;

        this.customers = customers;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDivisionName() { return divisionName; }
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public Set<Customer> getCustomers() { return customers; }
    public void setCustomers(Set<Customer> customers) { this.customers = customers; }
}
