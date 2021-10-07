package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents the status of the vaccine inventory
public class VaccineStatus implements Writable {
    private int pfizerVaccineInventoryCount;
    private int pfizerVaccineAdministeredCount;
    private int modernaVaccineInventoryCount;
    private int modernaVaccineAdministeredCount;

    private int maxVaccineInventory;

    // EFFECTS: initializes all fields to 0

    public VaccineStatus() {
        this.pfizerVaccineInventoryCount = 0;
        this.pfizerVaccineAdministeredCount = 0;
        this.modernaVaccineInventoryCount = 0;
        this.modernaVaccineAdministeredCount = 0;
        this.maxVaccineInventory = 0;
    }

    // EFFECTS: initializes all fields to given values respectively

    public VaccineStatus(int pfizerVaccineInventoryCount, int pfizerVaccineAdministeredCount,
                         int modernaVaccineInventoryCount, int modernaVaccineAdministeredCount,
                         int maxVaccineInventory) {
        this.pfizerVaccineInventoryCount = pfizerVaccineInventoryCount;
        this.pfizerVaccineAdministeredCount = pfizerVaccineAdministeredCount;
        this.modernaVaccineInventoryCount = modernaVaccineInventoryCount;
        this.modernaVaccineAdministeredCount = modernaVaccineAdministeredCount;
        this.maxVaccineInventory = maxVaccineInventory;
    }

    // EFFECTS: returns pfizer vaccine inventory count

    public int getPfizerVaccineInventoryCount() {
        return this.pfizerVaccineInventoryCount;
    }

    // EFFECTS: returns pfizer vaccine administered count

    public int getPfizerVaccineAdministeredCount() {
        return this.pfizerVaccineAdministeredCount;
    }

    // EFFECTS: returns moderna vaccine inventory count

    public int getModernaVaccineInventoryCount() {
        return this.modernaVaccineInventoryCount;
    }

    // EFFECTS: returns moderna vaccine administered count

    public int getModernaVaccineAdministeredCount() {
        return this.modernaVaccineAdministeredCount;
    }

    // EFFECTS: returns maximum amount of vaccines that can be stored in inventory

    public int getMaxVaccineInventory() {
        return this.maxVaccineInventory;
    }

    // EFFECTS: sets the maximum amount of vaccines that can be stored in inventory
    // MODIFIES: this
    // REQUIRES: maxVaccineInventory >= 0

    public void setMaxVaccineInventory(int maxVaccineInventory) {
        this.maxVaccineInventory = maxVaccineInventory;
    }

    // EFFECTS: returns all vaccines currently in inventory

    public int getTotalVaccineInventory() {
        return this.getPfizerVaccineInventoryCount() + this.getModernaVaccineInventoryCount();
    }

    // EFFECTS: adds Pfizer vaccines to inventory and returns true if there is space, return false otherwise
    // MODIFIES: this
    // REQUIRES: newVaccineCount >= 0

    public boolean addPfizerVaccineInventory(int newVaccineCount) {
        if (this.getTotalVaccineInventory() + newVaccineCount > this.getMaxVaccineInventory()) {
            return false;
        } else {
            this.pfizerVaccineInventoryCount += newVaccineCount;
            return true;
        }
    }

    // EFFECTS: adds Moderna vaccines to inventory and returns true if there is space, return false otherwise
    // MODIFIES: this
    // REQUIRES: newVaccineCount >= 0

    public boolean addModernaVaccineInventory(int newVaccineCount) {
        if (this.getTotalVaccineInventory() + newVaccineCount > this.getMaxVaccineInventory()) {
            return false;
        } else {
            this.modernaVaccineInventoryCount += newVaccineCount;
            return true;
        }
    }

    // EFFECTS: if there is enough Pfizer vaccines, remove them from inventory and add to administered count
    // MODIFIES: this
    // REQUIRES: administeredVaccineCount >= 0

    public boolean administerPfizerVaccine(int administeredVaccineCount) {
        if (this.getPfizerVaccineInventoryCount() < administeredVaccineCount) {
            return false;
        } else {
            this.pfizerVaccineInventoryCount -= administeredVaccineCount;
            this.pfizerVaccineAdministeredCount += administeredVaccineCount;
            return true;
        }
    }

    // EFFECTS: if there is enough Moderna vaccines, remove them from inventory and add to administered count
    // MODIFIES: this
    // REQUIRES: administeredVaccineCount >= 0

    public boolean administerModernaVaccine(int administeredVaccineCount) {
        if (this.getModernaVaccineInventoryCount() < administeredVaccineCount) {
            return false;
        } else {
            this.modernaVaccineInventoryCount -= administeredVaccineCount;
            this.modernaVaccineAdministeredCount += administeredVaccineCount;
            return true;
        }
    }

    // returns JSONObject with VaccineStatus data

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pfizerVaccineInventoryCount", this.pfizerVaccineInventoryCount);
        json.put("pfizerVaccineAdministeredCount", this.pfizerVaccineAdministeredCount);
        json.put("modernaVaccineInventoryCount", this.modernaVaccineInventoryCount);
        json.put("modernaVaccineAdministeredCount", this.modernaVaccineAdministeredCount);
        json.put("maxVaccineInventory", this.maxVaccineInventory);

        return json;
    }

    @Override
    public String toString() {
        String str = "Vaccine Inventory:" + "\n\n";
        str += "Pfizer Vaccine Inventory: " + this.getPfizerVaccineInventoryCount() + "\n";
        str += "Moderna Vaccine Inventory: " + this.getModernaVaccineInventoryCount() + "\n";
        str += "Administered Pfizer Vaccines: "
                + this.getPfizerVaccineAdministeredCount() + "\n";
        str += "Administered Moderna Vaccines: "
                + this.getModernaVaccineAdministeredCount() + "\n";
        str += "Vaccine Inventory Limit: "
                + this.getTotalVaccineInventory() + " / "
                + this.getMaxVaccineInventory();

        return str;
    }
}
