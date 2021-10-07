package persistence;

import model.Person;
import model.PersonList;
import model.VaccineStatus;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads VaccineStatus and PersonList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads VaccineStatus from file and returns it;
    // throws IOException if an error occurs reading data from file
    public VaccineStatus getVaccineStatus() throws IOException {
        String jsonData = this.readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseVaccineStatus(jsonObject);
    }


    // EFFECTS: reads PersonList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PersonList getPersonList() throws IOException {
        String jsonData = this.readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePersonList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses VaccineStatus from JSON object and returns it
    private VaccineStatus parseVaccineStatus(JSONObject jsonObject) {
        int pfizerVaccineInventoryCount = jsonObject.getInt("pfizerVaccineInventoryCount");
        int pfizerVaccineAdministeredCount = jsonObject.getInt("pfizerVaccineAdministeredCount");
        int modernaVaccineInventoryCount = jsonObject.getInt("modernaVaccineInventoryCount");
        int modernaVaccineAdministeredCount = jsonObject.getInt("modernaVaccineAdministeredCount");
        int maxVaccineInventory = jsonObject.getInt("maxVaccineInventory");

        VaccineStatus vs = new VaccineStatus(pfizerVaccineInventoryCount, pfizerVaccineAdministeredCount,
                modernaVaccineInventoryCount, modernaVaccineAdministeredCount, maxVaccineInventory);
        return vs;
    }

    // EFFECTS: parses personList from JSON object

    private PersonList parsePersonList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("personList");
        PersonList pl = new PersonList();
        for (Object json : jsonArray) {
            JSONObject jsonPerson = (JSONObject) json;
            this.addPerson(pl, jsonPerson);
        }
        return pl;
    }

    // EFFECTS: parses person from JSON object and adds it to personList
    // MODIFIES: pl

    private void addPerson(PersonList pl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int birthDay = jsonObject.getInt("birthDay");
        int birthMonth = jsonObject.getInt("birthMonth");
        int birthYear = jsonObject.getInt("birthYear");

        int index = pl.addPerson(name, birthDay, birthMonth, birthYear);

        this.addAppointments(pl.getPerson(index), jsonObject.getJSONArray("appointments"));
    }

    // EFFECTS: parses appointments from JSON object and adds it to person
    //MODIFIES: p

    private void addAppointments(Person p, JSONArray jsonArray) {
        int appointmentDay;
        int appointmentMonth;
        int appointmentYear;
        String message;

        for (Object json : jsonArray) {
            JSONObject nextAppointment = (JSONObject) json;
            appointmentDay = nextAppointment.getInt("appointmentDay");
            appointmentMonth = nextAppointment.getInt("appointmentMonth");
            appointmentYear = nextAppointment.getInt("appointmentYear");
            message = nextAppointment.getString("message");
            p.addAppointment(appointmentDay, appointmentMonth, appointmentYear, message);
        }
    }
}
