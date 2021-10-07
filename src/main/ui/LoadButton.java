package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Represents a button to load saved data
public class LoadButton extends Component {

    //EFFECTS: Creates a new JButton and adds it to parent
    public LoadButton(SchedulerAppDisplay schedulerAppDisplay, JComponent parent, int x, int y) {
        super(schedulerAppDisplay, parent, getGridBagConstraints(x,y));
    }

    // MODIFIES: this
    // EFFECTS:  creates new component
    @Override
    protected void createComponent() {
        JButton component = new JButton("Load");
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
        // EFFECTS: plays sound, and loads VaccineStatus and PersonList from JSON_PATH
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                schedulerAppDisplay.playClickSound();

                schedulerAppDisplay.vaccineStatusModel = schedulerAppDisplay.jsonReader.getVaccineStatus();
                schedulerAppDisplay.personListModel = schedulerAppDisplay.jsonReader.getPersonList();
                schedulerAppDisplay.vaccineInfo.setText(schedulerAppDisplay.vaccineStatusModel.toString());
                schedulerAppDisplay.appointmentInfo.setText(schedulerAppDisplay.personListModel.toString());
            } catch (IOException exception) {
                System.out.println("Unable to read data from " + SchedulerAppDisplay.JSON_PATH + "\n");
            }
        }
    }
}
