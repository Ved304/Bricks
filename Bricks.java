package mainpackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Bricks {
	
	public static boolean isValidString(String input) {
		
		if (input.length() < 6 || input.indexOf(':') == -1) {
			System.out.println("Warunek 1");
            return false;
        }
		else {
			
			String[] parts = input.split(":");
			
			if(!parts[0].matches("[0-9]+") || !parts[1].matches("[A-O]{4}")) {
				System.out.println("Warunek 2");
				return false;
			}
			
		}
		
		return true;
		
	}

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		//-----KONFIGURACJA-----
		
		String filePath = System.getProperty("user.dir") + "/src/mainpackage/file.txt";
        
		List<String> blockList = new ArrayList<>();
		
		List<String> instructList = new ArrayList<>();
		
		List<String> buildingList = new ArrayList<>();
		
		int prevBuilding = 0;
		
		int successCounter = 0;
		
		int failureCounter = 0;
		
		int missingCounter = 0;
		
		int phaseOneCounter = 0;
		
		int phaseTwoCounter = 0;
		
		int skip = 0;
		
		//-----ODCZYT DANYCH-----
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if(isValidString(line) == false) {
            		System.out.println("klops");
            		long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;
                    System.out.println("Elapsed time: " + (elapsedTime / 1000.0) + " seconds");
            		System.exit(0);
            	}
            	
                String[] parts = line.split(":");
                int number = Integer.parseInt(parts[0].trim());
                
                if (parts.length == 2 && parts[0].equals("0")) {
                    String string = parts[1].trim();
                    blockList.add(string);
                }
                else if(parts.length == 2) {
                    instructList.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Collections.sort(instructList, Comparator.comparing(s -> s.charAt(0)));
        /*
        System.out.println("Block list:");
        for (String string : blockList) {
            System.out.println(string);
        }
        
        System.out.println("Instruction list:");
        for (String string : instructList) {
            System.out.println(string);
        }
        */
        //-----ETAP 1-----
        
        for(String line : instructList) {
            String[] parts = line.split(":");
            int number = Integer.parseInt(parts[0].trim());
            
            if(number != skip) {
            	
                if(parts.length == 2 && number % 3 == 0 && number != 0) {
                	
                	if(number != prevBuilding) {
                    	prevBuilding = number;
                    	successCounter++;
                    	buildingList.clear();
                    }
                	
                	int index = blockList.indexOf(parts[1]);

                	if (blockList.contains(parts[1])) {
                	    String removedElement = blockList.remove(index);
                	    buildingList.add(removedElement);
                	    phaseOneCounter++;
                	} else {
                	    //System.out.println("Element not found in blockList.");
                	    blockList.addAll(buildingList);
                	    phaseOneCounter = phaseOneCounter - buildingList.size();
                	    buildingList.clear();
                	    successCounter--;
                	    failureCounter++;
                	    skip = number;
                	    missingCounter++;
                	}
                }
            	
            }
            else {
            	if(blockList.contains(parts[1]) == false) {
            		missingCounter++;
            	}
            	
            }
            /*
            System.out.println("Strings with prefix 0:");
            for (String string : blockList) {
                System.out.println(string);
            }
            
            System.out.println("Strings in buildings:");
            for (String string : buildingList) {
                System.out.println(string);
            }
            */
        }
        
        System.out.println(phaseOneCounter);
        System.out.println(phaseTwoCounter);
        System.out.println(blockList.size());
        System.out.println(missingCounter);
        System.out.println(successCounter);
        System.out.println(failureCounter);
        
        buildingList.clear();
		
        System.out.println("FAZA DRUGA");
        
        //-----ETAP 2-----
        
        for(String line : instructList) {
            String[] parts = line.split(":");
            int number = Integer.parseInt(parts[0].trim());
            
            if(number != skip) {
            	
                if(parts.length == 2 && number % 3 != 0 && number != 0) {
                	
                	if(number != prevBuilding) {
                    	prevBuilding = number;
                    	successCounter++;
                    	buildingList.clear();
                    }
                	
                	int index = blockList.indexOf(parts[1]);

                	if (blockList.contains(parts[1])) {
                	    String removedElement = blockList.remove(index);
                	    buildingList.add(removedElement);
                	    phaseTwoCounter++;
                	} else {
                	    //System.out.println("Element not found in blockList.");
                	    blockList.addAll(buildingList);
                	    phaseTwoCounter = phaseTwoCounter - buildingList.size();
                	    buildingList.clear();
                	    successCounter--;
                	    failureCounter++;
                	    skip = number;
                	    missingCounter++;
                	}
                	//System.out.println("Licznik");
                	//System.out.println(phaseTwoCounter);
                }
            	
            }
            
            /*
            System.out.println("Strings with prefix 0:");
            for (String string : blockList) {
                System.out.println(string);
            }
            
            System.out.println("Strings in buildings:");
            for (String string : buildingList) {
                System.out.println(string);
            }
            */
        }
        
        System.out.println(phaseOneCounter);
        System.out.println(phaseTwoCounter);
        System.out.println(blockList.size());
        System.out.println(missingCounter);
        System.out.println(successCounter);
        System.out.println(failureCounter);
        
        buildingList.clear();
        
        //-----TESTOWE-----
        /*
        System.out.println("Strings with prefix 0:");
        for (String string : blockList) {
            System.out.println(string);
        }
        
        System.out.println("Strings in buildings:");
        for (String string : buildingList) {
            System.out.println(string);
        }
        */
        
        /*
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                int number = Integer.parseInt(parts[0].trim());
                
                if(parts.length == 2 && number % 3 != 0 && number != 0) {
                	
                	int index = blockList.indexOf(parts[1]);

                	if (blockList.contains(parts[1])) {
                	    String removedElement = blockList.remove(index);
                	    buildingList.add(removedElement);
                	} else {
                	    System.out.println("Element not found in blockList.");
                	}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time: " + (elapsedTime / 1000.0) + " seconds");
        
        System.exit(0);
        
	}

}
