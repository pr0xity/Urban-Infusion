package no.ntnu.appdevapi.entities;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ApiModelProperty("The id of the user")
    @Column(name = "fk_user_id")
    private int userId;
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
    private String telephone;


    public UserAddress(int id, int userId, String addressLine1, String city, int postalCode, String country, String telephone) {
        this.id = id;
        this.userId = userId;
        this.addressLine1 = addressLine1;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.telephone = telephone;
    }

    public UserAddress(int id, int userId, String addressLine1, String addressLine2, String city, int postalCode, String country, String telephone) {
        this.id = id;
        this.userId = userId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.telephone = telephone;
    }

    public UserAddress() {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }

    public void setUserId(int user_id) { this.userId = user_id; }

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

    public String getTelephone() { return telephone; }

    public void setTelephone(String telephone) { this.telephone = telephone; }

}
