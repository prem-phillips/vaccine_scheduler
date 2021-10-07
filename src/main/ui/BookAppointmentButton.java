package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a button to create a new appointment
public class BookAppointmentButton extends Component {

    //EFFECTS: Creates a new JButton and adds it to parent
    public BookAppointmentButton(SchedulerAppDisplay schedulerAppDisplay, JComponent parent, int x, int y) {
        super(schedulerAppDisplay, parent, getGridBagConstraints(x,y));
    }

    // MODIFIES: this
    // EFFECTS:  creates new component
    @Override
    protected void createComponent() {
        JButton component = new JButton("Book appointment");
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
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 20, 0, 20);
        //c.anchor = GridBagConstraints.WEST;
        return c;
    }

    private class AddButtonClickHandler implements ActionListener {

        // EFFECTS: When clicked, play sound and open BookAppointmentDialog
        @Override
        public void actionPerformed(ActionEvent e) {
            schedulerAppDisplay.playClickSound();

            BookAppointmentDialog bookAppointmentDialog = new BookAppointmentDialog(schedulerAppDisplay);
        }
    }
}
