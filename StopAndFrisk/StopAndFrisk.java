import java.util.ArrayList;

/**
 * The StopAndFrisk class represents stop-and-frisk data, provided by
 * the New York Police Department (NYPD), that is used to compare
 * during when the policy was put in place and after the policy ended.
 * 
 * @author Tanvi Yamarthy
 * @author Vidushi Jindal
 */
public class StopAndFrisk {

    /*
     * The ArrayList keeps track of years that are loaded from CSV data file.
     * Each SFYear corresponds to 1 year of SFRecords. 
     * Each SFRecord corresponds to one stop and frisk occurrence.
     */ 
    private ArrayList<SFYear> database; 

    /*
     * Constructor creates and initializes the @database array
     * 
     * DO NOT update nor remove this constructor
     */
    public StopAndFrisk () {
        database = new ArrayList<>();
    }

    /*
     * Getter method for the database.
     * *** DO NOT REMOVE nor update this method ****
     */
    public ArrayList<SFYear> getDatabase() {
        return database;
    }

    /**
     * This method reads the records information from an input csv file and populates 
     * the database.
     * 
     * Each stop and frisk record is a line in the input csv file.
     * 
     * 1. Open file utilizing StdIn.setFile(csvFile)
     * 2. While the input still contains lines:
     *    - Read a record line (see assignment description on how to do this)
     *    - Create an object of type SFRecord containing the record information
     *    - If the record's year has already is present in the database:
     *        - Add the SFRecord to the year's records
     *    - If the record's year is not present in the database:
     *        - Create a new SFYear 
     *        - Add the SFRecord to the new SFYear
     *        - Add the new SFYear to the database ArrayList
     * 
     * @param csvFile
     */
    public void readFile ( String csvFile ) {

        // DO NOT remove these two lines
        StdIn.setFile(csvFile); // Opens the file
        StdIn.readLine();       // Reads and discards the header line

        while (!StdIn.isEmpty()) {
            String[] recordEntries = StdIn.readLine().split(",");
            int year = Integer.parseInt(recordEntries[0]); 
            String caseDescription = recordEntries[2];
            String gender = recordEntries[52];
            String race = recordEntries[66];
            String location = recordEntries[71];
            Boolean arrested = recordEntries[13].equals("Y");
            Boolean frisked = recordEntries[16].equals("Y");
           
            boolean YearinDatabase = false;

            for (SFYear SFYear : database) { //iterates through the database to see if there is already an existing year
                if (SFYear.getcurrentYear() == year) { // if true it creates and adds the object into the array.
                    SFRecord SFRecord = new SFRecord(caseDescription, arrested, frisked, gender, race, location );
                    SFYear.addRecord(SFRecord);
                    YearinDatabase = true;
                    break; // loop should end if it finds the correct year
                }
            }

            if (YearinDatabase == false) { //if it doesn't exist in the database already, it should create a new array list, create the object 
                SFYear SFYear = new SFYear(year);
                SFRecord SFRecord = new SFRecord(caseDescription, arrested, frisked, gender, race, location);
                SFYear.addRecord(SFRecord);
                database.add(SFYear);
            }
        }
    }

    /**
     * This method returns the stop and frisk records of a given year where 
     * the people that was stopped was of the specified race.
     * 
     * @param year we are only interested in the records of year.
     * @param race we are only interested in the records of stops of people of race. 
     * @return an ArrayList containing all stop and frisk records for people of the 
     * parameters race and year.
     */

    public ArrayList<SFRecord> populationStopped ( int year, String race ) {
        ArrayList<SFRecord> PopulationSFRecord = new ArrayList<>();

        for (SFYear SFYear : database) {
            if (SFYear.getcurrentYear() == year) {
                for (SFRecord SFRecord : SFYear.getRecordsForYear()) {
                    if (SFRecord.getRace().equals(race)) {
                        PopulationSFRecord.add(SFRecord);
                    }
                }
                break;
            }
            
        }

        return PopulationSFRecord;
    }

    /**
     * This method computes the percentage of records where the person was frisked and the
     * percentage of records where the person was arrested.
     * 
     * @param year we are only interested in the records of year.
     * @return the percent of the population that were frisked and the percent that
     *         were arrested.
     */
    public double[] friskedVSArrested ( int year ) {
        
        int numFrisked = 0; 
        int numArrested = 0;
        double numOfRecords = 0;
        double[] FriskedVSArrested = new double[2];
        
        for (SFYear SFYear : database) {
            if (SFYear.getcurrentYear() == year) {
                for (SFRecord SFRecord : SFYear.getRecordsForYear()) {
                    if (SFRecord.getArrested() == true) {
                        numArrested++;
                    }
                    if (SFRecord.getFrisked() == true) {
                        numFrisked++;
                    }
                    numOfRecords++;
                }
                FriskedVSArrested[0] = (numFrisked / numOfRecords) * 100;
                FriskedVSArrested[1] = (numArrested / numOfRecords) * 100;
            }
        }

        return FriskedVSArrested; // update the return value
    }

    /**
     * This method keeps track of the fraction of Black females, Black males,
     * White females and White males that were stopped for any reason.
     * Drawing out the exact table helps visualize the gender bias.
     * 
     * @param year we are only interested in the records of year.
     * @return a 2D array of percent of number of White and Black females
     *         versus the number of White and Black males.
     */
    public double[][] genderBias(int year) {
        double BlackPeople = 0;
        double WhitePeople = 0;
        double BlackMen = 0;
        double WhiteMen = 0;
        double BlackWomen = 0;
        double WhiteWomen = 0;
    
        for (SFYear SFYear : database) {
            if (SFYear.getcurrentYear() == year) {
                for (SFRecord SFRecord : SFYear.getRecordsForYear()) {
                    if (SFRecord.getRace().equals("B")) {
                        BlackPeople++;
                        if (SFRecord.getGender().equals("F")) {
                            BlackWomen++;
                        } else {
                            if (SFRecord.getGender().equals("M")) {
                                BlackMen++;    
                            } 
                           
                        }
                    } else if (SFRecord.getRace().equals("W")) {
                        WhitePeople++;
                        if (SFRecord.getGender().equals("F")) {
                            WhiteWomen++;
                        } else {
                            if (SFRecord.getGender().equals("M")) {
                            WhiteMen++;    
                            }
                        }
                    }
                }
            }
        }

        double[][] GenderPercentage = new double[2][3];

        GenderPercentage[0][0] = (BlackWomen / BlackPeople) * 0.5 * 100;
        GenderPercentage[1][0] = (BlackMen /  BlackPeople) * 0.5 * 100;
        GenderPercentage[0][1] = (WhiteWomen / WhitePeople) * 0.5 * 100;
        GenderPercentage[1][1] = (WhiteMen / WhitePeople) * 0.5 * 100;
        GenderPercentage[0][2] = GenderPercentage[0][0] + GenderPercentage[0][1];
        GenderPercentage[1][2] = GenderPercentage[1][0] + GenderPercentage[1][1];
    
        return GenderPercentage;
    }
    

    /**
     * This method checks to see if there has been increase or decrease 
     * in a certain crime from year 1 to year 2.
     * 
     * Expect year1 to preceed year2 or be equal.
     * 
     * @param crimeDescription
     * @param year1 first year to compare.
     * @param year2 second year to compare.
     * @return 
     */

    public double crimeIncrease ( String crimeDescription, int year1, int year2 ) {
        double year1crimecounter = 0;
        double year2crimecounter = 0;
        double Y1Records = 0;
        double Y2Records = 0;
        

       for (SFYear SFYear : database) {
        if (SFYear.getcurrentYear() == year1) {
            for (SFRecord SFRecord : SFYear.getRecordsForYear()) {
                Y1Records++;
                if (SFRecord.getDescription().indexOf(crimeDescription) != -1) {
                    year1crimecounter++;
                }
            }
        }
        if (SFYear.getcurrentYear() == year2) {
            for (SFRecord SFRecord : SFYear.getRecordsForYear()) {
                Y2Records++;
                if (SFRecord.getDescription().indexOf(crimeDescription) != -1) {
                    year2crimecounter++;
                }
            }
        }
       }
       double year1percentage = (year1crimecounter/Y1Records) * 100;
       double year2percentage = (year2crimecounter/Y2Records) * 100;
       
	return year2percentage - year1percentage; // update the return value

    }

    /**
     * This method outputs the NYC borough where the most amount of stops 
     * occurred in a given year. This method will mainly analyze the five 
     * following boroughs in New York City: Brooklyn, Manhattan, Bronx, 
     * Queens, and Staten Island.
     * 
     * @param year we are only interested in the records of year.
     * @return the borough with the greatest number of stops
     */
    public String mostCommonBorough ( int year ) {

        int Brooklyn = 0;
        int Manhattan = 0;
        int Bronx = 0; 
        int Queens = 0; 
        int StatenIsland = 0;

        for (SFYear SFYear : database) {
            if (SFYear.getcurrentYear() == year) {
                for (SFRecord SFRecord : SFYear.getRecordsForYear()) {
                    if (SFRecord.getLocation().equals("BROOKLYN")) {
                        Brooklyn++;
                    }
                    else if (SFRecord.getLocation().equals("MANHATTAN")) {
                        Manhattan++; 
                    }
                    else if (SFRecord.getLocation().equals("BRONX")) {
                        Bronx++;
                    }
                    else if (SFRecord.getLocation().equals("STATEN ISLAND")) {
                        StatenIsland++;
                    }
                    else if (SFRecord.getLocation().equals("QUEENS")) {
                        Queens++;
                    }
                }
            }
        }

        String[] borough = new String[5]; 
        borough[0] = "Brooklyn";
        borough[1] = "Manhattan";
        borough[2] = "Bronx";
        borough[3] = "Queens";
        borough[4] = "Staten Island";

        int[] counts = new int[5]; 
        counts[0] = Brooklyn;
        counts[1] = Manhattan;
        counts[2] = Bronx;
        counts[3] = Queens; 
        counts[4] = StatenIsland;

        String BoroughWithMostSF = "Null";
        int max = 0;

        for (int i = 0; i < borough.length; i++) {
            if (counts[i] > max) {
                max = counts[i];
                BoroughWithMostSF = borough[i];
            }
        } 
        return BoroughWithMostSF; // update the return value
    }

}
