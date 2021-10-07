package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Person;

// Represents a window to create a new appointment
public class BookAppointmentDialog {

    private JDialog screen;
    private JPanel newPanel;

    private JComboBox<String> newAppointmentSelect;
    private TextField newAppointmentMessageField;
    private TextField newAppointmentDayField;
    private TextField newAppointmentMonthField;
    private TextField newAppointmentYearField;

    private JButton makeNewAppointmentButton;

    private SchedulerAppDisplay schedulerAppDisplay;

    //EFFECTS: Creates a new JDialog and adds it to schedulerAppDisplay. If there are no profiles, dispose immediately
    public BookAppointmentDialog(SchedulerAppDisplay schedulerAppDisplay) {
        this.schedulerAppDisplay = schedulerAppDisplay;
        init();

        TitleLabel newAppointmentTitle = new TitleLabel(schedulerAppDisplay, newPanel, "Book appointment", 0, 0);
        TitleLabel spacerLabel = new TitleLabel(schedulerAppDisplay, newPanel, "", 1, 0);
        TextLabel newAppointmentSelect = new TextLabel(schedulerAppDisplay, newPanel, "Select profile:", 0, 1);
        this.newAppointmentSelect = new JComboBox<String>(getOptions());
        this.newPanel.add(this.newAppointmentSelect);
        TextLabel newAppointmentName = new TextLabel(schedulerAppDisplay, newPanel, "Reason for appointment:", 0, 2);
        this.newAppointmentMessageField = new TextField(schedulerAppDisplay, newPanel, 1, 2);
        ((JTextField) this.newAppointmentMessageField.thisComponent).setText("");
        TextLabel newAppointmentDay = new TextLabel(schedulerAppDisplay, newPanel, "Day of appointment:", 0, 3);
        this.newAppointmentDayField = new TextField(schedulerAppDisplay, newPanel, 1, 3);
        TextLabel newAppointmentMonth = new TextLabel(schedulerAppDisplay, newPanel, "Month of appointment:", 0, 4);
        this.newAppointmentMonthField = new TextField(schedulerAppDisplay, newPanel, 1, 4);
        TextLabel newAppointmentYear = new TextLabel(schedulerAppDisplay, newPanel, "Year of appointment:", 0, 5);
        this.newAppointmentYearField = new TextField(schedulerAppDisplay, newPanel, 1, 5);
        makeNewAppointmentButton = new JButton("Make new appointment");
        this.makeNewAppointmentButton.addActionListener(new NewProfileButtonClickHandler());
        this.makeNewAppointmentButton.setBorderPainted(false);
        this.makeNewAppointmentButton.setFocusPainted(false);
        this.makeNewAppointmentButton.setContentAreaFilled(true);
        this.makeNewAppointmentButton.setBackground(Color.LIGHT_GRAY);
        this.newPanel.add(makeNewAppointmentButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes JPanel and JDialog
    public void init() {
        this.newPanel = new JPanel();
        this.newPanel.setLayout(new GridLayout(7, 2));
        this.schedulerAppDisplay.add(newPanel);
        this.screen = new JDialog(schedulerAppDisplay, "Book appointment");
        this.screen.add(newPanel);
        this.screen.setSize(350, 250);
        this.screen.setLocation(this.schedulerAppDisplay.getX(),this.schedulerAppDisplay.getY());
        this.screen.setVisible(true);
    }

    // EFFECTS: returns options for selection box
    public String[] getOptions() {
        String[] options = new String[schedulerAppDisplay.personListModel.toArrayList().size()];
        for (int i = 0; i < schedulerAppDisplay.personListModel.toArrayList().size(); i++) {
            options[i] = schedulerAppDisplay.personListModel.toArrayList().get(i).getName();
        }
        if (options.length > 0) {
            return options;
        } else {
            screen.dispose(); // close if there are no profiles
            return new String[]{"No profiles created!"};
        }
    }

    private class NewProfileButtonClickHandler implements ActionListener {

        // MODIFIES: this, schedulerAppDisplay
        // EFFECTS: creates new Appointment with respective values. Disposes JDialog afterwards
        @Override
        public void actionPerformed(ActionEvent e) {
            schedulerAppDisplay.playClickSound();

            Person person = schedulerAppDisplay.personListModel.getPerson(newAppointmentSelect.getSelectedIndex());
            String message = ((JTextField) newAppointmentMessageField.thisComponent).getText();
            int day = Integer.parseInt(((JTextField) newAppointmentDayField.thisComponent).getText());
            int month = Integer.parseInt(((JTextField) newAppointmentMonthField.thisComponent).getText());
            int year = Integer.parseInt(((JTextField) newAppointmentYearField.thisComponent).getText());

            person.addAppointment(day,month,year,message);
            schedulerAppDisplay.appointmentInfo.setText(schedulerAppDisplay.personListModel.toString());
            screen.dispose();
        }
    }
}
