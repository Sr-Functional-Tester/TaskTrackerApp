package com.task.tracker.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FileToSaveRecord {
	private static final Random random = new Random();
	public static void saveMap(Map<String, Map> mapOfAllMaps) throws Exception {
		 //File outputFile = new File("/home/liveuser/Documents/output.txt");
		 File outputFile = checkAndCreateOutputFile("task_tracker_app.txt");
         // Write the extracted text to the output file
         try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(outputFile))) {
             writer.writeObject(mapOfAllMaps);
         }  
         
        // read();
	}
	
	public static  Map<String, Map> readMap()  {
		 //File outputFile = new File("/home/liveuser/Documents/output.txt");
		 File inputfile = checkAndCreateOutputFile("task_tracker_app.txt");
        // Write the extracted text to the output file
		// Write the extracted text to the output file
		 Map<String, Map> mapOfAllMaps=null;
         try (ObjectInputStream writer = new ObjectInputStream(new FileInputStream(inputfile))) {
        	 mapOfAllMaps = (Map<String, Map>) writer.readObject();
         } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
        return mapOfAllMaps;
       // read();
	}
	
	public static void update(List<String> records) throws IOException {
	   // File outputFile = new File("/home/liveuser/Documents/output.txt");
	    File outputFile = checkAndCreateOutputFile("task_tracker_app.txt");
	    // Write the updated list of records to the output file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
	        for (String record : records) {
	            writer.write(record + "\n");
	        }
	    }
	    
	    // Optionally, you can call `read()` again if you need to refresh or use the updated list elsewhere
	    read();
	}
	
	public static void updateOrInsertRecord(String uniqueIdToCheck, String newRecord) throws IOException {
       // File inputFile = new File("/home/liveuser/Documents/output.txt");
        File inputFile = checkAndCreateOutputFile("task_tracker_app.txt");
        File tempFile = checkAndCreateOutputFile("tempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean recordFound = false;

        // Read the file line by line
        while ((line = reader.readLine()) != null) {
            // Split the line by comma and get the last value (unique ID)
            String[] fields = line.split(",");
            String currentUniqueId = fields[fields.length - 1]; // The last element is the unique ID

            // If the unique ID matches the one we're checking for, replace the record
            if (currentUniqueId.equals(uniqueIdToCheck)) {
                writer.write(newRecord); // Write the new record instead of the old one
                recordFound = true;
            } else {
                writer.write(line); // Write the current line as is
            }
            writer.newLine();
        }

        // If the record was not found, add the new record at the end
        if (!recordFound) {
            writer.write(newRecord);
            writer.newLine();
        }

        // Close the reader and writer
        reader.close();
        writer.close();

        // Delete the original file and rename the temp file
        if (inputFile.delete()) {
            if (tempFile.renameTo(inputFile)) {
                System.out.println("File updated successfully.");
            } else {
                System.out.println("Error renaming the temporary file.");
            }
        } else {
            System.out.println("Error deleting the original file.");
        }
    }

	 // Method to generate a unique numeric key
    public static long generateUniqueKey() {
        // Get the current system time in milliseconds
        long currentTime = System.currentTimeMillis();

        // Add a random number to ensure uniqueness
        long randomValue = random.nextInt(1000000); // Random value between 0 and 999999

        // Combine the current time and random number to form a unique key
        return currentTime * 1000000 + randomValue;
    }
    
	public static int countRecords() throws IOException {
		//File file = new File("/home/liveuser/Documents/output.txt");
		File file = checkAndCreateOutputFile("task_tracker_app.txt");
      //  File file = new File(filePath);
        int count = 0;

        // Check if the file exists
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file);
        }

        // Use BufferedReader to read the file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
            }
        }

        return count;
    }
	
	public static List<String> read() throws IOException {
	 // File outputFile = new File("/home/liveuser/Documents/output.txt");
	  File outputFile = checkAndCreateOutputFile("task_tracker_app.txt");
	  List<String> displayList = new ArrayList<String>();
        // Write the extracted text to the output file
       if(outputFile.exists()) {
    	   try {
               // Create a BufferedReader to read the file
               BufferedReader reader = new BufferedReader(new FileReader(outputFile));
               
               String line;
               // Read the file line by line and print content
               while ((line = reader.readLine()) != null) {
                   //System.out.println("Read Line: " + line);  // Print each line of the file
            	   displayList.add(line);
               }
               
               // Close the reader
               reader.close();
    	   }catch(Exception e) {
    		   
    	   }
        }
       
		return displayList;  
	}
	
	public static void main(String[] args) throws IOException {
		//FileToSaveRecord.save("four number");
		FileToSaveRecord.read();
	}
	
	public static File checkAndCreateOutputFile(String fileName) {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");

        // Define the path to the Documents folder
        String documentsPath = userHome + File.separator + "Documents";

        // Define the path to the IAutomate folder
        String automateFolderPath = documentsPath + File.separator + "FileDB";

        // Create a File object for the IAutomate folder
        File automateFolder = new File(automateFolderPath);

        // Check if the IAutomate folder exists
        if (!automateFolder.exists()) {
            // If it doesn't exist, try to create it
            if (automateFolder.mkdir()) {
               // System.out.println("IAutomate folder created successfully.");
            } else {
                //System.out.println("Failed to create IAutomate folder.");
                return null;
            }
        } else {
          //  System.out.println("IAutomate folder already exists.");
        }

        // Define the path to the reminder file inside the IAutomate folder
        File outputFile = new File(automateFolder, fileName);

        // Check if the reminder file exists
        if (!outputFile.exists()) {
            // If it doesn't exist, try to create it
            try {
                if (outputFile.createNewFile()) {
                   // System.out.println("Reminder file created successfully inside IAutomate folder.");
                } else {
                  //  System.out.println("Failed to create reminder file.");
                }
            } catch (IOException e) {
               // System.out.println("Error creating reminder file: " + e.getMessage());
                return null;
            }
        } else {
           //System.out.println("Reminder file already exists.");
        }

        // Return the outputFile object (whether it's newly created or already exists)
        return outputFile;
    }
	
	
}
