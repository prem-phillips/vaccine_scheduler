package ui;

import javax.swing.*;
import java.awt.*;

// Represents a component in the GUI
public abstract class Component {

    protected JComponent thisComponent;
    protected SchedulerAppDisplay schedulerAppDisplay;

    // EFFECTS: Creates component and adds it to parent
    public Component(SchedulerAppDisplay schedulerAppDisplay, JComponent parent, GridBagConstraints c) {
        this.schedulerAppDisplay = schedulerAppDisplay;
        createComponent();
        parent.add(this.thisComponent, c);
    }

    // EFFECTS: creates new component
    // MODIFIES: this
    protected abstract void createComponent();

}
