package lab_5;

import java.util.Arrays;
// Max Quintavalle & Matt Wanner
// got help from collin beane from class
// and refrenced geeks for  geeks stable marriage problem & used chat gpt to help format it.
public class StableMarriage {

    public static String assignJobs(String[][] programmers, String[][] companies) {
        int programmerCount = programmers.length; // number of programmers
        int companyCount = companies.length; // number of companies

       
        boolean[] programmerStatus = new boolean[programmerCount]; 
        int[] companyAssignment = new int[companyCount]; 

        Arrays.fill(programmerStatus, false);
        Arrays.fill(companyAssignment, -1);

        int unmatchedProgrammers = programmerCount; // total number of programmers 

        
        while (unmatchedProgrammers > 0) {
            for (int programmer = 0; programmer < programmerCount; programmer++) { // Loop through each programmer, checking if they have a match
                if (!programmerStatus[programmer]) { 
                    String[] programmerPreferences = Arrays.copyOfRange(programmers[programmer], 1, programmers[programmer].length); // check the programmer's preferences
                    for (String company : programmerPreferences) { 
                        int companyIndex = -1;
                        for (int i = 0; i < companyCount; i++) {
                            if (companies[i][0].equals(company)) {
                                companyIndex = i;
                                break;
                            }
                        }
                        if (companyAssignment[companyIndex] == -1) { // Checks that the company is not assigned to any programmer
                            programmerStatus[programmer] = true; // Mark that the programmer has a match
                            companyAssignment[companyIndex] = programmer + 1; 
                            unmatchedProgrammers--; 
                            break; 
                        } else {
                            int currentProgrammer = companyAssignment[companyIndex] - 1; // Get programmer of the company
                            String[] companyPreferences = Arrays.copyOfRange(companies[companyIndex], 1, companies[companyIndex].length); // Get the company's preferences
                            int currentIndex = Arrays.asList(companyPreferences).indexOf(String.valueOf(currentProgrammer + 1)); 
                            int newIndex = Arrays.asList(companyPreferences).indexOf(String.valueOf(programmer + 1)); 
                            if (newIndex < currentIndex) { // checks if the company prefers new programmer
                                programmerStatus[programmer] = true;
                                programmerStatus[currentProgrammer] = false; // unmatches the old programmer
                                companyAssignment[companyIndex] = programmer + 1; // match the new programmer
                                break; 
                            }
                        }
                    }
                }
            }
        }
        

       
        StringBuilder result = new StringBuilder();
        for (int company = 0; company < companyCount; company++) {
            result.append(companies[company][0]).append(companyAssignment[company]);
            if (company < companyCount - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }
        
    public static boolean isStableMarriage(String assignment, String[][] programmerPreferences, String[][] companyPreferences) {
        String[] assignments = assignment.split(", ");
        for (String assignmentPair : assignments) {
            String[] pair = assignmentPair.split("");
            int programmer = Integer.parseInt(pair[1]) - 1;
            int companyIndex = -1;
            for (int i = 0; i < companyPreferences.length; i++) {
                if (companyPreferences[i][0].equals(pair[0])) {
                    companyIndex = i;
                    break;
                }
            }
            String[] programmerPrefs = programmerPreferences[programmer];
            String[] companyPrefs = companyPreferences[companyIndex];

            // Find the index of the programmer and company preferences
            int programmerIndex = -1;
            int companyPreferenceIndex = -1;
            for (int i = 1; i < programmerPrefs.length; i++) {
                if (programmerPrefs[i].equals(pair[0])) {
                    programmerIndex = i;
                    break;
                }
            }
            for (int i = 1; i < companyPrefs.length; i++) {
                if (companyPrefs[i].equals(pair[1])) {
                    companyPreferenceIndex = i;
                    break;
                }
            }

            // Check if the company prefers the programmer over its current assignment
            if (programmerIndex < companyPreferenceIndex) {
                // Find the current programmer assigned to the company
                int currentProgrammer = Integer.parseInt(companyPrefs[1]) - 1;
                // Find the company currently assigned to the current programmer
                String currentCompany = programmerPreferences[currentProgrammer][1];
                // Find the index of the current company in the company's preferences
                int currentCompanyIndex = -1;
                for (int i = 1; i < companyPrefs.length; i++) {
                    if (companyPrefs[i].equals(currentCompany)) {
                        currentCompanyIndex = i;
                        break;
                    }
                }
                // Check if the new programmer prefers the company over their current assignment
                if (programmerIndex < currentCompanyIndex) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
     
        String[][] programmerPreferences1 = {
            {"1", "E", "A", "D", "B", "C"},
            {"2", "D", "E", "B", "A", "C"},
            {"3", "D", "B", "C", "E", "A"},
            {"4", "C", "B", "D", "A", "E"},
            {"5", "A", "D", "B", "C", "E"}
        };
        String[][] companyPreferences1 = {
            {"A", "2", "5", "1", "3", "4"},
            {"B", "1", "2", "3", "4", "5"},
            {"C", "5", "3", "2", "1", "4"},
            {"D", "1", "3", "2", "4", "5"},
            {"E", "2", "3", "5", "4", "1"}
        };

         String assignment1 = assignJobs(programmerPreferences1, companyPreferences1);
            System.out.println(assignment1);
       System.out.println((isStableMarriage(assignment1, programmerPreferences1, companyPreferences1))); 

        String[][] programmerPreferences2 = {
            {"1", "A", "E", "D", "B", "C"},
            {"2", "B", "E", "D", "A", "C"},
            {"3", "C", "B", "D", "E", "A"},
            {"4", "D", "B", "C", "A", "E"},
            {"5", "E", "D", "B", "C", "A"}
        };
        String[][] companyPreferences2 = {
            {"A", "1", "5", "2", "3", "4"},
            {"B", "2", "1", "3", "4", "5"},
            {"C", "3", "5", "2", "1", "4"},
            {"D", "4", "3", "2", "1", "5"},
            {"E", "5", "3", "2", "4", "1"}
        };

         String assignment2 = assignJobs(programmerPreferences2, companyPreferences2);
            System.out.println(assignment2);
       System.out.println((isStableMarriage(assignment2, programmerPreferences2, companyPreferences2)));

        String[][] programmerPreferences3 = {
            {"1", "D", "B", "C"},
            {"2", "D", "B", "C"},
            {"3", "D", "B", "C",}
           
        };
        String[][] companyPreferences3 = {
            {"D", "2", "1", "3"},
            {"B", "1", "2", "3"},
            {"C", "3", "2", "1"}
        
        };

        String assignment3 = assignJobs(programmerPreferences3, companyPreferences3);
            System.out.println(assignment3);
       System.out.println((isStableMarriage(assignment3, programmerPreferences3, companyPreferences3)));


        String[][] programmerPreferences4 = {
            {"1", "Z", "A", "Y", "E", "C"},
            {"2", "E", "Y", "Z", "A", "C"},
            {"6", "Y", "E", "C", "Z", "A"},
            {"4", "C", "Z", "E", "A", "Y"},
            {"9", "A", "Y", "E", "C", "Z"}
        };
        String[][] companyPreferences4 = {
            {"A", "2", "5", "1", "3", "4"},
            {"Z", "1", "2", "3", "4", "5"},
            {"C", "5", "3", "2", "1", "4"},
            {"Y", "1", "3", "2", "4", "5"},
            {"E", "2", "3", "5", "4", "1"}
        };

        String assignment4 = assignJobs(programmerPreferences4, companyPreferences4);
            System.out.println(assignment4);
       System.out.println((isStableMarriage(assignment4, programmerPreferences4, companyPreferences4)));


       String[][] programmerPreferences5 = {
            {"1", "E", "A", "D", "K", "B", "C"},
            {"2", "D", "K", "E", "B", "A", "C"},
            {"3", "K", "D", "B", "C", "E", "A"},
            {"4", "C", "B", "K", "D", "A", "E"},
            {"5", "A", "D", "B", "C", "E", "K"},
            {"6", "K", "E", "D", "C", "B", "A"}
        };
        String[][] companyPreferences5 = {
            {"A", "2", "5", "1", "3", "6", "4"},
            {"B", "1", "2", "5", "3", "4", "6"},
            {"C", "6", "3", "5", "2", "1", "4"},
            {"D", "5", "1", "3", "2", "4", "6"},
            {"E", "2", "3", "6", "4", "1", "5"},
            {"K", "3", "4", "2", "5", "6", "1"}
        };

        String assignment5 = assignJobs(programmerPreferences5, companyPreferences5);
            System.out.println(assignment5);
       System.out.println((isStableMarriage(assignment5, programmerPreferences5, companyPreferences5)));
    }
}
    
// to make a test to run you make 2 2d arrays of preferences one for company and one for programmers that must have equal number of people
// and write where each array the first string is the name of the programmer or company ex: A or 1(can only be a single char) 
// then right their preferences where the most preferred is on the left and least on the right(make sure for the programmers you still right them as strings)  

// to use our test method you need to store the assignJobs result into a string and run it through our isStableMarriage function with the preferences and it will return true if its done correctly

// our algorithm is correct because it assigns a programmer to a company then loops through every time there is a new programmer to check if there are better matches to be had.
// and if there is a better match it will try to re match the newly unmatch programmer to another company.

// the worst case effeciency is when the Method's matches would all have to change every time a new programmer is inputed so it would go through the loop as much as it could so it would be O(n^2)