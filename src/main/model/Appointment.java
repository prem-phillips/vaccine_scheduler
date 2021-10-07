package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents an appointment with a date and a message
public class Appointment implements Writable {
    private int appointmentDay;
    private int appointmentMonth;
    private int appointmentYear;
    private String message;

    // EFFECTS: creates a new appointment with the given date and message
    // REQUIRES: appointmentDay, appointmentMonth, and appointmentYear must be valid (i.e. appointmentMonth <= 12)

    public Appointment(int appointmentDay, int appointmentMonth, int appointmentYear, String message) {
        this.appointmentDay = appointmentDay;
        this.appointmentMonth = appointmentMonth;
        this.appointmentYear = appointmentYear;
        this.message = message;
    }

    // EFFECTS: returns a string with the appointment date and message

    @Override
    public String toString() {
        String str = "     Appointment on " + this.appointmentDay + " / ";
        str += this.appointmentMonth + " / " + this.appointmentYear;
        str += "\n          Message: " + this.message + "\n";
        return str;
    }

    // EFFECTS: returns JSONObject with appointment data

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("appointmentDay", this.appointmentDay);
        json.put("appointmentMonth", this.appointmentMonth);
        json.put("appointmentYear", this.appointmentYear);
        json.put("message", this.message);

        return json;
    }

}
