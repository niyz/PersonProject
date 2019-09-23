package se.experis;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Boolean keepRunning =  true;

        String input;
        //System.out.println("Create, read, update or delete?");
        //input = in.nextLine().toLowerCase();
    while (keepRunning)
        System.out.println("Create, read, update or delete?\nExit to stop");

        Scanner in = new Scanner(System.in);
        input = in.nextLine().toLowerCase();
        switch(input)
        {
            case "create":
                System.out.println("In create");
                Person p1 = createPersonObject();
                p1.personToString();
                break;
            case "read":
                System.out.println("In read");
                break;
            case "update":
                System.out.println("In update");
            case "delete":
                System.out.println("In delete");
            case "exit":
                keepRunning = false;
                System.out.println("Exiting..");
            default:
                System.out.println("Default");
        }

    }

    private static Person createPersonObject(){
        Scanner in = new Scanner(System.in);
        String firstName, surName, personID, phone; //Person
        String country, city, street, streetNum, postalCode;
        ArrayList<String> phoneList = new ArrayList<String>();
        System.out.println("First name: ");
        firstName = in.nextLine();
        System.out.println("Surname: ");
        surName = in.nextLine();
        System.out.println("Person ID: ");
        personID = in.nextLine();
        System.out.println("Phone: ");
        phone = in.nextLine();
        phoneList.add(phone);
        System.out.println("Person in creation.\nAddress creation: ");
        System.out.println("Country: ");
        country = in.nextLine();
        System.out.println("City: ");
        city = in.nextLine();
        System.out.println("Street: ");
        street = in.nextLine();
        System.out.println("House number: ");
        streetNum = in.nextLine();
        System.out.println("Postal code: ");
        postalCode = in.nextLine();
        Address address = new Address(country, city, street, streetNum, postalCode);
        Person p1 = new Person(firstName, personID, surName, phoneList, address);

        return p1;
    }


}

