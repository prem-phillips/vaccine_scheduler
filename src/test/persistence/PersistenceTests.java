package persistence;

import model.Person;
import model.PersonList;
import model.VaccineStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PersistenceTests {

    @Test
    void testReaderFail() {
        JsonReader reader1 = new JsonReader("./data/noSuchFile.json");
        try {
            VaccineStatus vs = reader1.getVaccineStatus();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }

        try {
            PersonList pl = reader1.getPersonList();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderSuccess() {
        JsonReader reader2 = new JsonReader("./data/test-data.json");
        try {
            VaccineStatus vs = reader2.getVaccineStatus();
            assertEquals(490, vs.getPfizerVaccineInventoryCount());
            assertEquals(10, vs.getPfizerVaccineAdministeredCount());
            assertEquals(580, vs.getModernaVaccineInventoryCount());
            assertEquals(20, vs.getModernaVaccineAdministeredCount());
            assertEquals(5000, vs.getMaxVaccineInventory());

            PersonList pl = reader2.getPersonList();
            Person p = pl.getPerson(0);
            assertEquals("Prem", p.getName());
            assertEquals("     Appointment on 10 / 3 / 2021\n" +
                    "          Message: first-Pfizer-vaccine\n" +
                    "     Appointment on 20 / 3 / 2021\n" +
                    "          Message: second-Pfizer-vaccine\n", p.getAppointmentInformation());

        } catch (IOException e) {
            fail("Couldn't read from test-data");
        }
    }

    @Test
    void testWriterFail() {
        JsonWriter writer1 = new JsonWriter("./data/my\0illegal:fileName.json");
        try {
            writer1.open();
            fail("Should not have opened illegal file");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriterSuccess() {
        JsonWriter writer2 = new JsonWriter("./data/test-data-writing.json");
        try {
            writer2.open();
        } catch (FileNotFoundException e) {
            fail("Couldn't write to test-data-writing");
        }
        VaccineStatus vs1 = new VaccineStatus(100, 10, 200, 20, 6000);
        PersonList pl1 = new PersonList();
        pl1.addPerson("Prem", 1, 2, 3);

        writer2.write(vs1, pl1);
        writer2.close();

        JsonReader jsonReader = new JsonReader("./data/test-data-writing.json");
        try {
            VaccineStatus vs2 = jsonReader.getVaccineStatus();
            assertEquals(100, vs2.getPfizerVaccineInventoryCount());
            assertEquals(10, vs2.getPfizerVaccineAdministeredCount());
            assertEquals(200, vs2.getModernaVaccineInventoryCount());
            assertEquals(20, vs2.getModernaVaccineAdministeredCount());
            assertEquals(6000, vs2.getMaxVaccineInventory());

            PersonList pl2 = jsonReader.getPersonList();
            Person p = pl2.getPerson(0);
            assertEquals("Prem", p.getName());
            assertEquals("     No appointments found for Prem!\n", p.getAppointmentInformation());

        } catch (IOException e) {
            fail("Couldn't read from project-data");
        }
    }

}
