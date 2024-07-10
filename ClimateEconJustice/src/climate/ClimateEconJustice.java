package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        String[] input = inputLine.split(","); //takes data from line and breaks up it to place it into a string array. 

        String name = input[2]; //access the above array to find the name

        StateNode State = new StateNode(name, null, null); // node for (new) state

        if (firstState == null) { // case that there isn't inital state to set ptr = to
            firstState = State; 
        } 
        else {
            StateNode ptrToPrevLast = firstState; // creation of pointer

            while (ptrToPrevLast != null) { //condition to stop iteration through State linked list
                
                if (ptrToPrevLast.getName().equals(name)) { //checks to see the state already exists
                    break; // if true then no need to iterate through the loop. its stupid lol 
                } 
                else { 
                    if (ptrToPrevLast.getNext() == null) { // checks to see if its at the end of the linked list, if so, add the state
                        ptrToPrevLast.next = State;
                    }
                    else { 
                        ptrToPrevLast = ptrToPrevLast.getNext(); // if not, then iterate to the next node
                    }
                }

            }
            
        }


    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        String[] input = inputLine.split(",");

        String countyname = input[1];
        String statename = input[2];

        StateNode ptrStateNode = firstState;

        while (ptrStateNode != null) {
            if (ptrStateNode.getName().equals(statename)) {
                CountyNode County = new CountyNode(countyname, null, null);

                if (ptrStateNode.getDown() == null) { // checking to see if there is an existing county or not
                    ptrStateNode.down = County; //if there isn't, then the first county is the new county. 
                    break;
                }
                else { 
                    CountyNode ptrCounty = ptrStateNode.getDown(); // create ptr for county linked list
                    CountyNode prev = null;
                    boolean exists = false; 

                    while (ptrCounty != null) { 
                        if (ptrCounty.getName().equals(countyname)) {
                            exists = true; 
                        } 
                            prev = ptrCounty;
                            ptrCounty = ptrCounty.getNext();
                    }
                    if (ptrCounty == null && exists == false) {
                        prev.setNext(County); 
                    }
                    break;
                    } 
                }
            else { 
                ptrStateNode = ptrStateNode.getNext(); //iterates through state nodes
            }
        }


    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        String[] input = inputLine.split(","); // indexs the array with stuff from the inputline

        String StateName = input[2];
        String CountyName = input[1];
        String CommunityName = input[0]; // info from the input array 
        double AfricanAmerican = Double.parseDouble(input[3]);
        double Native = Double.parseDouble(input[4]);
        double Asian = Double.parseDouble(input[5]);
        double White = Double.parseDouble(input[8]);
        double Hispanic = Double.parseDouble(input[9]);
        String Disadvantaged = input[19];
        double PMLevel = Double.parseDouble(input[49]);
        double ChanceOfFlood = Double.parseDouble(input[37]);
        double prctPovertyLine = Double.parseDouble(input[121]);
        
        Data data = new Data(AfricanAmerican, Native, Asian, White, Hispanic, Disadvantaged, PMLevel, ChanceOfFlood, prctPovertyLine);

        StateNode ptrState = firstState;

        while (ptrState != null) {
            if (ptrState.getName().equals(StateName)) {
                
                CountyNode ptrCounty = ptrState.getDown();

                while (ptrCounty != null) {
                    if (ptrCounty.getName().equals(CountyName)) {
                        
                        CommunityNode community = new CommunityNode(CommunityName, null, data);
                        if (ptrCounty.getDown() == null) { 
                            ptrCounty.down = community;
                            break;
                        }
                        else {

                            CommunityNode ptrCommunity = ptrCounty.getDown();
                            boolean exists = false;
                            CommunityNode prev = null;

                            while (ptrCommunity != null) {
                                if (ptrCommunity.getName().equals(CommunityName)) {
                                    exists = true;
                                    break;
                                }
                                else {
                                prev = ptrCommunity;
                                ptrCommunity = ptrCommunity.getNext();
                                }
                            }
                            if (ptrCommunity == null && exists == false) {
                                prev.next = community;
                            }
                        }



                    break;
                    }
                    else {
                        ptrCounty = ptrCounty.getNext();
                    }
                }
                break;
            }
            else {
                ptrState = ptrState.getNext();
            }
        }
        
        


    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities(double userPrcntage, String race) {
        int numOfRDC = 0;
    
        for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) {
            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {
                    if (communityptr.getInfo().getAdvantageStatus().equals("True")) {
                        if (race.equals("African American") && communityptr.getInfo().getPrcntAfricanAmerican() * 100 >= userPrcntage) {
                            numOfRDC++;
                        }
                        if (race.equals("Native American") && communityptr.getInfo().getPrcntNative() * 100 >= userPrcntage) {
                            numOfRDC++;
                        }
                        if (race.equals("Asian American") && communityptr.getInfo().getPrcntAsian() * 100 >= userPrcntage) {
                            numOfRDC++;
                        }
                        if (race.equals("White American") && communityptr.getInfo().getPrcntWhite() * 100 >= userPrcntage) {
                            numOfRDC++;
                        }
                        if (race.equals("Hispanic American") && communityptr.getInfo().getPrcntHispanic() * 100 >= userPrcntage) {
                            numOfRDC++;
                        }
                    }
                }
            }
        }
    
        return numOfRDC;
    }
    

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        int numOfRnDC = 0;
    
        for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) {
            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {
                    if (communityptr.getInfo().getAdvantageStatus().equals("False")) {
                        if (race.equals("African American") && communityptr.getInfo().getPrcntAfricanAmerican() * 100 >= userPrcntage) {
                            numOfRnDC++;
                        }
                        if (race.equals("Native American") && communityptr.getInfo().getPrcntNative() * 100 >= userPrcntage) {
                            numOfRnDC++;
                        }
                        if (race.equals("Asian American") && communityptr.getInfo().getPrcntAsian() * 100 >= userPrcntage) {
                            numOfRnDC++;
                        }
                        if (race.equals("White American") && communityptr.getInfo().getPrcntWhite() * 100 >= userPrcntage) {
                            numOfRnDC++;
                        }
                        if (race.equals("Hispanic American") && communityptr.getInfo().getPrcntHispanic() * 100 >= userPrcntage) {
                            numOfRnDC++;
                        }
                    }
                }
            }
        }
    
        return numOfRnDC; // replace this line
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        ArrayList <StateNode> StatesWithExceedingPMLevels = new ArrayList<>();

        for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) {
            boolean existsinArray = false; 
            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                if (existsinArray == true) {
                    break;
                }
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {
                    if (communityptr.getInfo().getPMlevel() >= PMlevel) {
                        if (StatesWithExceedingPMLevels.contains(stateptr)) {
                            existsinArray = true;
                            break;
                        }
                        else {
                            StatesWithExceedingPMLevels.add(stateptr);
                        }
                    }
                }
            }
        }
        return StatesWithExceedingPMLevels; // replace this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        int countOfComm = 0; 

        for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) {
            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {
                    if (communityptr.getInfo().getChanceOfFlood() >= userPercntage) {
                        countOfComm++;
                    }
                }
            }
        }
        return countOfComm; // replace this line
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        ArrayList<CommunityNode> LIC = new ArrayList<>(10);
        double lowestPercentage = 0; 
        int lowestPercentageindex = 0;
        int count = 0;

        for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) { // iterates through state linked list
            
            if (stateptr.getName().equals(stateName)) { // condition to find the target state 

            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) { //iterations to check every community in every county in the target state
                    if (count < 10) {
                        LIC.add(communityptr); //add the first 10 communities 
                        count++;
                    }
                    
                    else{
                        lowestPercentage =LIC.get(0).getInfo().getPercentPovertyLine();
                        lowestPercentageindex = 0; 

                        for (int i = 0; i < 10; i++) { //checks to see where the lowest poverty percentage is in the array list. 
                            if (LIC.get(i).getInfo().getPercentPovertyLine() < lowestPercentage) { // iterates through the array to find the lowest element and its index
                                lowestPercentage = LIC.get(i).getInfo().getPercentPovertyLine();
                                lowestPercentageindex = i;
                            }
                        }

                        if (communityptr.getInfo().getPercentPovertyLine() > lowestPercentage) { // checks to see if the community ptr's percentage is greater than the lowest percentage in the array list. 
                            LIC.set(lowestPercentageindex, communityptr);
                        }
                    }
                }


            }
            break; 
            } 
        }  
            
        return LIC; // replace this line 
        }
        
    }

    
