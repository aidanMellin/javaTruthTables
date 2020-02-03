// Packages to import

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Okay so basically I want to make an auto visual representation of truth Tables (just for practice with Java)...
 *
 * The end result will have either 2 or 3 variables (p,q,[r]) and will need to be able to break the various constructors
 *  / antecedents into their respective columns, whilst also solving whether the statements are true or false.
 *
 * The easiest way to do it (at least my current thought, that is), is to make 2 or 3 for loops dependent on how many
 * variables there are.
 *
 * if there are 2 there will be 4 different possible states for the variables (0,0),(0,1),(1,0), and (1,1)
 * while if there is a 3rd variable added that jumps up to 8
 *
 */

public class truthExcelRead {

    // frame
    JFrame f;
    // Table
    JTable j;


    // Constructor
    truthExcelRead(int var, String[] args, boolean saveExcel)
    {
        // Frame initiallization
        f = new JFrame();

        // Frame Title
        f.setTitle("Truth Table");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Data to be displayed in the JTable
        String[][] data = new String[0][];
        String[] columnNames = new String[0];

        //Determines the User Input Test Cases entered via the Command Line (length and actual args themselves)
        int testCount = args.length;
        String[] testCases = new String[testCount];
        for(int i=0; i<testCount; i++){
            testCases[i] = args[i];
        }

        // Main chunk of the code that is dependent on how many variables are in use (P,Q) or (P,Q,R)
        if (var==2){
            columnNames = new String[2+testCount];
            columnNames[0] = "P";
            columnNames[1] = "Q";

            for(int i = 0; i<testCount; i++){
                columnNames[i+2] = testCases[i];
            }

            data = new String[4][2+testCount];
            data[0][0] = "0";
            data[0][1] = "0";
            data[1][0] = "0";
            data[1][1] = "1";

            data[2][0] = "1";
            data[2][1] = "0";
            data[3][0] = "1";
            data[3][1] = "1";

            boolean p = false; //P boolean
            boolean q = false; //Q boolean
            int dataPos = 0; // Int that increases in order to change the values in the respective vertical column cells
            int maxCount = testCases.length; //Max number of test cases that are pulled from Sys.args

            for(int currCol = 0; currCol < maxCount; currCol++) {
                for (int i = 0; i < 2; i++) { //P Cases
                    for (int j = 0; j < 2; j++) {
                        boolean caseOutput = false;
                        boolean negP = false;
                        boolean negQ = false;

                        if(testCases[currCol].contains("(!P)")){ //Checks to see if there is negation P, if yes, switch value
                            p = !p;
                            negP = true;
                        }
                        if(testCases[currCol].contains("(!Q)")){//Checks to see if there is negation Q, if yes, switch value
                            q = !q;
                            negQ = true;
                        }

                        if(testCases[currCol].contains("&&")){ //And cases
                            caseOutput = andCase(p,q);
                        }else if(testCases[currCol].contains("||")){ //Or cases
                            caseOutput = orCase(p,q);

//                        -------------------------HAVEN'T TESTED-------------------------------------------------------

                        } else if(testCases[currCol].contains("->")){ //Implication Cases
                            caseOutput = implies(p,q);

                        } else if(testCases[currCol].contains("<>")){ //Biconditional Cases
                            caseOutput = biconditional(p,q);
                        }

                        if(negP){ //If P was switched, switch it back
                            p = !p;
                            negP = false;
                        }
                        if(negQ){//If Q was switched, switch it back
                            q = !q;
                            negQ = false;
                        }

                        if(testCases[currCol].contains("![")){ //If there is a negation of the entire test case, flip output
                            caseOutput = !caseOutput;
                        }

                        data[dataPos][currCol+2] = String.valueOf(caseOutput); //Change the data to the matching boolean output
                        q = !q; //Switch q to hit both possible cases for true / false
                        dataPos += 1;
                    }
                    p = !p; //Switch P cases from 0 to 1 to fit all possibilities for true / false
                }
                dataPos = 0; //Reset the true / false for PQ so there is the correct alignment
            }
        }else if(var==3) {
            /*
             * P Q R
             * 0 0 0
             * 0 0 1
             * 0 1 0
             * 0 1 1
             * 1 0 0
             * 1 0 1
             * 1 1 0
             * 1 1 1
             */
            columnNames = new String[3+testCount];
            columnNames[0] = "P";
            columnNames[1] = "Q";
            columnNames[2] = "R";

            for(int i = 0; i<testCount; i++){
                columnNames[i+3] = testCases[i];
            }

            data = new String[8][3+testCount];
            data[0][0] = "0";
            data[0][1] = "0";
            data[0][2] = "0";

            data[1][0] = "0";
            data[1][1] = "0";
            data[1][2] = "1";

            data[2][0] = "0";
            data[2][1] = "1";
            data[2][2] = "0";

            data[3][0] = "0";
            data[3][1] = "1";
            data[3][2] = "1";

            data[4][0] = "1";
            data[4][1] = "0";
            data[4][2] = "0";

            data[5][0] = "1";
            data[5][1] = "0";
            data[5][2] = "1";

            data[6][0] = "1";
            data[6][1] = "1";
            data[6][2] = "0";

            data[7][0] = "1";
            data[7][1] = "1";
            data[7][2] = "1";


            boolean p = false; //P boolean
            boolean q = false; //Q boolean
            boolean r = false; //R booleans
            int dataPos = 0; // Int that increases in order to change the values in the respective vertical column cells
            int maxCount = testCases.length; //Max number of test cases that are pulled from Sys.args

            for(int currCol = 0; currCol < maxCount; currCol++) {
                for (int i = 0; i < 2; i++) { //P Cases
                    for (int j = 0; j < 2; j++) {
                        for(int k = 0; k < 2; k++){
                            boolean caseOutput = false;
                            boolean negP = false;
                            boolean negQ = false;
                            boolean negR = false;

                            Boolean[] theseVars = new Boolean[2];

                            if(testCases[currCol].contains("(!P)")){ //Checks to see if there is negation P, if yes, switch value
                                p = !p;
                                negP = true;
                            }
                            if(testCases[currCol].contains("(!Q)")){ //Checks to see if there is negation Q, if yes, switch value
                                q = !q;
                                negQ = true;
                            }
                            if(testCases[currCol].contains("(!R)")){
                                r = !r;
                                negR = true;
                            }

                            Boolean[] varsUsed  = useVar(testCases[currCol],p,q,r);

                            if(varsUsed[0] && varsUsed[1]){
//                                System.out.println("Using [P,Q] ["+varsUsed[0]+","+varsUsed[1]+"] for "+testCases[currCol]);
                                theseVars = new Boolean[]{p, q};
                            }else if(varsUsed[0] && varsUsed[2]){
//                                System.out.println("Using [P,R] ["+varsUsed[0]+","+varsUsed[1]+"] for "+testCases[currCol]);
                                theseVars = new Boolean[]{p, r};
                            }else if(varsUsed[1] && varsUsed[2]){
//                                System.out.println("Using [Q,R] ["+varsUsed[0]+","+varsUsed[1]+"] for "+testCases[currCol]);
                                theseVars = new Boolean[]{q,r};
                            }

                            if(testCases[currCol].contains("&&")) {
                                caseOutput = andCase(theseVars[0], theseVars[1]);
                            }else if(testCases[currCol].contains("||")){ //Or cases
                                caseOutput = orCase(theseVars[0], theseVars[1]);
//                        -------------------------HAVEN'T TESTED-------------------------------------------------------
                            } else if(testCases[currCol].contains("->")){ //Implication Cases
                                caseOutput = implies(theseVars[0], theseVars[1]);

                            } else if(testCases[currCol].contains("<>")){ //Biconditional Cases
                                caseOutput = biconditional(theseVars[0], theseVars[1]);
                            }

                            if(testCases[currCol].contains("![")){
                                caseOutput = !caseOutput;
                            }

                            if(negP){ //Switch P back if it was switched
                                p = !p;
                                negP = false;
                            }
                            if(negQ){ //Switch Q back if it was switched
                                q = !q;
                                negQ = false;
                            }
                            if(negR){ //Switch R back if it was switched
                                r = !r;
                                negR = false;
                            }

                            data[dataPos][currCol+3] = String.valueOf(caseOutput); //Change the data to the matching boolean output
                            r = !r; //Switch q to hit both possible cases for true / false
                            dataPos += 1;
                        }
                        q = !q;
                    }
                    p = !p;
                }
                dataPos = 0;
            }
        }

        // Initializing the JTable
        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);

        // Frame Size
        f.setSize(500, 200);

        // Frame Visible = true if saveExcel is false
        if(!saveExcel) {
            System.out.println("Not saving file, but opening separate Java window");
            f.setVisible(true);
        }else{
            f.setVisible(false);
        }

        /*
         * This section concatenates the args used (including P Q (R)) into a comma separated "list", to be read by the
         * CSV file
         * */
        String argsToString = "";
        if(var == 2){
            argsToString = "P,Q,";
        }else{
            argsToString = "P,Q,R,";
        }
        for(int i = 0; i < args.length; i++){
            if(i != args.length-1){
                argsToString += args[i]+",";
            }else{
                argsToString += args[i];
            }
        }


        /*
         * This section's purpose is to use the data array to work to build the CSV file, line by line, to completion
         * It will be built alongside the building of the actual CSV file so that the data can be input in "real time"
         * to make life easier instead of multiple variables and for loops
         * */
        if(saveExcel) {
            try {
                File myObj = new File("TruthTableDelimited.csv");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                Desktop desktop = Desktop.getDesktop();

                FileWriter myWriter = new FileWriter("TruthTableDelimited.csv");
                myWriter.write(argsToString);

                if (var == 2) {
                    //TODO later
                } else {
                    String currString = "";
                    for (String[] datum : data) {
                        for (int j = 0; j < datum.length; j++) {
                            if (j < datum.length - 1) {
                                currString += datum[j] + ",";
                            } else {
                                currString += datum[j];
                            }
                        }
                        myWriter.write("\n" + currString);
                        currString = "";
                    }
                }

                myWriter.close();
                System.out.println("Successfully wrote to the file.");
                desktop.open(new File("TruthTableDelimited.csv")); //Opens the file created automatically
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }



    }

    public static Boolean[] useVar(String thisCase, boolean p, boolean q, boolean r){

        Boolean[] result = new Boolean[]{false,false,false};

        if(thisCase.contains("P")){
            result[0] = true;
        }
        if(thisCase.contains("Q")){
            result[1] = true;
        }
        if(thisCase.contains("R")){
            result[2] = true;
        }

        return result;
    }

    public static boolean andCase(boolean p, boolean q){ return (p && q); }

    public static boolean orCase(boolean p, boolean q){ return (p || q); }

    public static boolean implies(boolean p, boolean q){ return (p==q) || (!p && q); }

    public static boolean biconditional(boolean p, boolean q){ return (p==q); }

    // Driver  method
    public static void main(String[] args) throws IOException {
        boolean saveToExcel;
        Scanner sc = new Scanner(System.in);

        System.out.println("Would you like to save this file to Excel? [yes / no]");
        String saveExcel = sc.next().toLowerCase();

        saveToExcel = saveExcel.contains("y"); //Save the file in CSV Excel form

        ArrayList argRead = new ArrayList<>();
        int numVars;
        File file = new File(args[0].toString());
        sc = new Scanner(file);

        String newArgs = sc.nextLine();

        //Convert the file into an ArrayList of flexible size (to be turned into String[] later)
        boolean go = true;
        while(go) {
            if (newArgs.contains(",")) {
                int indexComma = newArgs.indexOf(",");
                argRead.add(newArgs.substring(0, indexComma));
                newArgs = newArgs.substring(indexComma + 1);
            } else if (!newArgs.contains(",")) {
                argRead.add(newArgs);
                go = false;
            }
        }
//        System.out.println(argRead);

        //Determines how many variables are in use by reading the file for the existence of R
        //Assumes only the use of P,Q, or R
        if(newArgs.contains("R")){
            numVars = 3;
            argRead.remove(0);
            argRead.remove(0);
            argRead.remove(0);
//            System.out.println(argRead);
        }else{
            numVars = 2;
            argRead.remove(0);
            argRead.remove(0);
        }

        /*
        * This converts the argRead ArrayList into a String[] array, so that it can be read by the program without
        * having to really change the program that much
        * */
        String[] argSend = new String[argRead.size()];
        for(int i = 0; i<argRead.size(); i++){
            argSend[i] = (String) argRead.get(i);
        }

//        System.out.println("ARGS SEND "+ Arrays.toString(argSend));

        new truthExcelRead(numVars, argSend,saveToExcel);
    }
}
