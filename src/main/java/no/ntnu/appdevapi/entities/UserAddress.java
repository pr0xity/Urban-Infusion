package no.ntnu.appdevapi.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "address_id")
    private long id;
    @ApiModelProperty("The users main address")
    @Column(name = "address_line1")
    private String addressLine1;
    @ApiModelProperty("The users secondary address")
    @Column(name = "address_line2")
    private String addressLine2;
    @ApiModelProperty("The users city")
    private String city;
    @ApiModelProperty("The users postal code")
    @Column(name = "postal_code")
    private int postalCode;
    @ApiModelProperty("The users current country")
    private String country;
    @ApiModelProperty("The users telephone number")
    private String phone;

    @OneToOne(mappedBy = "address")
    private User user;

    public long getId() { return id; }

    public void setId(int id) { this.id = id; }

    @JsonIgnore
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getAddressLine1() { return addressLine1; }

    public void setAddressLine1(String address_line1) { this.addressLine1 = address_line1; }

    public String getAddressLine2() { return addressLine2; }

    public void setAddressLine2(String address_line2) { this.addressLine2 = address_line2; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public int getPostalCode() { return postalCode; }

    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getPhone() { return phone; }

    public void setPhone(String telephone) { this.phone = telephone; }

    /**
     * Gets the full address line.
     * @return {@code String} full address line.
     */
    public String getAddressLine() {
        return this.addressLine1 + (this.addressLine2 == null || this.addressLine2.isBlank() ? "" : ", " + this.addressLine2)
                + ", " + this.postalCode + " " + this.city + ", " + this.country;
    }
}