package saturn2;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

public class PrescriptionManagement {

  

   public static void main(String[] args) throws IOException, ParseException {
	   
	   
	   BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       Scanner scan =new Scanner(System.in);
       int choice, numMedications;
       Prescription prescription = new Prescription();
	   

      while(true) {
        	 
         while(true) {
// test
//             File f = new File("./files/products.json");
//             if(f.exists() && !f.isDirectory() && f.canRead()) {
//                System.out.println("yes it exists!");
//             }

            // TODO: Add code to display the menu and get the number(choice) a user selected
            System.out.println("\n====MENU=====");
            System.out.println("1. Add Prescription");
            System.out.println("2. View Prescription");
            System.out.println("3. Process Prescription");
            System.out.println("4. Exit");
            System.out.println("\nPlease select an option from [1-4]: ");
            choice = scan.nextInt();

            switch (choice) {
               case 1:
            	   
                    // TODO: Add code to get Prescription ID, Customer ID,  Doctor's Name
                    // Don't forget to add code to save these information in the prescription
                   System.out.println("\n\t\t\t\tADD PRESCRIPTION\n");
                   scan.nextLine();
                   System.out.println("Please enter the Prescription ID: ");
                   String prescriptionID = scan.nextLine();
                   prescription.setPrescriptionID(prescriptionID);

                   System.out.println("Please enter the Customer's ID: ");
                   String customerID = scan.nextLine();
                   prescription.setCustomerID(customerID);

                   System.out.println("Enter Doctor's Name: ");
                   String doctorName = scan.nextLine();
                   prescription.setDoctorName(doctorName);

                   prescription.setDate(LocalDate.now());

                   System.out.print("Enter the number of medications to add: ");
                   numMedications = Integer.parseInt(reader.readLine());
                   
                   ArrayList<Medication> medications = new ArrayList<>();
                   String medicationName, medicationDetails, dosage, medicationID;
                   int quantity;

                   // TODO: Add code to display available products/medications before adding them on the prescription
                   String medicationsFilePath = "./files/products.json";
                   displayMedications(medicationsFilePath);


                   for (int i = 1; i <= numMedications; i++) {
                       scan.reset();//scan.nextLine();
                       System.out.println("\n\nEnter details for Medication " + i + ":\n");

                       System.out.println("Please enter the Medication ID: ");
                       medicationID = scan.nextLine();

                       System.out.println("Please enter the Medication Name: ");
                       medicationName = scan.nextLine();

                       System.out.println("Please enter the Medication Details: ");
                       medicationDetails = scan.nextLine();

                       System.out.println("Please enter the Medication Dosage: ");
                       dosage = scan.nextLine();

                       System.out.println("Please enter the Medication Quantity: ");
                       quantity = scan.nextInt();

                       // to consume any newline character remaining
                       scan.nextLine();

                        // TODO: Add code to get Medication ID, Name, Details, Dosage and Quantity
                       Medication medication = new Medication(medicationID, medicationName, medicationDetails, dosage, quantity);
                       medications.add(medication);
                    }
                   
                    // TODO: Add code to save all medications inserted by the user on the prescription
                   prescription.setMedications(medications);
                   prescription.addPrescription();

                   break;
               case 2:
                    // TODO: Add code to retrieve all prescriptions in the file
                   System.out.println("\n\t\t\t\tVIEW PRESCRIPTION\n");
                   ArrayList<Prescription> prescriptions;
                   prescriptions = prescription.viewPrescription();
                    // Prescriptions must be returned in the array

                   if(prescriptions.size()==0) {
                       System.out.println("No prescriptions available\n");
                   }
                   else {
                       System.out.println("| PrescriptionID |  DoctorName   |    CustomerID | \tDate\t | ");
                       System.out.println("******************************************************************");
                       
                       for(Prescription p: prescriptions) 
                       {
                           System.out.println("|\t  "+ p.getPrescriptionID()+"\t\t\t\t"+ p.getDoctorName()+ "\t\t  " + p.getCustomerID()+"\t\t" + p.getDate());
                           
                           System.out.println("");
                           System.out.println("| MedicationID |  \tName    | \tQuantity | ");
                           for(Medication med : p.getMedications()) 
                           {
                               System.out.println("|\t  "+ med.getID()+"\t\t\t"+ med.getName() + "\t\t " + med.getQty() );
                           }
                       
                           System.out.print("\n");
                           System.out.println("*****************************************************************");    
                       }
                       
                       System.out.println("");
                   }
                   
            	   break;
               case 3:
                   System.out.println("\n\t\t\t\tPROCESS PRESCRIPTION\n");

                   System.out.println("Please Enter the ID of the prescription you want to delete: ");
                 String pID = scan.next();
                    // TODO: Add code to get the ID of the prescription you want to delete
                   prescription.deletePrescription(pID);
                  break;
               case 4:
                   System.out.println("Exiting the Prescription Management section...");
                   System.exit(0);
               default:
                  System.out.println("Invalid choice. Please try again!");
            }
            
            
         }
         
         
      }
   }
   
   
   
   public static void displayMedications(String filePath) throws FileNotFoundException, IOException, ParseException {
	   
	      JSONParser parser = new JSONParser();
	      try(FileReader fileReader = new FileReader(filePath)){
	          if (fileReader.read() == -1) {
	              return;
	          } 
	          else {
	              fileReader.close();
	              JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
	              
                  System.out.println("\n\n---------------------------------------------------------------------------------------");
                  System.out.println("|\t"  + "\t\t  "  + "\t\t\t\t");
                  System.out.println("|\t" + "\t\t"  +  "Available Medications" + "\t\t");
                  System.out.println("|\t"  + "\t\t  "  + "\t\t\t\t");
                  System.out.println("---------------------------------------------------------------------------------------");
                  System.out.println("| Medication ID |  Medication Name   |    Medication Price ||    Medication Quantity |");
                  System.out.println("---------------------------------------------------------------------------------------");
                  
	              for (Object obj: jsonArray) {
	                  JSONObject jsonObject = (JSONObject) obj;
                      String medicationID = (String) jsonObject.get("code");
                      String medicationName = (String) jsonObject.get("name");
                      String medicationPrice = (String) jsonObject.get("price");
                      String medicationQuantity = jsonObject.get("quantity").toString();


                    // TODO: Add code to get medication ID (it's named as code from medications/products file), name, price and quantity
                    // medication ID, name, price and quantity should be casted to String

                      System.out.println("|\t" + medicationID + "\t\t\t" + medicationName + "\t\t\t\t\t  " + medicationPrice + "\t\t\t\t  " + medicationQuantity + "\t\t");
	                  
	              }
                  System.out.println("---------------------------------------------------------------------------------------");

	              
	          }  
	      }
	   
   } 


}
