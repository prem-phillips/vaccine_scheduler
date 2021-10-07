package ui;

import ui.Component;
import ui.SchedulerAppDisplay;

import javax.swing.*;
import java.awt.*;

// Represents a text field
public class TextField extends Component {

    //EFFECTS: Creates a new JTextField and adds it to parent
    public TextField(SchedulerAppDisplay schedulerAppDisplay, JComponent parent, int x, int y) {
        super(schedulerAppDisplay, parent, getGridBagConstraints(x,y));
    }

    // MODIFIES: this
    // EFFECTS:  creates new component
    @Override
    protected void createComponent() {
        JTextField component = new JTextField("0");

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
        c.insets = new Insets(0, 0, 0, 20);
        //c.anchor = GridBagConstraints.WEST;
        return c;
    }
}
