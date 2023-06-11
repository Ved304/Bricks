import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Bricks {
	
	public static boolean isValidString(String input) {
		
		String[] parts = input.split(":");
		
		if(parts.length != 2 || !parts[0].matches("[0-9]+") || !parts[1].matches("[A-O]{4}")) {
			return false;
		}
		
		return true;
		
	}

	public static void main(String[] args) {
		
		//-----KONFIGURACJA-----
		
		String filePath = System.getProperty("user.dir") + "/src/mainpackage/plik.txt";
        
		List<String> blockList = new LinkedList<>();
		List<String> instructList = new LinkedList<>();
		List<String> buildingList = new LinkedList<>();
		
		int prevBuilding = 0;
		int successCounter = 0;
		int failureCounter = 0;
		int missingCounter = 0;
		int phaseOneCounter = 0;
		int phaseTwoCounter = 0;
		int skip = 0;
		
		//-----ODCZYT DANYCH-----
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if(isValidString(line) == false) {
            		System.out.println("klops");
            		System.exit(0);
            	}
            	
                String[] parts = line.split(":");
                
                if (parts.length == 2 && parts[0].equals("0")) {
                    blockList.add(parts[1]);
                }
                else if(parts.length == 2) {
                    instructList.add(line);
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Collections.sort(instructList, Comparator.comparing(s -> s.charAt(0)));
        
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

                	if (blockList.remove(parts[1])) {
                	    String removedElement = parts[1];
                	    buildingList.add(removedElement);
                	    phaseOneCounter++;
                	} else {
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
            
        }
        
        buildingList.clear();
        
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

                	if (blockList.remove(parts[1])) {
                	    String removedElement = parts[1];
                	    buildingList.add(removedElement);
                	    phaseTwoCounter++;
                	} else {
                	    blockList.addAll(buildingList);
                	    phaseTwoCounter = phaseTwoCounter - buildingList.size();
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
            
        }
        
        System.out.println(phaseOneCounter);
        System.out.println(phaseTwoCounter);
        System.out.println(blockList.size());
        System.out.println(missingCounter);
        System.out.println(successCounter);
        System.out.println(failureCounter);
        
        buildingList.clear();
        
        System.exit(0);
        
	}

}
