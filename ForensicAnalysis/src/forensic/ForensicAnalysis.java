package forensic;

/**
 * This class represents a forensic analysis system that manages DNA data using
 * BSTs.
 * Contains methods to create, read, update, delete, and flag profiles.
 * 
 * @author Kal Pandit
 */
public class ForensicAnalysis {

    private TreeNode treeRoot;            // BST's root
    private String firstUnknownSequence;
    private String secondUnknownSequence;

    public ForensicAnalysis () {
        treeRoot = null;
        firstUnknownSequence = null;
        secondUnknownSequence = null;
    }

    /**
     * Builds a simplified forensic analysis database as a BST and populates unknown sequences.
     * The input file is formatted as follows:
     * 1. one line containing the number of people in the database, say p
     * 2. one line containing first unknown sequence
     * 3. one line containing second unknown sequence
     * 2. for each person (p), this method:
     * - reads the person's name
     * - calls buildSingleProfile to return a single profile.
     * - calls insertPerson on the profile built to insert into BST.
     *      Use the BST insertion algorithm from class to insert.
     * 
     * DO NOT EDIT this method, IMPLEMENT buildSingleProfile and insertPerson.
     * 
     * @param filename the name of the file to read from
     */
    public void buildTree(String filename) {
        // DO NOT EDIT THIS CODE
        StdIn.setFile(filename); // DO NOT remove this line

        // Reads unknown sequences
        String sequence1 = StdIn.readLine();
        firstUnknownSequence = sequence1;
        String sequence2 = StdIn.readLine();
        secondUnknownSequence = sequence2;
        
        int numberOfPeople = Integer.parseInt(StdIn.readLine()); 

        for (int i = 0; i < numberOfPeople; i++) {
            // Reads name, count of STRs
            String fname = StdIn.readString();
            String lname = StdIn.readString();
            String fullName = lname + ", " + fname;
            // Calls buildSingleProfile to create
            Profile profileToAdd = createSingleProfile();
            // Calls insertPerson on that profile: inserts a key-value pair (name, profile)
            insertPerson(fullName, profileToAdd);
        }
    }

    /** 
     * Reads ONE profile from input file and returns a new Profile.
     * Do not add a StdIn.setFile statement, that is done for you in buildTree.
    */
    public Profile createSingleProfile() {

        int numOfSTRS = StdIn.readInt(); 
        STR[] strs = new STR[numOfSTRS];

        for (int i = 0; i < numOfSTRS; i++) {
            String strString = StdIn.readString();
            int occurences = StdIn.readInt(); 

            STR STR = new STR(strString, occurences);
            strs[i] = STR;
        } 
        Profile newProfile = new Profile(strs); 

        return newProfile; // update this line
    }


    /**
     * Inserts a node with a new (key, value) pair into
     * the binary search tree rooted at treeRoot.
     * 
     * Names are the keys, Profiles are the values.
     * USE the compareTo method on keys.
     * 
     * @param newProfile the profile to be inserted
     */
    public void insertPerson(String name, Profile newProfile) {
        TreeNode treenode = new TreeNode(name, newProfile, null , null); 

        if (treeRoot == null) { //check to see if the tree is empty. 
            treeRoot = treenode; //if so, the first profile is the treeroot
        }

        else { 
            TreeNode ptr = treeRoot;
            
            while (ptr != null) {

                if (name.compareTo(ptr.getName()) < 0) { //name is lexocongraphically wth its called less therefore its on the left
                    if (ptr.getLeft() == null) { //ptr has no left ptr 
                        ptr.setLeft(treenode); //set left ptr to node 
                        break; //break the loop 
                    }
                    else {
                        ptr = ptr.getLeft(); 
                    }
                }
                else {
                    if (ptr.getRight() == null) {
                        ptr.setRight(treenode);
                        break;
                    }
                    else {
                        ptr = ptr.getRight();
                    }
                }
            }
        }
        
    }

    /**
     * Finds the number of profiles in the BST whose interest status matches
     * isOfInterest.
     *
     * @param isOfInterest the search mode: whether we are searching for unmarked or
     *                     marked profiles. true if yes, false otherwise
     * @return the number of profiles according to the search mode marked
     */
    public int getMatchingProfileCount(boolean isOfInterest) {

        int countOfInterest = 0;
        return MatchingIteration(isOfInterest, treeRoot, countOfInterest);

    }

    private int MatchingIteration(boolean isOfInterest, TreeNode x, int countOfInterest) {
        if (x == null) {
            return countOfInterest;
        }
        else {
            if (x.getProfile().getMarkedStatus() == isOfInterest) {
                countOfInterest++;
            }
        }
        
        countOfInterest += MatchingIteration(isOfInterest, x.getLeft(), 0);
        countOfInterest += MatchingIteration(isOfInterest, x.getRight(), 0);

        return countOfInterest;

    }

    /**
     * Helper method that counts the # of STR occurrences in a sequence.
     * Provided method - DO NOT UPDATE.
     * 
     * @param sequence the sequence to search
     * @param STR      the STR to count occurrences of
     * @return the number of times STR appears in sequence
     */
    private int numberOfOccurrences(String sequence, String STR) {
        
        // DO NOT EDIT THIS CODE
        
        int repeats = 0;
        // STRs can't be greater than a sequence
        if (STR.length() > sequence.length())
            return 0;
        
            // indexOf returns the first index of STR in sequence, -1 if not found
        int lastOccurrence = sequence.indexOf(STR);
        
        while (lastOccurrence != -1) {
            repeats++;
            // Move start index beyond the last found occurrence
            lastOccurrence = sequence.indexOf(STR, lastOccurrence + STR.length());
        }
        return repeats;
    }

    /**
     * Traverses the BST at treeRoot to mark profiles if:
     * - For each STR in profile STRs: at least half of STR occurrences match (round
     * UP)
     * - If occurrences THROUGHOUT DNA (first + second sequence combined) matches
     * occurrences, add a match
     */
    public void flagProfilesOfInterest() {
        flagiteration(treeRoot); 
    }

    private void flagiteration(TreeNode x) {
            if (x == null) {
                return;
            }

            String sequence = firstUnknownSequence + secondUnknownSequence;
            int countofMatchingSTRS = 0;
            int countofProfileSTRS = 0;

            for (int i = 0; i < x.getProfile().getStrs().length; i++) {
                if (x.getProfile().getStrs()[i].getOccurrences() == numberOfOccurrences(sequence, x.getProfile().getStrs()[i].getStrString())) {
                    countofMatchingSTRS++;
                }
                countofProfileSTRS++;
                
            }

            if (countofMatchingSTRS >= (countofProfileSTRS + 1) / 2) {
                x.getProfile().setInterestStatus(true);
            }

            flagiteration(x.getLeft());
            flagiteration(x.getRight());
        }
    /**
     * Uses a level-order traversal to populate an array of unmarked Strings representing unmarked people's names.
     * - USE the getMatchingProfileCount method to get the resulting array length.
     * - USE the provided Queue class to investigate a node and enqueue its
     * neighbors.
     * 
     * @return the array of unmarked people
     */
    public String[] getUnmarkedPeople() {

        String[] UnmarkedPeople = new String[getMatchingProfileCount(false)];
        Queue<TreeNode> queue = new Queue <>();
        queue.enqueue(treeRoot);
        int keyNum = 0;

        while(!queue.isEmpty()) {
            TreeNode currentNode = queue.dequeue();

            if (!currentNode.getProfile().getMarkedStatus()) {
                UnmarkedPeople[keyNum] = currentNode.getName();
                keyNum++;
            }

            if (currentNode.getLeft() != null) {
                queue.enqueue(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                queue.enqueue(currentNode.getRight());
            }
        }

        return UnmarkedPeople; // update this line
    }

    /**
     * Removes a SINGLE node from the BST rooted at treeRoot, given a full name (Last, First)
     * This is similar to the BST delete we have seen in class.
     * 
     * If a profile containing fullName doesn't exist, do nothing.
     * You may assume that all names are distinct.
     * 
     * @param fullName the full name of the person to delete
     */
    public void removePerson(String fullName) {
        TreeNode parent = null;
    TreeNode current = treeRoot;

    while (current != null) {
        if (fullName.compareTo(current.getName()) == 0) {
        
            if (current.getLeft() == null && current.getRight() == null) {
                if (parent == null) {
                    treeRoot = null; 
                } else {
                    if (parent.getLeft() == current) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                }
                break; 
            }
            
            if (current.getLeft() == null) {
                if (parent == null) {
                    treeRoot = current.getRight();
                } else {
                    if (parent.getLeft() == current) {
                        parent.setLeft(current.getRight());
                    } else {
                        parent.setRight(current.getRight());
                    }
                }
                break; 
            } else if (current.getRight() == null) {
                if (parent == null) {
                    treeRoot = current.getLeft();
                } else {
                    if (parent.getLeft() == current) {
                        parent.setLeft(current.getLeft());
                    } else {
                        parent.setRight(current.getLeft());
                    }
                }
                break; 
            }
            
    
            TreeNode successorParent = current;
            TreeNode successor = current.getRight();

            while (successor.getLeft() != null) {
                successorParent = successor;
                successor = successor.getLeft();
            }

            if (successorParent != current) {
                successorParent.setLeft(successor.getRight());
                successor.setRight(current.getRight());
            }

            successor.setLeft(current.getLeft());

            if (parent == null) {
                treeRoot = successor;
            } else {
                if (parent.getLeft() == current) {
                    parent.setLeft(successor);
                } else {
                    parent.setRight(successor);
                }
            }
            
            break; 
        } else if (fullName.compareTo(current.getName()) < 0) {
            parent = current;
            current = current.getLeft();
        } else {
            parent = current;
            current = current.getRight();
        }
    }
}

    /**
     * Clean up the tree by using previously written methods to remove unmarked
     * profiles.
     * Requires the use of getUnmarkedPeople and removePerson.
     */
    public void cleanupTree() {
        String [] hitlist = getUnmarkedPeople(); 

        for (int i = 0; i < hitlist.length; i++) { 
            removePerson(hitlist[i]);
        }

    }

    /**
     * Gets the root of the binary search tree.
     *
     * @return The root of the binary search tree.
     */
    public TreeNode getTreeRoot() {
        return treeRoot;
    }

    /**
     * Sets the root of the binary search tree.
     *
     * @param newRoot The new root of the binary search tree.
     */
    public void setTreeRoot(TreeNode newRoot) {
        treeRoot = newRoot;
    }

    /**
     * Gets the first unknown sequence.
     * 
     * @return the first unknown sequence.
     */
    public String getFirstUnknownSequence() {
        return firstUnknownSequence;
    }

    /**
     * Sets the first unknown sequence.
     * 
     * @param newFirst the value to set.
     */
    public void setFirstUnknownSequence(String newFirst) {
        firstUnknownSequence = newFirst;
    }

    /**
     * Gets the second unknown sequence.
     * 
     * @return the second unknown sequence.
     */
    public String getSecondUnknownSequence() {
        return secondUnknownSequence;
    }

    /**
     * Sets the second unknown sequence.
     * 
     * @param newSecond the value to set.
     */
    public void setSecondUnknownSequence(String newSecond) {
        secondUnknownSequence = newSecond;
    }

}
