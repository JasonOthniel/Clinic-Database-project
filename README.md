# Clinic-Database-project

This is from a school project from my summer 2022.
This is a group project with two other classmates.
The goal is to make a small database of an imaginary company and make an application to access the database. 
The goal is to apply the knowledge of SQL.
The application was made with Java programming language using VS code.
The pictures uploaded are rough diagrams for the database, it does not reflect the final database.

# What is in the database?
Some points are further split into 2 tables. To see more details refer to .sql file(the query that is run in the application is not there.
- patient's data(Name, birth date phone number, diagnosis, emergency contact, insurance provider)
- employee's data(Name, SSN, birth date, salary, occupation
- doctor's schedule(doctor's ID and time slot
- doctor's specialization(doctor's ID and specialization)
- medicine data sold in the clinic's pharmacy(name, price, information whether a medicine needs a prescription or not, number of available supply.
- transactions(billing ID, patients ID being billed, date, amount payed, cashier ID
- medicine supplier and medicine orders
The initial data injected to the database are also in the same file.

# What can the application do?
The application is solely for patient(users) who wants to make appointment to the clinic. 
You can imagine the program is access through the web or donwloaded application.
The user choose a number shown in the Menu. They can input 'q' and enter it to quit at any time.
The program can also validate input between alphabets and digits.
1. Create/Change schedule.
- The user will be asked whether they want to make/update the schedule.
-  If they choose to make a new one, they will be given a list of doctor's(with their ID and specialization)(they choose by entering the doctor's ID). Then it will show all available schedules. 
- After that the user will fill their data. If they do not want to fill in the emergency contact information, they can fill it with their own phone number.
- If the appointment is sucessful(database sucesfully updated), it will let the user know and give a 'patient number' to the user.
- If the user want to change schedule, it will verivy the user by asking them to input their patient number. The program will check whether the user already has an appointment or not. If not then the it will the user to make a new one, otherwise it will repeat the above process minus inputting the user's information.
3. Check Bill
- To verify the user, it will ask the patient to input their patient's number of phone number.
4. Change patient's data
- same as the previous function it will verify the user, after that they can choose what data they want to change.(It use the 'UPDATE' query from the SQL)
5. Check Medicine
- It has two uses: view entire medicine supply, search for a specific medicine and their supply. Any drugs that are out of stock will not be shown.

# How to connect the JDBC?
This answer is for VS code only. Also I run the server in my own computer.
I use the JDBC connector 'mysql-connector-java-8.0.30'(I assume you can use other versions just fine).
In VS code you, if you open the 'Explore tab' you will find 'JAVA PROJECTS'. 
Under that tab you will find 'Referenced Libraries'. 
Click the 'plus' button and put your connector there.

# Where is the query used in the application?
You can check on the App.java, that is where all the application code is.
