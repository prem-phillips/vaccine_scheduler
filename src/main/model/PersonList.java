package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of people
public class PersonList implements Writable {
    private ArrayList<Person> personList;

    // EFFECTS: makes list of person

    public PersonList() {
        this.personList = new ArrayList<Person>();
    }

    // EFFECTS: returns person at given index
    // REQUIRES: index must be in range

    public Person getPerson(int index) {
        return this.personList.get(index);
    }

    // EFFECTS: returns index with person with same name. If no person is found, return -1.

    public int getPersonIndex(String name) {
        for (int i = 0; i < this.personList.size(); i++) {
            if (this.personList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    // EFFECTS: creates person and adds to list
    // REQUIRES: birthDay, birthMonth, and birthYear must be valid (i.e. appointmentMonth <= 12)
    // MODIFIES: this

    public int addPerson(String name, int birthDay, int birthMonth, int birthYear) {
        if (this.getPersonIndex(name) == -1) {
            this.personList.add(new Person(name, birthDay, birthMonth, birthYear));
            return this.personList.size() - 1; //return index of new person
        } else {
            return -1;
        }
    }

    // EFFECTS: returns JSONArray with personList data

    public JSONArray toJsonArray() {
        JSONArray jsonArray = new JSONArray();

        for (Person p : this.personList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns JSONObject with personList data
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        return json.put("personList", this.toJsonArray());
    }

    public ArrayList<Person> toArrayList() {
        return this.personList;
    }

    public String toString() {
        String str = "";
        for (Person p : this.personList) {
            str += p.toString() + "\n";
        }

        return str;
    }
}
