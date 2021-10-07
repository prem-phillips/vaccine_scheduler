package ui;


import model.Person;
import model.PersonList;
import model.VaccineStatus;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents the scheduler application
public class SchedulerApp {

    private Scanner input;
    private VaccineStatus vaccineStatusModel;
    private PersonList personListModel;
    private static final String JSON_PATH = "./data/project-data.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: starts application

    public SchedulerApp() {
        runScheduler();
    }

    // EFFECTS: initializes Scanner, VaccineStatus, and PersonList, JsonReader and JsonWriter objects

    public void init() {
        this.input = new Scanner(System.in);
        this.vaccineStatusModel = new VaccineStatus();
        this.vaccineStatusModel.setMaxVaccineInventory(10000);
        this.vaccineStatusModel.setMaxVaccineInventory(10000);
        this.personListModel = new PersonList();
        this.jsonReader = new JsonReader(JSON_PATH);
        this.jsonWriter = new JsonWriter(JSON_PATH);
    }

    // EFFECTS: runs application, processes user input
    // MODIFIES: this

    public void runScheduler() {
        init();
        boolean keepGoing = true;
        int command = 0;
        displayWelcomeMessage();

        while (keepGoing) {
            displayMenu();
            command = input.nextInt();

            if (command == 1) {
                displayInstructions();
            } else if (command >= 2 && command < 9) {
                this.newAction(command);
            } else if (command == 9) {
                this.saveData();
            } else if (command == 10) {
                this.loadData();
            } else if (command == 11) {
                System.out.println("\n||\t\t\tShutting down\t\t\t||\n");
                keepGoing = false;
            } else {
                System.out.println("Please enter valid number!\n");
            }
        }
    }

    // EFFECTS: displays welcome message

    public void displayWelcomeMessage() {
        System.out.println("\n||\t\t\tVaccine Scheduler Program\t\t\t||\n||\t\t\tCreated by Prem Phillips\t\t\t||\n");
    }

    // EFFECTS: displays selection menu

    public void displayMenu() {
        System.out.println("Select from:");
        System.out.println("1 -\t\tGet instructions");
        System.out.println("2 -\t\tBook a vaccination appointment");
        System.out.println("3 -\t\tUpdate an upcoming vaccination appointment");
        System.out.println("4 -\t\tView upcoming vaccination appointments");
        System.out.println("5 -\t\tView vaccine inventory");
        System.out.println("6 -\t\tAdd vaccines to inventory");
        System.out.println("7 -\t\tAdminister vaccines from inventory");
        System.out.println("8 -\t\tChange settings");
        System.out.println("9 -\t\tSave vaccine inventory and upcoming appointments");
        System.out.println("10-\t\tLoad vaccine inventory and upcoming appointments");
        System.out.println("11-\t\tShutdown");
    }

    // EFFECTS: displays instructions

    public void displayInstructions() {
        System.out.println("\n||\t\t\tInstructions\t\t\t||");
        System.out.println("There are many features to this program. You can add vaccines through option 6, \n"
                + "and adminster them through option 7. You can view the vaccine inventory through\n"
                + "option 5. Options 2,3,4 handle vaccination appointments. You create a user and\n"
                + "add an appointment through option 2.\n");
    }

    // EFFECTS: processes action
    // REQUIRES: command must be in [2,8]

    public void newAction(int command) {
        switch (command) { // max method length
            case 2:
                this.bookAppointment();
                break;
            case 3:
                this.updateAppointment();
                break;
            case 4:
                this.viewAppointmentInformation();
                break;
            case 5:
                this.displayInventory();
                break;
            case 6:
                this.addVaccines();
                break;
            case 7:
                this.administerVaccines();
                break;
            case 8:
                this.updateSettings();
                break;
        }
    }

    // EFFECTS: displays vaccine inventory

    public void displayInventory() {
        System.out.println("\n||\t\t\tVaccine Inventory\t\t\t||");
        System.out.println("Pfizer Vaccine Inventory: " + this.vaccineStatusModel.getPfizerVaccineInventoryCount());
        System.out.println("Moderna Vaccine Inventory: " + this.vaccineStatusModel.getModernaVaccineInventoryCount());
        System.out.println("Administered Pfizer Vaccines: "
                + this.vaccineStatusModel.getPfizerVaccineAdministeredCount());
        System.out.println("Administered Moderna Vaccines: "
                + this.vaccineStatusModel.getModernaVaccineAdministeredCount());
        System.out.println("Vaccine Inventory Limit: "
                + this.vaccineStatusModel.getTotalVaccineInventory() + " / "
                + this.vaccineStatusModel.getMaxVaccineInventory() + "\n");
    }

    // EFFECTS: processes addition of vaccines
    // MODIFIES: this

    public void addVaccines() {
        System.out.println("\n||\t\t\tAdd Vaccines\t\t\t||");
        System.out.println("Please enter the number of vaccines you wish to add");

        int newVaccineCount = input.nextInt();

        System.out.println("Select from:");
        System.out.println("1 -\t\tAdd to Pfizer Vaccines");
        System.out.println("2 -\t\tAdd to Moderna Vaccines");

        int command = input.nextInt();

        if (command == 1) {
            if (this.vaccineStatusModel.addPfizerVaccineInventory(newVaccineCount)) {
                System.out.println("Vaccines added!\n");
            } else {
                System.out.println("Vaccines could not be added, as vaccine inventory limit reached!\n");
            }
        } else if (command == 2) {
            if (this.vaccineStatusModel.addModernaVaccineInventory(newVaccineCount)) {
                System.out.println("Vaccines added!\n");
            } else {
                System.out.println("Vaccines could not be added, as vaccine inventory limit reached!\n");
            }
        } else {
            System.out.println("Unknown number\n");
        }
    }

    // EFFECTS: processes administration of vaccines
    // MODIFIES: this

    public void administerVaccines() {
        System.out.println("\n||\t\t\tAdminister Vaccines\t\t\t||");
        System.out.println("Please enter the number of vaccines you wish to administer");

        int administeredVaccineCount = input.nextInt();

        System.out.println("Select from:");
        System.out.println("1 -\t\tAdminister Pfizer Vaccines");
        System.out.println("2 -\t\tAdminister Moderna Vaccines");

        int command = input.nextInt();

        if (command == 1) {
            if (this.vaccineStatusModel.administerPfizerVaccine(administeredVaccineCount)) {
                System.out.println("Vaccines administered!\n");
            } else {
                System.out.println("Vaccines could not be administered, as there were not enough vaccines!\n");
            }
        } else if (command == 2) {
            if (this.vaccineStatusModel.administerModernaVaccine(administeredVaccineCount)) {
                System.out.println("Vaccines administered!\n");
            } else {
                System.out.println("Vaccines could not be administered, as there were not enough vaccines!\n");
            }
        } else {
            System.out.println("Unknown number\n");
        }
    }

    // EFFECTS: processes user updating settings
    // MODIFIES: this

    public void updateSettings() {
        System.out.println("\n||\t\t\tUpdate Settings\t\t\t||");

        System.out.println("Select from:");
        System.out.println("1 -\t\tChange vaccine inventory limit");

        int command = input.nextInt();

        if (command == 1) {
            System.out.println("Enter new vaccine inventory limit:");
            int newVaccineLimit = input.nextInt();
            this.vaccineStatusModel.setMaxVaccineInventory(newVaccineLimit);
            System.out.println("Vaccine inventory limit updated!\n");
        } else {
            System.out.println("Unknown number\n");
        }
    }

    // EFFECTS: processes appointment booking. gives option to create profile or to use existing profile
    // MODIFIES: this

    public void bookAppointment() {
        System.out.println("\n||\t\t\tBook Appointment\t\t\t||\nSelect from:\n1 -\t\tCreate new profile\n"
                + "2 -\t\tSearch for existing profile");
        int command = input.nextInt();
        int index = -1;
        System.out.println("Enter person's name:");
        String name = input.next();
        if (command == 1) {
            System.out.println("Enter person's day (ie 1,2,3) of birth:");
            int day = input.nextInt();
            System.out.println("Enter person's month (ie 1,2,3) of birth:");
            int month = input.nextInt();
            System.out.println("Enter person's year (ie 2000) of birth:");
            int year = input.nextInt();
            index = this.personListModel.addPerson(name, day, month, year);
        } else if (command == 2) {
            index = this.personListModel.getPersonIndex(name);
        }
        if (index == -1) {
            System.out.println("Error making appointment!\n");
        } else {
            System.out.println("Booking appointment for: " + this.personListModel.getPerson(index).getName());
            makeNewAppointment(index);
        }
    }

    // EFFECTS: processes appointment making
    // REQUIRES: indexOfPerson must be in range in personListModel
    // MODIFIES: this

    public void makeNewAppointment(int indexOfPerson) {
        Person p = this.personListModel.getPerson(indexOfPerson);

        System.out.println("Enter appointment day (ie 1,2,3)");
        int day = input.nextInt();
        System.out.println("Enter appointment month (ie 1,2,3)");
        int month = input.nextInt();
        System.out.println("Enter appointment year (ie 2021)");
        int year = input.nextInt();
        System.out.println("Enter reason for appointment:");
        String message = input.next();

        p.addAppointment(day, month, year, message);
        System.out.println("Appointment made!\n");
    }

    // EFFECTS: processes appointment updating
    // MODIFIES: this

    public void updateAppointment() {
        System.out.println("\n||\t\t\tModify Appointment\t\t\t||");
        System.out.println("Enter person's name:");
        String name = input.next();
        int indexOfPerson = this.personListModel.getPersonIndex(name);
        if (indexOfPerson == -1) {
            System.out.println("Person not found!\n");
            return;
        }
        System.out.println("Select from:");
        System.out.println("1 -\t\tModify appointment");
        System.out.println("2 -\t\tCancel appointment");
        int command = input.nextInt();
        if (command == 1) {
            this.modifyAppointment(indexOfPerson);
        } else if (command == 2) {
            this.removeAppointment(indexOfPerson);
        } else {
            System.out.println("Unknown number!\n");
        }
    }

    // EFFECTS: processes appointment modifying
    // REQUIRES: indexOfPerson must be in range of personListModel
    // MODIFIES: this

    public void modifyAppointment(int indexOfPerson) {
        Person p = this.personListModel.getPerson(indexOfPerson);
        System.out.println("Modifying appointment for: " + p.getName());

        System.out.println("Select appointment:");
        System.out.println(p.getAppointmentInformationWithSelectionNumbers());
        int indexOfAppointment = input.nextInt() - 1;
        if (p.removeAppointment(indexOfAppointment)) {
            System.out.println("Modifying appointment. Please enter the new information");
            this.makeNewAppointment(indexOfPerson);
        } else {
            System.out.println("Unknown number!\n");
        }
    }

    // EFFECTS: processes removal of appointment
    // REQUIRES: indexOfPerson must be in range of personListModel
    // MODIFIES: this

    public void removeAppointment(int indexOfPerson) {
        Person p = this.personListModel.getPerson(indexOfPerson);
        System.out.println("Removing appointment for: " + p.getName());

        System.out.println("Select appointment:");
        System.out.println(p.getAppointmentInformationWithSelectionNumbers());
        int indexOfAppointment = input.nextInt() - 1;
        if (p.removeAppointment(indexOfAppointment)) {
            System.out.println("Appointment removed!\n");
        } else {
            System.out.println("Unknown number!\n");
        }
    }

    // EFFECTS: processes viewing of appointment information

    public void viewAppointmentInformation() {
        System.out.println("\n||\t\t\tView Appointment\t\t\t||");
        System.out.println("Enter person's name:");
        String name = input.next();
        int indexOfPerson = this.personListModel.getPersonIndex(name);
        if (indexOfPerson == -1) {
            System.out.println("Person not found!\n");
            return;
        }

        Person p = this.personListModel.getPerson(indexOfPerson);
        System.out.println("Viewing appointments for: " + p.getName());

        System.out.println(p.getAppointmentInformation());
    }

    // EFFECTS: saves VaccineStatus and PersonList to JSON_PATH
    public void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.vaccineStatusModel, this.personListModel);
            jsonWriter.close();
            System.out.println("Saved data to " + JSON_PATH + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_PATH + "\n");
        }
    }

    // EFFECTS: loads VaccineStatus and PersonList from JSON_PATH
    // MODIFIES: this
    public void loadData() {
        try {
            this.vaccineStatusModel = jsonReader.getVaccineStatus();
            this.personListModel = jsonReader.getPersonList();
            System.out.println("Loaded data from " + JSON_PATH + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read data from " + JSON_PATH + "\n");
        }
    }

}



