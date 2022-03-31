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
    private int user_id;
    @ApiModelProperty("The users main address")
    private String address_line1;
    @ApiModelProperty("The users secondary address")
    private String address_line2;
    @ApiModelProperty("The users city")
    private String city;
    @ApiModelProperty("The users postal code")
    private int postalCode;
    @ApiModelProperty("The users current country")
    private String country;
    @ApiModelProperty("The users telephone number")
    private String telephone;


    public UserAddress(int id, int user_id, String address_line1, String city, int postalCode, String country, String telephone) {
        this.id = id;
        this.user_id = user_id;
        this.address_line1 = address_line1;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.telephone = telephone;
    }

    public UserAddress(int id, int user_id, String address_line1, String address_line2, String city, int postalCode, String country, String telephone) {
        this.id = id;
        this.user_id = user_id;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.telephone = telephone;
    }

    public UserAddress() {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getAddress_line1() { return address_line1; }

    public void setAddress_line1(String address_line1) { this.address_line1 = address_line1; }

    public String getAddress_line2() { return address_line2; }

    public void setAddress_line2(String address_line2) { this.address_line2 = address_line2; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public int getPostalCode() { return postalCode; }

    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getTelephone() { return telephone; }

    public void setTelephone(String telephone) { this.telephone = telephone; }

}
