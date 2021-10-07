package ui;

import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// Represents a button to save data
public class SaveButton extends Component {

    //EFFECTS: Creates a new JButton and adds it to parent
    public SaveButton(SchedulerAppDisplay schedulerAppDisplay, JComponent parent, int x, int y) {
        super(schedulerAppDisplay, parent, getGridBagConstraints(x, y));
    }

    // MODIFIES: this
    // EFFECTS:  creates new component
    @Override
    protected void createComponent() {
        JButton component = new JButton("Save");
        component.setBorderPainted(true);
        component.setFocusPainted(false);
        component.setContentAreaFilled(true);
        component.addActionListener(new AddButtonClickHandler());
        component.setBackground(Color.LIGHT_GRAY);

        this.thisComponent = component;
    }

    // EFFECTS: Returns GridBagConstraints for component with respective x and y
    public static GridBagConstraints getGridBagConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 20, 0, 20);
        //c.anchor = GridBagConstraints.WEST;
        return c;
    }

    private class AddButtonClickHandler implements ActionListener {

        // MODIFIES: schedulerAppDisplay
        // EFFECTS: plays sound and saves VaccineStatus and PersonList to JSON_PATH
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                schedulerAppDisplay.playClickSound();

                JsonWriter jsonWriter = schedulerAppDisplay.jsonWriter;
                jsonWriter.open();
                jsonWriter.write(schedulerAppDisplay.vaccineStatusModel, schedulerAppDisplay.personListModel);
                jsonWriter.close();

                JTextArea appointmentInfo = schedulerAppDisplay.appointmentInfo;
                appointmentInfo.setText(schedulerAppDisplay.personListModel.toString() + "Saved data!");
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + SchedulerAppDisplay.JSON_PATH + "\n");
            }

        }
    }
}
