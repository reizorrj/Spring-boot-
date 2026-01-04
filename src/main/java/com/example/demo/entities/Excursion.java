package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "excursions")
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_id")
    private Long id;

    @JsonProperty("excursion_title")
    @Column(name = "excursion_title")
    private String excursionTitle;

    @JsonProperty("excursion_price")
    @Column(name = "excursion_price")
    private BigDecimal excursionPrice;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;

    @JsonIgnore
    @ManyToMany(mappedBy = "excursions")
    private Set<CartItem> cartItems = new HashSet<>();

    public Excursion() {}

    public Excursion(Long id, String excursionTitle, BigDecimal excursionPrice, String imageUrl, Date createDate, Date lastUpdate, Vacation vacation, Set<CartItem> cartItems) {
        this.id = id;
        this.excursionTitle = excursionTitle;
        this.excursionPrice = excursionPrice;
        this.imageUrl = imageUrl;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.vacation = vacation;
        this.cartItems = cartItems;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExcursionTitle() { return excursionTitle; }
    public void setExcursionTitle(String excursionTitle) { this.excursionTitle = excursionTitle; }

    public BigDecimal getExcursionPrice() { return excursionPrice; }
    public void setExcursionPrice(BigDecimal excursionPrice) { this.excursionPrice = excursionPrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    public Vacation getVacation() { return vacation; }
    public void setVacation(Vacation vacation) { this.vacation = vacation; }

    public Set<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(Set<CartItem> cartItems) { this.cartItems = cartItems; }
}