package se.experis;

import java.util.ArrayList;

public class Person {
    private String name;
    private String personID;
    private String lastName;
    private ArrayList<String>  phoneIDList;
    //TODO: Add address and its constructor

    Person(String name, String personID, String lastName, ArrayList<String> phoneIDList){
        this.name = name;
        this.personID = personID;
        this.lastName = lastName;
        this.phoneIDList = new ArrayList<String>(phoneIDList);
    }

    public void personToString(){
        System.out.print("\nName: " + name + "\nPerson ID: " + personID + "\nSurname: " + lastName + "\nPhone number: " + phoneIDList.get(0).toString());
        for (int i = 1; i < phoneIDList.size(); i++) {
            System.out.print("\n\t\t\t  " + phoneIDList.get(i).toString());

        }
        //TODO: System.out.println("Address: ");
    }
}
