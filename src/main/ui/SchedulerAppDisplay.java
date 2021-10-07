package ui;

import model.PersonList;
import model.VaccineStatus;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

// Represents the scheduler GUI
public class SchedulerAppDisplay extends JFrame {

    VaccineStatus vaccineStatusModel;
    PersonList personListModel;
    static final String JSON_PATH = "./data/project-data.json";
    JsonReader jsonReader;
    JsonWriter jsonWriter;


    public static final int WIDTH = 720;
    public static final int HEIGHT = 480;

    JPanel buttonArea = new JPanel();
    JPanel infoArea = new JPanel();

    TextField addVaccineField;
    TextField administerVaccineField;
    VaccineSelectField vaccineSelectField;
    VaccineUpdateButton vaccineUpdateButton;
    NewProfileButton newProfileButton;
    BookAppointmentButton bookAppointmentButton;
    //ModifyAppointmentButton modifyAppointmentButton;
    SaveButton saveButton;
    LoadButton loadButton;
    JTextArea vaccineInfo;
    JTextArea appointmentInfo;

    private AudioStream soundStream;

    // EFFECTS: starts application
    public SchedulerAppDisplay() {
        super("Scheduler App");
        init();
        initializeGraphics();
    }

    // EFFECTS: initializes VaccineStatus, PersonList, JsonReader, JsonWriter, soundStream objects
    // MODIFIES: this
    public void init() {
        this.vaccineStatusModel = new VaccineStatus();
        this.vaccineStatusModel.setMaxVaccineInventory(10000);
        this.vaccineStatusModel.setMaxVaccineInventory(10000);
        this.personListModel = new PersonList();
        this.jsonReader = new JsonReader(JSON_PATH);
        this.jsonWriter = new JsonWriter(JSON_PATH);

        try {
            this.soundStream = new AudioStream(new FileInputStream(new File("./data/click1.wav")));
        } catch (Exception e) {
            System.out.println("Could not load sound!");
        }
    }

    // EFFECTS:  draws the JFrame window where this app will operate, and initialize all components
    // MODIFIES: this
    private void initializeGraphics() {
        setLayout(new GridLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        createButtonComponents();
        createInfoComponents();
        addComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all button components
    private void createButtonComponents() {
        this.buttonArea = new JPanel(new GridBagLayout());

        TitleLabel vaccineLabel = new TitleLabel(this, this.buttonArea, "Vaccine inventory manager", 0, 0);
        TextLabel addVaccineLabel = new TextLabel(this, this.buttonArea, "Amount of vaccines to add:", 0, 1);
        this.addVaccineField = new TextField(this, this.buttonArea, 1, 1);
        TextLabel removeVaccineLabel = new TextLabel(this, this.buttonArea, "Amount of vaccines to administer:", 0, 2);
        this.administerVaccineField = new TextField(this, this.buttonArea, 1, 2);
        TextLabel selectVaccineLabel = new TextLabel(this, this.buttonArea, "Vaccine type:", 0, 3);
        vaccineSelectField = new VaccineSelectField(this, this.buttonArea, 1, 3);
        this.vaccineUpdateButton = new VaccineUpdateButton(this, this.buttonArea, 0, 4);

        TitleLabel appointmentLabel = new TitleLabel(this, this.buttonArea, "Appointment manager", 0, 5);
        this.newProfileButton = new NewProfileButton(this, this.buttonArea, 0, 6);
        this.bookAppointmentButton = new BookAppointmentButton(this, this.buttonArea, 0, 7);
    //    this.modifyAppointmentButton = new ModifyAppointmentButton(this, this.buttonArea, 0, 8);
        TextLabel spacerLabel1 = new TextLabel(this, this.buttonArea, "", 0, 8);
        TextLabel spacerLabel2 = new TextLabel(this, this.buttonArea, "", 0, 9);

        this.saveButton = new SaveButton(this, this.buttonArea, 0, 10);
        this.loadButton = new LoadButton(this, this.buttonArea, 1, 10);
    }

    // EFFECTS:  a helper method which declares and instantiates all info components (which contain the buttons)
    // MODIFIES: this
    private void createInfoComponents() {
        this.infoArea = new JPanel(new GridBagLayout());

        this.vaccineInfo = new JTextArea();
        this.appointmentInfo = new JTextArea();

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.33;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(15, 20, 5, 20);
        this.vaccineInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        this.vaccineInfo.setText(this.vaccineStatusModel.toString());
        this.infoArea.add(this.vaccineInfo, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 20, 15, 20);
        this.appointmentInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        this.appointmentInfo.setText("\n\n\n");
        this.infoArea.add(this.appointmentInfo, c);
    }

    // EFFECTS:  a helper method which adds components to JFrame
    // MODIFIES: this
    private void addComponents() {
        GridBagConstraints c = null;

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        this.add(this.buttonArea, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        this.add(this.infoArea, c);
    }

    // EFFECTS:  plays soundStream, and then re-initialize soundStream
    // MODIFIES: this
    public void playClickSound() {
        try {
            AudioPlayer.player.start(this.soundStream);
            this.soundStream = new AudioStream(new FileInputStream(new File("./data/click1.wav")));
        } catch (Exception exception) {
            System.out.println("Could not play sound!");
        }
    }

}
