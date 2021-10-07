package ui;

import javax.swing.*;
import java.awt.*;

// Represents a text label
public class TextLabel extends Component {

    //EFFECTS: Creates a new JLabel and adds it to parent
    public TextLabel(SchedulerAppDisplay schedulerAppDisplay, JComponent parent, String message, int x, int y) {
        super(schedulerAppDisplay, parent, getGridBagConstraints(x, y));
        ((JLabel) this.thisComponent).setText(message);
    }

    // MODIFIES: this
    // EFFECTS:  creates new component
    @Override
    protected void createComponent() {
        JLabel component = new JLabel();

        component.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 13));

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
        c.insets = new Insets(0, 25, 0, 0);
        //c.anchor = GridBagConstraints.WEST;
        return c;
    }
}
