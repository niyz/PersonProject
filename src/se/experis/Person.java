package se.experis;

import java.util.ArrayList;

public class Person {
    private String name;
    private String personID;
    private String lastName;
    private ArrayList<String>  phoneIDList;
    Address address;

    //TODO: Add address and its constructor

    Person(String name, String personID, String lastName, ArrayList<String> phoneIDList, Address address){
        this.name = name;
        this.personID = personID;
        this.lastName = lastName;
        this.phoneIDList = new ArrayList<String>(phoneIDList);
        this.address = address;
    }

    public void personToString(){
        System.out.print("\nName: " + name + "\nPerson ID: " + personID + "\nSurname: " + lastName + "\nPhone number: " + phoneIDList.get(0).toString());
        for (int i = 1; i < phoneIDList.size(); i++) {
            System.out.print("\n\t\t\t  " + phoneIDList.get(i).toString());
        }
        address.printAddress();
    }

    public String getName(){

        return this.name;
    }
    public String getPersonID()
    {
        return this.personID;
    }
    public String getLastName(){

        return this.lastName;
    }
    public ArrayList<String> getPhoneIDList()
    {
        return this.phoneIDList;
    }

    public Address getAddress()
    {
        return this.address;
    }
}
