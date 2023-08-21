package saturn2;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.ParseException;
//import FileHandler;

public class Prescription {
	   private String prescriptionID;
	   private String customerID;
	   private String doctorName;
	   ArrayList<Medication> medications;
	   private LocalDate date;
	   private static JSONArray prescriptionList;
	   FileHandler fileHandler = new FileHandler();

	   public Prescription() {
		   prescriptionList = new JSONArray();
	   }
	   
	   public Prescription(String _prescriptionID, String _customerID, String _doctorName, ArrayList<Medication> _medication)
	   {
	       prescriptionID = _prescriptionID;
	       customerID = _customerID;
	       doctorName = _doctorName;
	       medications = _medication;
	       date = LocalDate.now();
	   }
	  
	   public Prescription(String prescriptionID, String customerID, String doctorName, LocalDate dateToPrint, ArrayList<Medication> medications) {
	       this.prescriptionID = prescriptionID;
	       this.customerID = customerID;
	       this.doctorName = doctorName;
	       this.medications = medications;
	       this.date = LocalDate.now();
	       this.fileHandler = new FileHandler();
		}

// TODO: Add code to help you to create object/instance for this class in different way


	   // Factory method to create a prescription with minimal information
	    public static Prescription createPrescription(String _customerID, String _doctorName, ArrayList<Medication> _medications) {
	        return new Prescription(null, _customerID, _doctorName, _medications);
	    }

	    // TODO: Add code to help you access or modify data members for this class


	    public String getPrescriptionID() {
	        return prescriptionID;
	    }

	    public void setPrescriptionID(String prescriptionID) {
	        this.prescriptionID = prescriptionID;
	    }


	    public String getCustomerID() {
	        return customerID;
	    }

	    public void setCustomerID(String customerID) {
	        this.customerID = customerID;
	    }


	    public String getDoctorName() {
	        return doctorName;
	    }
	    
	    public void setDoctorName(String doctorName) {
	    	this.doctorName = doctorName;
	    }
	    
	    public ArrayList<Medication> getMedications(){
	    	return medications;
	    }
	    
	    public void setMedications(ArrayList<Medication> medics) {
			medications = new ArrayList<>();
			this.medications.addAll(medics) ;
	    }
	    
	    public void setDate(LocalDate date) {
	    	this.date = date;
	    }

		public LocalDate getDate() {
		return date;
	}




	// TODO: Add code needed to be able to add prescription in the file
		// While adding the prescription in the file, please follow the format shown below
		// Format for the prescription: {"DoctorName":"Yves","PrescriptionID":"TA3","Medications":[{"quantity":2,"processedStatus":false,"name":"IBUPROFEN","id":"IB7"}],"CustomerID":"GR","Date":"2023-08-07"}

	    public void addPrescription() throws IOException, ParseException {
	        JSONArray existingPrescriptions = fileHandler.readJSONArrayFromFile();

			// TODO: Add code to add prescription in the file
	        JSONObject prescriptionObject = new JSONObject();
	        prescriptionObject.put("DoctorName", doctorName);
	        prescriptionObject.put("PrescriptionID", prescriptionID);

	        prescriptionObject.put("Medications", getMedicationsOnPrescription());
	        prescriptionObject.put("CustomerID", customerID);
	        prescriptionObject.put("Date", date.toString());
	        
	        existingPrescriptions.add(prescriptionObject);

	        fileHandler.writeJSONArrayToFile(existingPrescriptions);
	    }
	   

		// TODO: Add code needed to be able to get all medications on the prescription  
		// TODO: You must return an array of medications!

		public JSONArray  getMedicationsOnPrescription() {
			JSONArray jsonArray = new JSONArray();
			

			// TODO: Add code to get medications on the prescription
			for (Medication medication : medications) {
	            JSONObject medicationObject = new JSONObject();
	            medicationObject.put("id", medication.getID());
	            medicationObject.put("name", medication.getName());
	            medicationObject.put("quantity", medication.getQty());
	            medicationObject.put("processedStatus", medication.getProcessedStatus());
	            
	            jsonArray.add(medicationObject);
	        }
			
			return jsonArray;
		}

		// TODO: Add code to help you viewing all prescriptions in the file
		// You must return an array of prescriptions

	   	public ArrayList<Prescription> viewPrescription() throws IOException, ParseException {
			// TODO: Add code to help you reading from the prescriptions.json file
	   		
	   		ArrayList<Prescription> prescriptions = new ArrayList<>();
	        JSONArray jsonArray = fileHandler.readJSONArrayFromFile();

	        // TODO: Add code to help you creating an array of prescriptions

	        for (Object obj : jsonArray) {
	            JSONObject jsonObject = (JSONObject) obj;
                
                String doctorName = (String) jsonObject.get("DoctorName");
                String prescriptionID = (String) jsonObject.get("PrescriptionID");
                String customerID = (String) jsonObject.get("CustomerID");
                String date = (String) jsonObject.get("Date");
                LocalDate dateToPrint = LocalDate.parse(date);
                
                ArrayList<Medication> medications = new ArrayList<>();
                
                JSONArray medicationsArray = (JSONArray) jsonObject.get("Medications");

                for (Object medObj : medicationsArray) {
                    JSONObject medication = (JSONObject) medObj;

					// TODO: Add code to get medication ID, name and quantity
					// medication quantity should be casted to int
                    // also medication ID and name should be casted to String
                    String medicationID = (String) medication.get("id");
                    String medicationName = (String) medication.get("name");
//					System.out.println(medication.get("quantity") +"\n");
					int quantity = medication.get("quantity").hashCode();

                    medications.add(new Medication(medicationID, medicationName, quantity));
                }

                prescriptions.add(new Prescription(prescriptionID,customerID, doctorName, dateToPrint, medications));
                
            }
	        return prescriptions;

	    }

		// TODO: Add code to help you deleting a specific prescription
		public void deletePrescription(String prescriptionToDelete) throws IOException, ParseException {
			// TODO: Add code to help you reading from the prescriptions.json file
			JSONArray existingPrescriptions = fileHandler.readJSONArrayFromFile();

			int indexToDelete = -1;
			for (int i = 0; i < existingPrescriptions.size(); i++) {
				JSONObject jsonObject = (JSONObject) existingPrescriptions.get(i);
				String existingPrescriptionID = (String) jsonObject.get("PrescriptionID");

				// TODO: Add code to check if the prescription you want to delete is similar to one exists
				if (existingPrescriptionID.equals(prescriptionToDelete)) {
					indexToDelete = i;
					break;
				}
			}

			if (indexToDelete != -1) {
				existingPrescriptions.remove(indexToDelete);
				fileHandler.writeJSONArrayToFile(existingPrescriptions);
				System.out.println("Prescription " + prescriptionToDelete + " has been deleted.");
			}
			else {
				System.out.println("Prescription "+ prescriptionToDelete + " not found");
			}
		}
			
}

