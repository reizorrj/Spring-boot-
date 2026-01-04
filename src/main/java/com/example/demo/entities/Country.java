package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;

    @Column(name = "country")
    private String countryName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private Set<Division> divisions = new HashSet<>();

    public Country() {}

    public Country(Long id, String countryName, Date createDate, Date lastUpdate, Set<Division> divisions) {
        this.id = id;
        this.countryName = countryName;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.divisions = divisions;
    }
    public Long getId() { return id; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    public Set<Division> getDivisions() { return divisions; }
    public void setDivisions(Set<Division> divisions) { this.divisions = divisions; }
}

