# Vaccine Scheduler Program
### Created by Prem Phillips

This program is designed to be used at a vaccination center for COVID-19.

Mass vaccinations can be a logistical nightmare. This program attempts to help the vaccination effort. This program has many features. Firstly, it has the ability to keep track of vaccine inventory. It can keep track of how many vaccines are in storage, and how many have been administered. Secondly, the program can keep track of vaccination appointments.

####*User stories:*
- As a user, I want to be able to get the instructions for this program
- As a user, I want to be able to book an appointment for a vaccination
- As a user, I want to be able to modify an upcoming appointment
- As a user, I want to be able to view upcoming appointments
- As a user, I want to be able to view the vaccine inventory
- As a user, I want to be able to add vaccines to the inventory
- As a user, I want to be able to administer vaccines from the inventory
- As a user, I want to be able to update the settings (ie. modify inventory limit)
- As a user, I want to be able to save the vaccine inventory and upcoming appointments to a file
- As a user, I want to be able to load the vaccine inventory and upcoming appointments from a file

#####*Phase 4: Task 2*

I have chosen to follow option 2 for this task. This option required me to describe  a type hierarchy. In the project, there are multiple hierarchies. For this task, I will describe the "Component" hierarchy in the ui package. When I was designing the project, I noticed that there are many components in the GUI which have similarities. I decided to represent a general component as an abstract class. The abstract class contains a JComponent field, and an abstract method called createComponent. Each subclass initializes the JComponent to a specific object, such as a JButton or a JTextField.

#####*Phase 4: Task 3:* 

When reflecting on my project, I have noticed a few improvements that could be made in the future. I think the addition of more abstract classes would reduce the amount of code in this project. For example, I could have created an abstract class for a general button, and a general JDialog box. These classes would help reduce code, and decrease the semantic coupling inside the ui package.