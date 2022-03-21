
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

//Player class contains info on each player
class Player {

    private static int NO = 0;

    //Define attributes of the class
    private String name;
    private int playerNO;
    private int[] scores = new int[3];
    private int totalScore;

    //default constructor
    public Player() {
        ++NO;
        playerNO = NO;

        name = "Player" + NO;
    }

    //Other constructor
    public Player(String name) {
        ++NO;
        playerNO = NO;

        this.name = name;
    }

    //Mutator methods
    public void setName(String name) {
        this.name = name;
    }

    public void setScores(int index, int score) {
        scores[index] = score;
    }

    //Accessor methods
    public String getName() {
        return name;
    }

    public int getScore(int index) {
        return scores[index];
    }

    public int getPlayerNO() {
        return playerNO;
    }

    //Calculates and reutrns the total score
    public int getTotalScore() {
        totalScore = scores[0] + scores[1] + scores[2];
        return totalScore;
    }

}

class ArcheryGame {

    private static Random rand;
    private static String[][] tableSummary;

    //Returns a random score from 0 to 10
    public static int generateScore() {
        return rand.nextInt(11);
    }

    //Prints the result
    public static void printInfo(String[][] tableSummary) {
        System.out.printf("%-10s" + "%-10s" + "%-10s" + "%-10s" + "%-10s" + "%-10s%n",
                "Player NO", "Name", "1st Game", "2nd Game", "3rd Game", "Total Score");
        for (String[] x : tableSummary) {
            for (String y : x) {
                System.out.printf("%-10s", y);
            }
            System.out.println("");
        }

    }

    //Prints the final results
    //Ranking of players
    public static void finalResults(ArrayList<Player> alist) {
        System.out.printf("%s" + "%s%n", "Champion: ", alist.get(0).getName());
        System.out.printf("%s" + "%s%n", "1st Runner up: ", alist.get(1).getName());
        System.out.printf("%s" + "%s%n", "2nd Runner up: ", alist.get(2).getName());
    }

    //Orders the players according to their score
    //Highest to lowest
    public static ArrayList<Player> finalScore(ArrayList<Player> alist) {
        //Instantiate new ArrayList
        ArrayList<Player> playerList = new ArrayList<>();

        //Deep copy alist into ArrayList
        for(int i = 0; i < alist.size(); i++){
            playerList.add(alist.get(i));
        }
        
        //Bubble sort (descending order) copied ArrayList
        for (int i = 0; i < playerList.size() - 1; i++) {
            for (int j = 0; j < playerList.size() - i - 1; j++) {
                if (playerList.get(j).getTotalScore() < playerList.get(j + 1).getTotalScore()) {
                    Player temp = playerList.get(j);
                    playerList.set(j, playerList.get(j + 1));
                    playerList.set(j + 1, temp);
                }
            }
        }
        
        //Return sorted ArrayList
        return playerList;
    }

    public static void main(String[] args) {

        //Instantiate random object
        rand = new Random();

        //Instantiate scanner object
        Scanner scanner = new Scanner(System.in);

        //Create ArrayList. This ArrayList stores all the players
        ArrayList<Player> playerList = new ArrayList<>();

        //Ask user how many players are there
        System.out.print("How many players are there? Enter a number: ");
        int playerCount = scanner.nextInt();
        scanner.nextLine();

        //Create player objects and store in playerList
        for (int i = 0; i < playerCount; i++) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            if (name.equals("")) {
                playerList.add(new Player());
            } else {
                playerList.add(new Player(name));
            }
        }

        System.out.println("----------" + "----------" + "----------");

        //Create 2D Array. This 2D array is used to display information
        tableSummary = new String[playerCount][6];

        //Insert player number into column 0
        for (int i = 0; i < playerList.size(); i++) {
            tableSummary[i][0] = String.valueOf(playerList.get(i).getPlayerNO());
        }

        //We insert the player names into column01
        for (int i = 0; i < playerList.size(); i++) {
            tableSummary[i][1] = playerList.get(i).getName();
        }

        //We have a total of 3 games
        for (int g = 0; g < 3; g++) {
            //We generate the score for each player in each game 
            for (int i = 0; i < playerList.size(); i++) {
                int score = generateScore();
                playerList.get(i).setScores(g, score);
            }

            //Insert game 1 scores into column02
            for (int i = 0; i < playerList.size(); i++) {
                tableSummary[i][g + 2] = String.valueOf(playerList.get(i).getScore(g));
            }
        }

        //Add total score into tableSummary
        for (int i = 0; i < playerList.size(); i++) {
            tableSummary[i][5] = String.valueOf(playerList.get(i).getTotalScore());
        }

        //Print player info and scores
        printInfo(tableSummary);
        System.out.println("----------" + "----------" + "----------");

        //Final results
        //We sort the final scores in descending order
        //And print out 1st,2nd,3rd
        finalResults(finalScore(playerList));
    }

}
