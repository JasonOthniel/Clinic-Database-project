import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
//import java.util.*;
//Database Project. 
//Team member: Jason Lim, Ryan, Allen, Nate Ingram

//
public class App {

    public static void scheduleOrViewAppointment(Scanner userInput) throws ParseException {
        String answer;
        String firstName;
        String lastName;
        String phoneNum;
        String birthDate;
        String ECName;
        String ECNum;
        String ECRelation;
     //   String specialization;
        String timeSlot;
        String diag;
        String insurance;
        char sex;
        Statement sqlQ;         //runs sql
        String output;
        ResultSet result;       //hold output from sql
        String st; //the sql statement
        String toQuit = "q";
        while(true) {
            System.out.println("1. Schedule Appointment");
            System.out.println("2. Change Appointment");
            System.out.println("Input 'q' at any time to quit program");
            answer = userInput.nextLine();
            if(answer.equals(toQuit)) return;
            switch(answer) {
                case "1"://
                //Showing the doctor type table
                st = "select Fname, Lname, docID, specialization from employee, doctor where doctor.docID=employeeID order by specialization";
                result = null;
                try{//Connecting to MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver"); 
                    String db = "jdbc:mysql://localhost:3306/clinicdata";
                    Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                    //sending statement
                    sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                    result = sqlQ.executeQuery(st);//will hold output from sql
                    System.out.println("Name\t\tdoctor ID Specialization");
                    System.out.println("-------------------------------------------------");
                    while(result.next() != false) {
                        output = result.getString("Fname") + 
                            " " + result.getString("Lname") +
                            "\t" + result.getString("docID") +
                            "\t" + result.getString("Specialization");
                            System.out.println(output);
                        }
                    System.out.println("-------------------------------------------------\n\n");
                    sqlQ.close();
                    } catch(ClassNotFoundException e) {
                    System.out.println("jar didnt load right");
                    } catch(SQLException e) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Problem with database.");
                    }
                //ask user to input docID/specialization? then show schedule of the doctor
                String doctorID;
                System.out.println("Please enter docID");
                doctorID = userInput.nextLine();
                if(doctorID.equals(toQuit)) return;
                //show the schedule of specialization chosen
                st = "select distinct Fname, Lname, schedule, docID from dschedule, employee"
                    + " where docID = " + doctorID + " and schedule not in (select timeslot from treat) and "  
                    + doctorID +  "=employeeID";

                try{//Connecting to MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver"); 
                    String db = "jdbc:mysql://localhost:3306/clinicdata";
                    Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                    //sending statement
                    sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                    result = sqlQ.executeQuery(st);//will hold output from sql
                    System.out.println("Doctor ID\tName\t\tschedule");
                    System.out.println("-------------------------------------------------");
                    while(result.next() != false) {
                        output = result.getString("docID") + 
                            "\t\t" + result.getString("Fname") +
                            " " + result.getString("Lname") +
                            "\t" + result.getString("schedule");
                            System.out.println(output);
                        }
                    System.out.println("-------------------------------------------------\n\n");
                    sqlQ.close();
                } catch(ClassNotFoundException e) {
                System.out.println("jar didnt load right");
                } catch(SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("Problem with database.");
                }
                //ask which timelost they want
                //check if timeslot exist
                //if not, then repeat process
                while (true){
                    System.out.println("Enter the timeslot you desire");
                    timeSlot = userInput.nextLine();
                    if (timeSlot.equals(toQuit)) return;
                    String query = "SELECT docID, timeslot from treat where " + doctorID
                        + " = docID and timeslot = '" + timeSlot + "'";
                    //call SQL here
                    try{
                        //Connecting to MySQL
                            Class.forName("com.mysql.cj.jdbc.Driver"); 
                            String db = "jdbc:mysql://localhost:3306/clinicdata";
                            Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                        //sending statement
                            sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                            result = sqlQ.executeQuery(query);//will hold output from sql
                            //if result not null, then good. otherwise, nope
                            if(result == null) {
                                System.out.println("wrong timeslot input");
                                System.out.println("Please enter the correct timeslot.");
                            } else {
                                break;
                            }
                            sqlQ.close();
                        } catch(ClassNotFoundException e) {
                            System.out.println("jar didnt load right");
                        }
                        catch(SQLException e) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Problem with database.");
                    }
                }
                //Asking user to input data
                    System.out.println("\nEnter your first name: ");
                    firstName = userInput.nextLine();
                    if (firstName.equals(toQuit)) return;
                    
                    System.out.println("Enter your last name: ");
                    lastName = userInput.nextLine();
                    if (lastName.equals(toQuit)) return;
                    
                    System.out.println("Enter your phone number: ");
                    System.out.println("Enter 0 to exit:");
                    phoneNum = userInput.nextLine();
                    if (phoneNum.equals(toQuit)) return;
                       

                    while (phoneNum.length() != 10 || ISLong(phoneNum) == false) {
                        System.out.println("Invalid input. Enter your phone number: ");
                        System.out.println("Enter 0 to exit:");
                        phoneNum = userInput.nextLine();
                        if (phoneNum.equals(toQuit)) return;
                    }
                    
                    while(true){
                        System.out.println("Enter your birth date (YYYY-MM-DD): ");
                        birthDate = userInput.nextLine();
                        if (birthDate.equals(toQuit)) return;
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        df.setLenient(false);
                        try {
                            df.parse(birthDate);
                            break;
                        } catch (ParseException e) {
                            System.out.println("wrong input.");
                        }
                    }

                    System.out.println("Fill in sex(M/F)");
                    sex = userInput.next().charAt(0);
                    if (Character.compare(sex, 'q') == 0) return;
                    boolean t = true;
                    while(t) {
                    //check input for sex
                        if(sex == 'm' || sex == 'M' || sex == 'f' || sex == 'F') {t = false;}
                        else {
                            System.out.println("Wrong input. Please enter sex(M/F)");
                            sex = userInput.next().charAt(0);
                        }
                    }

                    System.out.println("Fill in diagnosis(200 characters max)");
                    userInput.nextLine();
                    diag = userInput.nextLine();
                    if (diag.equals(toQuit)) return;

                    System.out.println("Fill in insurance provider(leave blank if none)");
                    insurance = userInput.nextLine();
                    if(insurance == "") {insurance = "NONE";}
                    if (insurance.equals(toQuit));
                    

                    System.out.println("Enter your emergency contact's number: ");
                    System.out.println("Fill your own phone number if you don't want to put emergency contact");
                    ECNum = userInput.nextLine();
                    if (ECNum.equals(toQuit)) return;
                    
                    while (phoneNum.length() != 10 || ISLong(phoneNum) == false) {// also wanted to validate that it's a number but couldn't find an isInteger kinda function
                        System.out.println("Invalid input. Enter your emergency contact number: ");
                        ECNum = userInput.nextLine();
                        if (ECNum.equals(toQuit)) return;
                    }
                    if(!ECNum.equals(phoneNum)) {
                        //want to fill in emergency contact
                        System.out.println("Fill in contact name");
                        ECName = userInput.nextLine();
                        if (ECName.equals(toQuit)) return;
                        System.out.println("Fill in your relation?");
                        ECRelation = userInput.nextLine();
                        if (ECRelation.equals(toQuit)) return;
                    } else { ECName = ""; ECRelation = "";}
                    
                        if (Character.compare(sex, 'q') == 0) return;
                //    }

                    // UPDATE TABLE!
                        //get a new patient ID.
                        st = "select max(patientNum) from patient;";
                        int newNum;
                    try{
                        //Connecting to MySQL
                        Class.forName("com.mysql.cj.jdbc.Driver"); 
                        String db = "jdbc:mysql://localhost:3306/clinicdata";
                        Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                        //sending statement
                        sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                        result = sqlQ.executeQuery(st);//will hold output from sql
                        //if result not null, then good. otherwise, nope
                        if(result ==null) {newNum = 1;} 
                        else {
                            result.next();
                            newNum = 1 + Integer.parseInt(result.getString("max(patientNum)"));
                            result = null;
                        }
                        //get a new patient Number.
                            //sending statement
                                //updating patient table
                        st = "insert into patient values(" + newNum + ",'" + birthDate +
                            "','" + insurance + "','" + firstName + "','" +
                        lastName + "','" + sex + "'," + phoneNum + ",'" +
                        diag + "'," + ECNum + ");";
                        String st2 = "insert into treat values(" + newNum + "," + doctorID +
                        ",'" + timeSlot + "');";
                        sqlQ.addBatch(st);
                        sqlQ.addBatch(st2);
                        sqlQ.executeBatch();
                        System.out.println("Appointment Made");
                        System.out.println("Your patient number is:" + newNum + " and the appointment is " + timeSlot + "\n\n");
                        sqlQ.close();
                        return;
                    } catch(ClassNotFoundException e) {
                        System.out.println("jar didnt load right");
                    }
                    catch(SQLException e) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Problem with database.");
                    }
                    break;
                case "2":
                    //ask user to input their patient number and phone number. then choose doctor type, then choose schedule.
                    String patientNumber;
                    String phoneNumber;
                    result = null;
                    while(true) {
                        System.out.println("Enter your Patient Number");
                        patientNumber = userInput.nextLine();
                        if (patientNumber.equals(toQuit)) return;
                        System.out.println("Enter your Phone Number");
                        phoneNumber = userInput.nextLine();
                        if (phoneNumber.equals(toQuit)) return;
                        while (phoneNumber.length() != 10 || ISLong(phoneNumber) == false) {
                            System.out.println("Invalid input. Enter your phone number: ");
                            phoneNum = userInput.nextLine();
                            if (phoneNum.equals(toQuit)) return;
                        }
                
                //Checking whether patient exist
                        st = "select patientNum from patient where patientNum= " + patientNumber + 
                        " and Phone = " + phoneNumber;
                        //call SQL here
                        try{
                        //Connecting to MySQL
                            Class.forName("com.mysql.cj.jdbc.Driver"); 
                            String db = "jdbc:mysql://localhost:3306/clinicdata";
                            Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                        //sending statement
                            sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                            result = sqlQ.executeQuery(st);//will hold output from sql
                            //if result not null, then good. otherwise, nope
                            if(!result.next()) {
                                System.out.println("Patient do not exist, enter the correct value or fill in the patient data.");
                                sqlQ.close();
                                return;
                            } else {
                                result = null;
                            }
                            sqlQ.close();
                        } catch(ClassNotFoundException e) {
                            System.out.println("jar didnt load right");
                        }
                        catch(SQLException e) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Problem with database.");
                        }
                    //Checking whether appointment exist
                        st = "SELECT docID, timeslot from treat where " + patientNumber
                        + " = patientNum";
                        try{
                            //Connecting to MySQL
                                Class.forName("com.mysql.cj.jdbc.Driver"); 
                                String db = "jdbc:mysql://localhost:3306/clinicdata";
                                Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                            //sending statement
                                sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                                result = null;
                                result = sqlQ.executeQuery(st);//will hold output from sql
                                //if result not null, then good. otherwise, nope
                                if(!result.next()) {
                                    System.out.println("You don't have any appointment.");
                                    System.out.println("Please make a new one\n\n");
                                    sqlQ.close();
                                    return;////////////////
                                } else {
                                    result = null;
                                    sqlQ.close();
                                    break;
                                }
                            } catch(ClassNotFoundException e) {
                                System.out.println("jar didnt load right");
                            }
                            catch(SQLException e) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                            System.out.println("Problem with database.");
                            }
                    }

                //show doctor specialization
                    st = "select Fname, Lname, docID, specialization from employee, doctor where doctor.docID=employeeID order by specialization";
                    try{//Connecting to MySQL
                        Class.forName("com.mysql.cj.jdbc.Driver"); 
                        String db = "jdbc:mysql://localhost:3306/clinicdata";
                        Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                        //sending statement
                        sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                        result = sqlQ.executeQuery(st);//will hold output from sql
                        System.out.println("Name\t\tdoctor ID Specialization");
                        System.out.println("-------------------------------------------------");
                        while(result.next() != false) {
                            output = result.getString("Fname") + 
                                " " + result.getString("Lname") +
                                "\t" + result.getString("docID") +
                                "\t\t" + result.getString("Specialization");
                                System.out.println(output);
                            }
                        System.out.println("-------------------------------------------------\n\n");
                        sqlQ.close();
                        } catch(ClassNotFoundException e) {
                            System.out.println("jar didnt load right");
                        } catch(SQLException e) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                            System.out.println("Problem with database.");
                        }
                //ask user to input docID then show schedule of the doctor
                    System.out.println("Please enter doctor ID");
                    doctorID = userInput.nextLine();
                    if(doctorID.equals(toQuit)) return;
                //show the schedule of specialization chosen
                    st = "select distinct Fname, Lname, schedule, docID from dschedule, employee"
                    + " where docID = " + doctorID + " and schedule not in (select timeslot from treat) and "  
                    + doctorID +  "=employeeID";
                    try{//Connecting to MySQL
                        Class.forName("com.mysql.cj.jdbc.Driver"); 
                        String db = "jdbc:mysql://localhost:3306/clinicdata";
                        Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                        //sending statement
                        sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                        result = sqlQ.executeQuery(st);//will hold output from sql
                        System.out.println("Doctor ID\tName\t\tschedule");
                        System.out.println("-------------------------------------------------");
                        while(result.next() != false) {
                            output = result.getString("docID") + 
                                "\t\t" + result.getString("Fname") +
                                " " + result.getString("Lname") +
                                "\t" + result.getString("schedule");
                                System.out.println(output);
                            }
                        System.out.println("-------------------------------------------------\n\n");
                        sqlQ.close();
                    } catch(ClassNotFoundException e) {
                    System.out.println("jar didnt load right");
                    } catch(SQLException e) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Problem with database.");
                    }    
                //ask which timelost they want
                    //check if timeslot exist
                    //if not, then repeat process
                    while (true){
                        System.out.println("Enter the timeslot you desire");
                        timeSlot = userInput.nextLine();
                        if (timeSlot.equals(toQuit)) return;
                        st = "SELECT docID, timeslot from treat where " + doctorID
                            + " = docID and timeslot = '" + timeSlot + "'";
                            result = null;
                        //call SQL here
                        try{
                            //Connecting to MySQL
                                Class.forName("com.mysql.cj.jdbc.Driver"); 
                                String db = "jdbc:mysql://localhost:3306/clinicdata";
                                Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                            //sending statement
                                sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                                result = sqlQ.executeQuery(st);//will hold output from sql
                                //if result not null, then good. otherwise, nope
                                if(result.next()) {
                                    System.out.println("wrong timeslot input");
                                    System.out.println("Please enter the correct timeslot.");
                                } else {
                                    break;
                                }
                                sqlQ.close();
                            } catch(ClassNotFoundException e) {
                                System.out.println("jar didnt load right");
                            }
                            catch(SQLException e) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                            System.out.println("Problem with database.");
                        }
                    }
                //delete old entry and add new entry
                    st = "DELETE FROM treat WHERE patientNum = " + patientNumber + ";";
                    String st2 = "insert into treat values(" + patientNumber + "," + doctorID +
                        ",'" + timeSlot + "');";
                    try{
                        //Connecting to MySQL
                        Class.forName("com.mysql.cj.jdbc.Driver"); 
                        String db = "jdbc:mysql://localhost:3306/clinicdata";
                        Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                        //sending statement
                        sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                        //updating patient table
                        ////patientNumber, phoneNumber
                        
                        sqlQ.addBatch(st);
                        sqlQ.addBatch(st2);
                        sqlQ.executeBatch();
                        System.out.println("Appointment Change to " + timeSlot + "\n\n");
                        sqlQ.close();
                        return;
                    } catch(ClassNotFoundException e) {
                        System.out.println("jar didnt load right");
                    }
                    catch(SQLException e) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Problem with database.");
                    }

                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
                
            }
        }
    }
    
    

    public static void executeBillCheck(Scanner userInput) {
        Statement sqlQ;         //runs sql
        String output;
        ResultSet result;       //hold output from sql
        System.out.println("Choose to enter Patient Number (A) or Phone Number (B)");

        char optionSelect = userInput.next().charAt(0);
        userInput.nextLine();

        if (optionSelect == 'A' || optionSelect == 'a') {
            System.out.println("Enter your Patient Number");
            String patientNumber = userInput.nextLine();
            String query = "SELECT billID, dateIssued, cost FROM trans WHERE trans.patientNum =" + patientNumber;
            //call SQL here
            try{
                //Connecting to MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver"); 
                    String db = "jdbc:mysql://localhost:3306/clinicdata";
                    Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                //sending statement
                    sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                    result = sqlQ.executeQuery(query);//will hold output from sql
                    System.out.println("Bill Number\tDate Issued\tCost");
                    System.out.println("-------------------------------------------------");
                    while(result.next() != false) {
                        output = result.getString("billID") + 
                            "\t\t" + result.getString("dateIssued") +
                            "\t" + result.getString("cost");
                        System.out.println(output);
                    }
                    System.out.println("-------------------------------------------------\n\n");
                    sqlQ.close();
                } catch(ClassNotFoundException e) {
                    System.out.println("jar didnt load right");
                }
                catch(SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("Problem with database.");
                }
        }
        else if (optionSelect == 'B' ||  optionSelect == 'b') {
            System.out.println("Enter your Phone Number");
            String phoneNumber = userInput.nextLine();
            String query = "SELECT DISTINCT billID, dateIssued, cost FROM trans, patient WHERE trans.patientNum IN" +
            "(SELECT patientNum FROM patient WHERE phone = "+phoneNumber + ");";
            //call SQL here
            try{
                //Connecting to MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver"); 
                    String db = "jdbc:mysql://localhost:3306/clinicdata";
                    Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                //sending statement
                    sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                    result = sqlQ.executeQuery(query);//will hold output from sql
                    System.out.println("Bill Number\tDate Issued\tCost");
                    System.out.println("-------------------------------------------------");
                    while(result.next() != false) {
                        output = result.getString("billID") + 
                            "\t\t" + result.getString("dateIssued") +
                            "\t" + result.getString("cost");
                        System.out.println(output);
                    }
                    System.out.println("-------------------------------------------------\n\n");
                    sqlQ.close();
                } catch(ClassNotFoundException e) {
                    System.out.println("jar didnt load right");
                }
                catch(SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("Problem with database.");
                }
        }
        else {System.out.println("Invalid option selected");}
    }

    public static void executeChangePatientData(Scanner userInput)//add change schedule after done with the other code
    {
        Statement sqlQ;         //runs sql
        System.out.println("Enter your Patient Number");
        String patientNumber = userInput.nextLine();
        while (ISLong(patientNumber) == false) {
            System.out.println("Invalid input. Enter your patient number: ");
            patientNumber = userInput.nextLine();
        }
        System.out.println("Enter your Phone Number");
        String phoneNumber = userInput.nextLine();
        while (phoneNumber.length() != 10 || ISLong(phoneNumber) == false) {
            System.out.println("Invalid input. Enter your phone number: ");
            phoneNumber = userInput.nextLine();
        }
        System.out.println("Enter which field to change: Insurance (i), Phone Number (p), "
        + "Diagnosis (d),\nEmergency Contact Number (e), Emergency Contact Name (c), Relation (r)");
        String userSelect = userInput.nextLine();

        char optionChar = userSelect.charAt(0);

        String optionSelected;
        //CRITICS FROM GRADER: we should have validate whether the patient exists or not.
        switch (optionChar)
        {
            case 'I':
            case 'i':
                optionSelected = "insuranceProvider";
                break;
            case 'p':
            case 'P':
                optionSelected = "Phone";
                break;
            case 'd':
            case 'D':
                optionSelected = "diagnosis";
                break;
            case 'e':
            case 'E':
                optionSelected = "patient.EmergencyNO";
                break;
            case 'c':
            case 'C':
                optionSelected = "ECname";
                break;
            case 'r':
            case 'R':
                optionSelected = "relation";
                break;
            default:
                optionSelected = "None";
        }

        System.out.println("Enter the new data");
        String newData = userInput.nextLine();
        if(optionChar == 'p' || optionChar == 'P' || optionChar == 'E' ||optionChar == 'e') {
        } else {
        newData = "'" + newData + "'";
        }

        String userValidation = "UPDATE patient, emgContact SET " + optionSelected + " = " + newData
        + " WHERE patientNum = " + patientNumber + " AND Phone = " + phoneNumber + " and patient.emergencyNO=emgContact.emergencyNO";

        try{
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                String db = "jdbc:mysql://localhost:3306/clinicdata";
                Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
            //sending statement
                sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                sqlQ.executeUpdate(userValidation);//will hold output from sql
                sqlQ.close();
            } catch(ClassNotFoundException e) {
                System.out.println("jar didnt load right");
            }
            catch(SQLException e) {
            //    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("Wrong input. Enter numbers!\n");
            }
    }


    static void checkDrug(Scanner sc) {
        //	View drug table(only show the ones in stock)(no need to the number of drugs we have)
        //	Search specific drug(ofc if we have the reserve)
        boolean x = true;
        String choice;
      //  Scanner scan = new Scanner(System.in);
        while(x) {
        try{
            System.out.println("Medicine Menu");
            System.out.println("1. View All Medicine");
            System.out.println("2. Search Specific Medicine");
            System.out.println("3. Exit");
            System.out.println("Choose a number:");          
            choice = sc.nextLine();
            Statement sqlQ;         //runs sql
            String output;
            ResultSet result;       //hold output from sql
            switch (Integer.parseInt(choice)) {
                case 1:             //give the whole table without number of medicine left
                    String drug = "select Dname, price, needPrescription from drug where reserve <> 0";
                    try{
                    //Connecting to MySQL
                        Class.forName("com.mysql.cj.jdbc.Driver"); 
                        String db = "jdbc:mysql://localhost:3306/clinicdata";
                        Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                    //sending statement
                        sqlQ = DBconnect.createStatement(); //allow SQL to be executed
                        result = sqlQ.executeQuery(drug);//will hold output from sql
                        System.out.println("Drug Name\tPrice\t\tNeed Prescription");
                        System.out.println("-------------------------------------------------");
                        while(result.next() != false) {
                            output = result.getString("Dname") + 
                                "\t\t" + result.getString("price") +
                                "\t\t" + result.getString("needPrescription");
                            System.out.println(output);
                        }
                        System.out.println("-------------------------------------------------\n\n");
                        sqlQ.close();
                    } catch(ClassNotFoundException e) {
                        System.out.println("jar didnt load right");
                    }
                    catch(SQLException e) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Problem with database.");
                    }  
                    break;
                case 2: //Ask for a medicine name. if empty, tell the user.
                    System.out.print("Type the medicine name:");          
                    String med = sc.nextLine();
                    try{
                        //Connecting to MySQL
                            Class.forName("com.mysql.cj.jdbc.Driver"); 
                            String db = "jdbc:mysql://localhost:3306/clinicdata";
                            Connection DBconnect = DriverManager.getConnection(db, "root", "thePassword");
                        //Sending statement
                            sqlQ = DBconnect.createStatement();
                            result = sqlQ.executeQuery("select Dname, price, needPrescription from drug where Dname = '" + med + "'");
                            System.out.println("\nDrug Name\tPrice\t\tNeed Prescription");
                            System.out.println("-------------------------------------------------");
                            while(result.next() != false) {
                                output = result.getString("Dname") + 
                                    "\t\t" + result.getString("price") +
                                    "\t\t" + result.getString("needPrescription");
                                System.out.println(output);
                            }
                            System.out.println("-------------------------------------------------\n\n");
                            sqlQ.close();
                        } catch(ClassNotFoundException e) {
                            System.out.println("jar didnt load right");
                        }
                        catch(SQLException e) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                            System.out.println("Problem with database.");
                        }
                    break;
                case 3:   //Exiting program
                    x = false;
                    System.out.println("\n\n");
                    break;
                default: 
                    System.out.println("Wrong input. Please choose between 1 - 3");
                    break;
            }
        } catch(java.lang.NumberFormatException e){ System.out.println("Please enter an integer");}
        }
    }
    

    //to check whether a string can be converted to int or not
public static boolean ISLong(String st) {
        try {
          Long y = Long.parseLong(st);
            return true; //String is an Integer
      } catch (NumberFormatException e) {
          return false; //String is not an Integer
      } 
  }




    public static void main(String[] args) {
        System.out.println("DATABASE PROJECT PHASE 4");
        System.out.println("TEAM MEMBER:\n1. Jason Lim\n2.Ryan Allen\n3.Nate Ingram");
        boolean x = true;
        Scanner obj = new Scanner(System.in);
        String choice;    
        while(x) {
        try{   
            System.out.println("Main Menu");
            System.out.println("1. Make or Change Appointment");
            System.out.println("2. Checking Bills");
            System.out.println("3. Changing Patient Data");
            System.out.println("4. Checking Medicine");
            System.out.println("5. Exit");
            System.out.println("Choose a number:");
            choice = obj.nextLine();
            switch (Integer.parseInt(choice)) {
                case 1: 
                    scheduleOrViewAppointment(obj);
                    break;
                case 2:  
                    executeBillCheck(obj);
                    break;
                case 3:  
                    executeChangePatientData(obj);
                    break;
                case 4:
                    checkDrug(obj);
                    break;
                case 5:   //Exiting program
                    x = false;
                    break;
                default: 
                    System.out.println("Wrong input. Please choose between 1 - 5");
                    break;
                }
        } catch(java.lang.NumberFormatException e){ System.out.println("Please enter an integer");
        } catch (ParseException e) {
            System.out.println("Please enter an integer");
        }
        }
        obj.close();
        System.out.println("Exiting Program");
    }
}

