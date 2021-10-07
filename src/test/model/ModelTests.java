package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModelTests {

    private Appointment testAppointment;
    private Person testPerson;
    private PersonList testPersonList;
    private VaccineStatus testVaccineStatus;

    @BeforeEach
    void runBefore() {
        this.testAppointment = new Appointment(14, 2, 2021,
                "First Pfizer vaccination");
        this.testPerson = new Person("John Smith", 1, 10, 2000);
        this.testPersonList = new PersonList();
        this.testVaccineStatus = new VaccineStatus();
    }

    @Test
    void testAppointment() {
        assertEquals("     Appointment on 14 / 2 / 2021\n" +
                "          Message: First Pfizer vaccination\n", this.testAppointment.toString());

        assertEquals("     Appointment on 14 / 2 / 2021\n" +
                "          Message: First Pfizer vaccination\n", this.testAppointment.toString());

        // toJson testing:
        JSONObject jsonTestObject = new JSONObject();
        jsonTestObject.put("appointmentDay", 14);
        jsonTestObject.put("appointmentMonth", 2);
        jsonTestObject.put("appointmentYear", 2021);
        jsonTestObject.put("message", "First Pfizer vaccination");

        assertEquals(jsonTestObject.toString(), this.testAppointment.toJson().toString());
    }

    @Test
    void testPerson() {
        assertEquals("John Smith", this.testPerson.getName());

        this.testPerson.addAppointment(14, 2, 2021,
                "First Pfizer vaccination");
        assertEquals("     Appointment on 14 / 2 / 2021\n" +
                "          Message: First Pfizer vaccination\n", this.testPerson.getAppointmentInformation());
        assertEquals("1 -\t\t     Appointment on 14 / 2 / 2021\n" +
                "          Message: First Pfizer vaccination\n", this.testPerson.getAppointmentInformationWithSelectionNumbers());

        assertTrue(this.testPerson.removeAppointment(0));
        assertEquals("     No appointments found for John Smith!\n", this.testPerson.getAppointmentInformation());
        assertEquals("No appointments found for John Smith!\nPlease type a number to return to main menu.",
                this.testPerson.getAppointmentInformationWithSelectionNumbers());

        assertFalse(this.testPerson.removeAppointment(0));

        assertEquals("John Smith  1 / 10 / 2000\n" +
                "     No appointments found for John Smith!\n", this.testPerson.toString());

        // toJson testing:
        this.testPerson.addAppointment(2, 3, 4,
                "appointment-message-1");

        JSONObject jsonAppointmentObject = new JSONObject();
        jsonAppointmentObject.put("appointmentDay", 2);
        jsonAppointmentObject.put("appointmentMonth", 3);
        jsonAppointmentObject.put("appointmentYear", 4);
        jsonAppointmentObject.put("message", "appointment-message-1");

        JSONObject jsonTestObject = new JSONObject();
        jsonTestObject.put("birthDay", 1);
        jsonTestObject.put("birthMonth", 10);
        jsonTestObject.put("birthYear", 2000);
        jsonTestObject.put("name", "John Smith");
        jsonTestObject.put("appointments", new JSONArray().put(jsonAppointmentObject));

        assertEquals(jsonTestObject.toString(), this.testPerson.toJson().toString());
    }

    @Test
    void testPersonList() {
        assertEquals(0, this.testPersonList.addPerson("John Smith",
                1, 10, 2000));
        assertEquals(-1, this.testPersonList.addPerson("John Smith",
                1, 10, 2000)); // -1 because john already existed

        assertEquals("John Smith", this.testPersonList.getPerson(0).getName());

        assertEquals(1, this.testPersonList.toArrayList().size());

        assertEquals(0, this.testPersonList.getPersonIndex("John Smith"));

        assertEquals(-1, this.testPersonList.getPersonIndex("abcd"));
        assertEquals(-1, this.testPersonList.getPersonIndex("abcd")); // DNE

        assertEquals("John Smith  1 / 10 / 2000\n" +
                "     No appointments found for John Smith!\n" +
                "\n", this.testPersonList.toString());


        // toJson testing:
        JSONObject jsonTestObject = new JSONObject();
        jsonTestObject.put("birthDay", 1);
        jsonTestObject.put("birthMonth", 10);
        jsonTestObject.put("birthYear", 2000);
        jsonTestObject.put("appointments", new JSONArray());
        jsonTestObject.put("name", "John Smith");

        JSONArray jsonArrayTestObject = new JSONArray();
        jsonArrayTestObject.put(jsonTestObject);

        JSONObject jsonPersonListTestObject = new JSONObject();
        jsonPersonListTestObject.put("personList", jsonArrayTestObject);

        assertEquals(jsonPersonListTestObject.toString(), this.testPersonList.toJson().toString());
    }


    @Test
    void testVaccineStatus() {
        this.testVaccineStatus.setMaxVaccineInventory(2000);
        assertEquals(0, this.testVaccineStatus.getTotalVaccineInventory());
        assertTrue(this.testVaccineStatus.addPfizerVaccineInventory(100));
        assertTrue(this.testVaccineStatus.addModernaVaccineInventory(200));
        assertFalse(this.testVaccineStatus.addPfizerVaccineInventory(2000));
        assertFalse(this.testVaccineStatus.addModernaVaccineInventory(2000));
        assertTrue(this.testVaccineStatus.addPfizerVaccineInventory(2000 - 100 - 200));
        assertEquals(2000, this.testVaccineStatus.getTotalVaccineInventory());

        assertTrue(this.testVaccineStatus.administerPfizerVaccine(10));
        assertEquals(1790, this.testVaccineStatus.getPfizerVaccineInventoryCount());
        assertEquals(10, this.testVaccineStatus.getPfizerVaccineAdministeredCount());
        assertEquals(2000 - 10, this.testVaccineStatus.getTotalVaccineInventory());

        assertTrue(this.testVaccineStatus.administerModernaVaccine(20));
        assertEquals(180, this.testVaccineStatus.getModernaVaccineInventoryCount());
        assertEquals(20, this.testVaccineStatus.getModernaVaccineAdministeredCount());
        assertEquals(2000 - 10 - 20, this.testVaccineStatus.getTotalVaccineInventory());

        assertFalse(this.testVaccineStatus.administerPfizerVaccine(5000));
        assertFalse(this.testVaccineStatus.administerModernaVaccine(5000));

        String str = "Vaccine Inventory:\n" +
                "\n" +
                "Pfizer Vaccine Inventory: 1790\n" +
                "Moderna Vaccine Inventory: 180\n" +
                "Administered Pfizer Vaccines: 10\n" +
                "Administered Moderna Vaccines: 20\n" +
                "Vaccine Inventory Limit: 1970 / 2000";

        assertEquals(str, this.testVaccineStatus.toString());

        // toJson testing:
        JSONObject jsonTestObject = new JSONObject();
        jsonTestObject.put("pfizerVaccineInventoryCount", 1790);
        jsonTestObject.put("pfizerVaccineAdministeredCount", 10);
        jsonTestObject.put("modernaVaccineInventoryCount", 180);
        jsonTestObject.put("modernaVaccineAdministeredCount", 20);
        jsonTestObject.put("maxVaccineInventory", 2000);

        assertEquals(jsonTestObject.toString(), this.testVaccineStatus.toJson().toString());
    }

}