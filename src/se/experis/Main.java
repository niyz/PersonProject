package se.experis;

import java.sql.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        boolean keepRunning = true;
        //Create instance of database
        DataBase db = new DataBase();
        //Create phoneList and populate it
        /*ArrayList<String> phoneNumbers_p1 = new ArrayList<String>();
        phoneNumbers_p1.add("0700000");
        phoneNumbers_p1.add("0700001");
        ArrayList<String> phoneNumbers_p2 = new ArrayList<String>();
        phoneNumbers_p2.add("0700022");
        phoneNumbers_p2.add("0700023");
        ArrayList<String> emailList_p1 = new ArrayList<String>();
        ArrayList<String> emailList_p2 = new ArrayList<String>();
        emailList_p1.add("somemail@some.com");
        emailList_p2.add("anothermail@different.com");
        //Create address for one person
        Address address_p1 = new Address("Sweden", "Kalmar", "Blackagatan", "32", "32322");
        Address address_p2 = new Address("Denmark", "Whoknows", "Beerstreet", "91", "12333");

        //Create the person with its member variables including the address
        Person p1 = new Person("elliot", "123123", "bushkaka", phoneNumbers_p1,emailList_p1, address_p1);
        Person p2 = new Person("Jannik", "333333", "Densk", phoneNumbers_p2,emailList_p2, address_p2);

        //Create the personList
        ArrayList<Person> personList = new ArrayList<Person>();
        //add person
        personList.add(p1);
        db.insertPerson(p1);
        personList.add(p2);
        db.insertPerson(p2);*/
        Person returnAbleSearch;

        String input;
        while (keepRunning) {
            System.out.println("\nAdd or search for person?\nExit to stop");
            System.out.println("Enter the digit for the corresponding choice: \n[1]: Exit\n[2]: Add\n[3]: Search");


            Scanner in = new Scanner(System.in);
            input = in.nextLine().toLowerCase();
            switch (input) {
                case "2":
                    System.out.println("In create");
                    Person pNew = createPersonObject();
                    //TODO: Insert to database when Elliot is done with the function
                    //For now: Update personList
                    //personList.add(pNew);
                    db.insertPerson(pNew);
                    break;
                case "3":
                    System.out.println("In read.. Ongoing implementation");
                    returnAbleSearch = readPersonObjectFromDB(db);
                    break;
                case "update":
                    System.out.println("In update");
                    //Person p3 = updatePersonToDatabase();
                    break;
                case "delete":
                    System.out.println("In delete");
                    break;
                case "search":
                    System.out.print("Search for name: ");
                    input = in.nextLine();
                    ArrayList<Person> p = db.dbSearch(input);
                    for (Person per : p) {
                        per.personToString();
                        System.out.println("---- Phone ----");
                        for(String phone : per.getPhoneIDList()){
                            System.out.println(phone);
                        }
                        System.out.println("---- Email ----");
                        for(String email : per.getEmailList()){
                            System.out.println(email);
                        }
                        System.out.println("---- Address ----");
                        per.getAddress().printAddress();

                    }
                    break;
                case "exit":
                    keepRunning = false;
                    System.out.println("Exiting..");
                    break;
                default:
                    System.out.println("Default");
                    break;
            }
        }
    }

    private static Person createPersonObject(){
        Scanner in = new Scanner(System.in);
        String firstName, surName, personID, phone, email; //Person
        String country, city, street, streetNum, postalCode;
        ArrayList<String> phoneList = new ArrayList<String>();
        ArrayList<String> emailList = new ArrayList<String>();

        System.out.println("First name: ");
        firstName = in.nextLine().toLowerCase();
        System.out.println("Surname: ");
        surName = in.nextLine();
        System.out.println("Person ID: ");
        personID = in.nextLine();
        System.out.println("Phone: ");
        phone = in.nextLine();
        phoneList.add(phone);
        System.out.println("Mail: ");
        email = in.nextLine();
        emailList.add(email);
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
        Person p1 = new Person(firstName, personID, surName, phoneList, emailList, address);

        return p1;
    }
    private static Person readPersonObjectFromDB(DataBase db){
        Scanner in = new Scanner(System.in);
        ArrayList<Person> listOfFoundPersons = new ArrayList<Person>();
        String searchable, choosePerson, choice;
        int chosenPerson;
        Person returnable = null;
        boolean run = true;

        System.out.println("Enter the digit for the correspoding choice: \n[1]: Back\n[2]: Search");
        choice = in.nextLine();
        while(run){

            if (choice.equals("1"))
            {
                run = false;

            }else if (choice.equals("2")) {
                System.out.println("Enter search string: \n");
                searchable = in.nextLine();
                listOfFoundPersons = db.dbSearch(searchable);
                System.out.println(listOfFoundPersons.size());
                if (listOfFoundPersons.size() > 0){

                    for (int i = 0; i < listOfFoundPersons.size(); i++) {
                        System.out.println(i + " : ");
                        listOfFoundPersons.get(i).personToString();
                    }
                    System.out.println("Enter the preceding digit to choose person");
                    choosePerson = in.nextLine();
                    chosenPerson = Integer.parseInt(choosePerson);
                    returnable = listOfFoundPersons.get(chosenPerson);
                }else{
                    System.out.println("No hit..");
                }
                run = false;

            }
        }

        return returnable;

    }
    private static Person updatePersonToDatabase(){
        Scanner in = new Scanner(System.in);
        String searchable;
        System.out.println("Search query: ");
        searchable = in.nextLine();
        //TODO: Anrop till db update funktion
        //Db.update-någonting.
        Person p3 = null;

        return p3;

    }


}

