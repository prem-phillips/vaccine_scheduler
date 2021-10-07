package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


import java.util.ArrayList;

// Represents Person with a name, birthdate, and appointments
public class Person implements Writable {

    private String name;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private ArrayList<Appointment> appointments;

    // EFFECTS: creates person with name, birthDay, birthMonth, and birthYear
    // REQUIRES: birthDay, birthMonth, and birthYear must be valid (i.e. appointmentMonth <= 12)

    public Person(String name, int birthDay, int birthMonth, int birthYear) {
        this.name = name;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.appointments = new ArrayList<Appointment>();
    }

    // EFFECTS: return the name of the person

    public String getName() {
        return this.name;
    }

    // EFFECTS: creates a new appointment for the person with the given date and message
    // REQUIRES: appointmentDay, appointmentMonth, and appointmentYear must be valid (i.e. appointmentMonth <= 12)
    // MODIFIES: this

    public void addAppointment(int appointmentDay, int appointmentMonth, int appointmentYear, String message) {
        this.appointments.add(new Appointment(appointmentDay, appointmentMonth, appointmentYear, message));
    }

    // EFFECTS: returns all appointment information for person

    public String getAppointmentInformation() {
        String info = "";
        for (int i = 0; i < this.appointments.size(); i++) {
            info += this.appointments.get(i).toString();
        }
        if (info.isEmpty()) {
            return "     No appointments found for " + this.name + "!\n";
        } else {
            return info;
        }
    }

    // EFFECTS: returns all appointment information for person with a number

    public String getAppointmentInformationWithSelectionNumbers() {
        String info = "";
        for (int i = 0; i < this.appointments.size(); i++) {
            info += (i + 1) + " -\t\t" + this.appointments.get(i).toString();
        }
        if (info.isEmpty()) {
            return "No appointments found for " + this.name + "!\nPlease type a number to return to main menu.";
        } else {
            return info;
        }
    }

    // EFFECTS: removes appointment at given index and return true. If index is outside of range, return false
    // MODIFIES: this

    public boolean removeAppointment(int index) {
        if (this.appointments.size() <= index) {
            return false;
        } else {
            this.appointments.remove(index);
            return true;
        }
    }

    // EFFECTS: returns JSONObject with person data

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("birthDay", birthDay);
        json.put("birthMonth", birthMonth);
        json.put("birthYear", birthYear);

        JSONArray jsonArray = new JSONArray();
        for (Appointment a: this.appointments) {
            jsonArray.put(a.toJson());
        }
        json.put("appointments", jsonArray);

        return json;
    }

    // EFFECTS: returns a String with person data

    @Override
    public String toString() {
        String str = this.name + "  " + this.birthDay + " / " + this.birthMonth + " / " + this.birthYear + "\n";
        str += this.getAppointmentInformation();
        return str;
    }

}
