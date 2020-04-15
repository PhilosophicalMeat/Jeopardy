import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class HellIsReal {
    //SCANNER FOR PLAYER INPUT
    public static Scanner input = new Scanner(System.in);
    //PLAYER VARIABLE DECLARATION (max 5 players)
    public static int[] playerScores;
    public static int totalPlayers=0, turnNumber=1;
    //TOPIC VARIABLE AND ARRAY DECLARATION
    public static String[] topicNames = new String[6], playerNames;
    public static String[][][] boardArray = new String[6][5][4];//fourth value is boolean stating whether or not the question has been selected

    //MAIN METHOD
    public static void main(String[] args) throws FileNotFoundException{
        GetPlayerData();
        //the good shit
        Scanner fileReader = new Scanner(new File("C:\\Desktop\\making sarah's version more user-friendly\\src\\JeopardyTestFile1"));
        GetJeopardyFile(fileReader);
        GetAvailableQuestions();

    }

    //METHOD FOR GETTING TOTAL PLAYERS AND THEIR NAMES
    private static void GetPlayerData(){
        System.out.println("Welcome to Jeopardy!\nEnter the number of players");
        totalPlayers = input.nextInt();
        playerScores = new int[totalPlayers];
        playerNames = new String[totalPlayers];
        System.out.println("");
        playerNames[0] = input.nextLine(); //this needs to be here for some reason or else the input skips the first value in the array. i have no idea why
        for (int i = 0; i < playerNames.length; i++) {
            System.out.println("please enter player "+(i+1)+"'s name");
            playerNames[i] = input.nextLine();
            System.out.println("Confirmed: player "+(i+1)+" name is now "+playerNames[i]);
        }
    }
    //METHOD FOR ACCESSING THE JEOPARDY TEXT FILE
    private static void GetJeopardyFile(Scanner fileReader){
        System.out.println("Available questions:");
        for (int i = 0; i < boardArray.length; i++) {
            String tempLine = fileReader.nextLine();
            try{
                String[] tempArray = tempLine.split(",");
                topicNames[i] = tempArray[0].substring(1,tempArray[0].length()-1);
/*
                System.out.println(topicNames[i]);
*/
                for (int j = 0; j < boardArray[i].length; j++) {
                    boardArray[i][j] = tempArray[j+1].split("/");
/*
                    System.out.println(boardArray[i][j][0]+"    "+boardArray[i][j][1]+"     "+boardArray[i][j][2]+"     "+boardArray[i][j][3]);
*/
                }
            }
            catch(Exception e){
                System.out.println("an error occured somewhere in the process");
            }
        }
    }

    //METHOD FOR DISPLAYING PLAYER SCORES
    private static void viewPlayerScores(){
        System.out.println("____Current Scores____");
        for (int i = 0; i < playerScores.length; i++) {
            System.out.println(playerNames[i]+"'s score: "+playerScores[i]);
        }
        GetAvailableQuestions();
    }

    //METHOD FOR DISPLAYING AVAILABLE QUESTIONS
    private static void GetAvailableQuestions(){
        System.out.println("____Available Questions____");
        for (int i = 0; i < boardArray.length; i++) {
            System.out.print(topicNames[i]+"    |");
            for (int j = 0; j <boardArray[i].length ; j++) {
                if(boardArray[i][j][3].equals("false")){
                    System.out.print("["+(j+1)+"]"+boardArray[i][j][0]+"   |");
                }
            }
            System.out.println();
        }
        GetPlayerRequest();
    }
    //METHOD FOR GETTING PLAYER REQUEST
    private static void GetPlayerRequest(){
        int CurrentPlayerIndex = (turnNumber%totalPlayers)-1;
        System.out.println(playerNames[CurrentPlayerIndex]+", please select a question (topicName,questionPointValue)");
        String[]playerRequest = input.nextLine().split(",");
        //sending the value through a for loop to indentify it
        for (int i = 0; i < topicNames.length; i++) {
            if(topicNames[i].equals(playerRequest[0])){
                for (int j = 0; j < boardArray[i].length; j++) {
                    if(boardArray[i][j][0].equals(playerRequest[1])){
                        if(boardArray[i][j][3].equals("false")){
                            System.out.println(boardArray[i][j][1]+"\n What is your answer, player "+playerNames[CurrentPlayerIndex]+"?");
                            String playerTempAnswer = input.nextLine();
                            if(playerTempAnswer.equals(boardArray[i][j][2])){
                                System.out.println("Correct! "+playerNames[CurrentPlayerIndex]+" gains "+boardArray[i][j][0]+" points!\n");
                                playerScores[CurrentPlayerIndex]+= Integer.parseInt(boardArray[i][j][0]);
                                boardArray[i][j][3] = "true";
                                turnNumber+=1;
                            }
                            else{
                                System.out.println("Incorrect!");
                                boardArray[i][j][3] = "true";
                                turnNumber += 1;
                            }
                        }
                    }
                }
            }
        }
        viewPlayerScores();
    }

}
