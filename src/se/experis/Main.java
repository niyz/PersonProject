package se.experis;

import java.sql.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        boolean keepRunning = true;
        //Create instance of database
        DataBase db = new DataBase();
        Person returnAbleSearch;

        String input;
        while (keepRunning) {
            System.out.println("Enter the digit for the corresponding choice: \n[1]: Exit\n[2]: Add\n[3]: Search");


            Scanner in = new Scanner(System.in);
            input = in.nextLine().toLowerCase();
            switch (input) {
                case "2":
                    System.out.println("In create");
                    Person pNew = createPersonObject();
                    //For now: Update personList
                    //personList.add(pNew);
                    db.insertPerson(pNew);
                    break;
                case "3":
                    System.out.println("In read.. Ongoing implementation");
                    returnAbleSearch = readPersonObjectFromDB(db);
                    if (returnAbleSearch != null)
                    {
                        System.out.println("Something returned");
                        System.out.println("Enter the digit for the corresponding choice: \n[1]: Back\n[2]: Update\n[3]: Delete\n[4]: Add relation");
                        String input2 = in.nextLine();
                        if (input2.equals("1"))
                        {
                            System.out.println("1");
                            System.out.println("BACK... should work");
                        }else if (input2.equals("2")){
                            System.out.println("2");
                            System.out.println("Update...half implemented");
                            updatePersonToDatabase(db, returnAbleSearch);


                        }else if (input2.equals("3")){
                            System.out.println("3");
                            System.out.println("Delete... not implemented");


                        }else if (input2.equals("4")){
                            System.out.println("4");
                            System.out.println("Add Relation.. not implemented");
                        }

                    }else{
                        System.out.println("nothing returned");
                    }
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
                        //System.out.println("---- Phone ----");
                        for(String phone : per.getPhoneIDList()){
                            //System.out.println(phone);
                        }
                        System.out.println("---- Email ----");
                        for(String email : per.getEmailList()){
                            System.out.println(email);
                        }
                        //System.out.println("---- Address ----");
                        //per.getAddress().printAddress();
                        System.out.println("---- next ---");

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
                if (listOfFoundPersons.size() > 0){

                    for (int i = 0; i < listOfFoundPersons.size(); i++) {
                        System.out.print("["+ i + "]: ");
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
    private static void updatePersonToDatabase(DataBase db, Person personOrg){

        Person personNew = new Person(personOrg.getName(), personOrg.getPersonID(),
                "johansson", personOrg.getPhoneIDList(),
                personOrg.getEmailList(), personOrg.getAddress());

        //Temporary fix
        System.out.println("New: " + personNew.getLastName() +"\nOld: " + personOrg.getLastName());
        databaseFunctions.updatePerson(personOrg, personNew);

        //TODO: db.updatePerson(personOrg, personNew);
    }
}

