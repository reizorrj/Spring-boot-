package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacations")
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long id;

    @JsonProperty("vacation_title")
    @Column(name = "vacation_title")
    private String vacationTitle;

    @Column(name = "description")
    private String description;

    @JsonProperty("travel_price")
    @Column(name = "travel_fare_price")
    private BigDecimal travelPrice;

    @JsonProperty("image_URL")
    @Column(name = "image_url")
    private String imageUrl;

    @JsonProperty("create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @JsonProperty("last_update")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL)
    private Set<Excursion> excursions = new HashSet<>();

    public Vacation() {}

    public Vacation(Long id, String vacationTitle, String description, BigDecimal travelPrice, String imageUrl, Date createDate, Date lastUpdate, Set<Excursion> excursions) {
        this.id = id;
        this.vacationTitle = vacationTitle;
        this.description = description;
        this.travelPrice = travelPrice;
        this.imageUrl = imageUrl;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.excursions = excursions;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVacationTitle() { return vacationTitle; }
    public void setVacationTitle(String vacationTitle) { this.vacationTitle = vacationTitle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getTravelPrice() { return travelPrice; }
    public void setTravelPrice(BigDecimal travelPrice) { this.travelPrice = travelPrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    public Set<Excursion> getExcursions() { return excursions; }
    public void setExcursions(Set<Excursion> excursions) { this.excursions = excursions; }
}