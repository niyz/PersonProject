package se.experis;

public class Address {
    private String country;
    private String city;
    private String street;
    private String streetNum;
    private String postalCode;

    Address (String country, String city, String street, String streetNum, String postalCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.streetNum = streetNum;
        this.postalCode = postalCode;
    }

    public void printAddress() {
        System.out.println("Country:" + country + "City:" + city + "Street:" + street + "Street number:" +  streetNum + "Postal code:" + postalCode);
    }
}
