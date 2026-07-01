import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.1 -- 29-07-2017
 */
public class MyDodo extends Dodo
{
    /* ATTRIBUTE DECLARATIONS: */
    private int myNrOfStepsTaken;
    private int myNrOfEggsHatched;
    private int myScore;
    
    public MyDodo() {
        super( EAST );
        /* INITIALISATION OF ATTRIBUTES: */
        myNrOfStepsTaken = 0;
    }

    /* METHODS OF THE CLASS: */

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
   public void move() {
    if ( myNrOfStepsTaken > 0 && canMove() ) {
        step();
        myNrOfStepsTaken--;
        
        if (!getWorld().getObjects(Scoreboard.class).isEmpty()) {
            Scoreboard sb = (Scoreboard) getWorld().getObjects(Scoreboard.class).get(0);
            sb.getScore(myNrOfStepsTaken, myScore); 
        }
    } 
}


    /**
     * Test if Dodo can move forward, 
     * i.e. there are no obstructions or end of world in the cell in front of her.
     * 
     * <p> Initial:   Dodo is somewhere in the world
     * <p> Final:     Same as initial situation
     * 
     * @return  boolean true if Dodo can move (thus, no obstructions ahead)
     *                  false if Dodo can't move
     *                      there is an obstruction or end of world ahead
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }

    /**
    * Places all the Egg objects in the world in a list.
    * 
    * @return List of Egg objects in the world
    */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }
    
    public List<Integer> createListOfNumbers() {
        return new ArrayList<> (Arrays.asList( 2, 43, 7, -5, 12, 7 ));
    }
    

    /**
     * Method for praciticing with lists.
     */
    public void practiceWithLists( ){
        List<Integer> listOfNumbers = createListOfNumbers();
        System.out.println("First element: " + listOfNumbers.get(1) ); 
    }

    public void practiceWithListsOfSurpriseEgss( ){
        List<SurpriseEgg>  listOfEgss = SurpriseEgg.generateListOfSurpriseEggs( 12, getWorld() );
    }
    
    public void mimiRight(){
        turnRight();
    }
    public void faceEast(){
        if (getDirection() != EAST) {
            setDirection(EAST);
        }
    }

    public void turn180(){
        turnRight();
        turnRight();
    }
       public void climbOverFence() {
    if (getDirection() == EAST) {        
        turnLeft();
        move();
        turnRight();
        move();
        move();
        turnRight();
        move();
        turnLeft();
    } else if (getDirection() == WEST) { 
        turnRight();
        move();
        turnLeft();
        move();
        move();
        turnLeft();
        move();
        turnRight();
    }
}

public void moveRandomly() {
    int myNrOfStepsTaken = Mauritius.MAXSTEPS;
    Scoreboard bord = (Scoreboard) getWorld().getObjects(Scoreboard.class).get(0);
    
    while (myNrOfStepsTaken > 0) {
        setDirection(randomDirection());
        
        while (borderAhead() || fenceAhead()) {
            setDirection(randomDirection());
        }
        
        move();
        myNrOfStepsTaken--;
        
        bord.getScore(myNrOfStepsTaken, 0);
    }
}
public void countEggsInRow(){
        int oldX = getX();
        int oldY = getY();
        int oldDir = getDirection();
        int eggCounter = 0;
        
        goBackToStartOfRowAndFaceBack();

        while(!borderAhead()){
            if (onEgg()){
                eggCounter++;
            
        }
        move();
        
        }
        
        goToLocation(oldX, oldY);
        setDirection(oldDir);
        
        showCompliment("Totaal aantal eieren: " + eggCounter);
}
public int countEggsInRowV2(){
    int eggCounter = 0;

    goBackToStartOfRowAndFaceBack();

    while(!borderAhead()){
        if (onEgg()){
            eggCounter++;
        }
        move();
    }

    return eggCounter;
}

    public double averageInRow(){
     double totalEgg = countEggsInWorld();
     return totalEgg / getWorld().getHeight();
    }

    public void stepOneCellBackwards(){
        turn180();
        move();
        turn180();
    }

    public void layTrailEgg(int distance){
        int nrStepsTaken = 1;             
        while ( nrStepsTaken < distance ) {  
            layEgg();
            move(); 
            nrStepsTaken++;                 
        }
        layEgg();
        
    }
    public double countEggsInWorld()  {
    int aantalRijen = getWorld().getHeight(); 
    int totaalAantalEieren = 0;
    int huidigeRij = 0;

    while (huidigeRij < aantalRijen) {
        goToLocation(0, huidigeRij);
        setDirection(EAST); 
        
        totaalAantalEieren = totaalAantalEieren + countEggsInRowV2(); 
        
        huidigeRij++;
    }

    goToLocation(0, 0);
    return totaalAantalEieren;
}
public void mostEggInRow() {
    int aantalRijen = getWorld().getHeight(); 
    
    int meesteEieren = -1; 
    int rijMetMeesteEieren = 0;
    int huidigeRij = 0; 

    while (huidigeRij < aantalRijen) {
        goToLocation(0, huidigeRij);
        setDirection(EAST); 
        
        int eierenInDezeRij = countEggsInRowV2(); 
        
        if (eierenInDezeRij > meesteEieren) {
            meesteEieren = eierenInDezeRij;
            rijMetMeesteEieren = huidigeRij;
        }
        
        huidigeRij++;
    }

    goToLocation(0, 0);
    System.out.println("De rij met de meeste eieren is rij: " + rijMetMeesteEieren);
}

    public void walkToEdgeAndLayEggOnNest(){
        while (!borderAhead()) {
        if (onNest() && canLayEgg()) {
            layEgg();
        }
        move();
    }
    if (onNest() && canLayEgg()) {
            layEgg();
        }
    }
    public void buildMonument() {
    int startY = getY();
    int startX = getX();
    int aantalRijen = getWorld().getHeight();
    int huidigeRij = startY;

    while (huidigeRij < aantalRijen && !borderAhead()) {
        goToLocation(startX, huidigeRij);
        setDirection(EAST);

        int eierenNodig = (huidigeRij - startY) + 1;
        int eierenGelegd = 0;

        while (eierenGelegd < eierenNodig) {
            layEgg();
            move();
            eierenGelegd++;
        }

        huidigeRij++;
    }
    layEgg();
}
public void buildPyramid() {
        int startX = getX();
        int startY = getY();
        int row = startY;
        while (row < getWorld().getHeight()) {
            int offset = row - startY;
            int startColumn = startX - offset;
            int endColumn = startX + offset;
            if (startColumn < 0 || endColumn >= getWorld().getWidth()) {
                break;
            }
            goToLocation(startColumn, row);
            setDirection(EAST);
            layTrailEgg(2 * offset + 1);
            row++;
        }
    }
public void buildStrongMonument(){
    int startX = getX();
    int startY = getY();
    int aantalRijen = getWorld().getHeight();
    int huidigeRij = startY;
    
    int eierenNodig = 1; 

    while (huidigeRij < aantalRijen&& !borderAhead()) {
        goToLocation(startX, huidigeRij);
        setDirection(EAST);

        int eierenGelegd = 0;

        while (eierenGelegd < eierenNodig) {
            layEgg();
            move();
            eierenGelegd++;
        }
        
        eierenNodig = eierenNodig * 2; 
        huidigeRij++;
    }
    layEgg();
}
    public void pickupGrainAndPrintCoordinates() {
    while (!borderAhead()) {
        if (onGrain()) {
            System.out.println("Graan op: (" + getX() + ", " + getY() + ")");
            pickUpGrain();
        }
        move();
    }
    // Laatste cel nog checken
    if (onGrain()) {
        System.out.println("Graan op: (" + getX() + ", " + getY() + ")");
        pickUpGrain();
    }
}
    public void eggTrailToNest(){
        while (!onNest()){
        if (eggAhead() || nestAhead()){
            move();
            if(onEgg()){
                pickUpEgg();
            }
        }
        else{
            while(!eggAhead() && !nestAhead()){
            turnRight();
        }
            }
           
    }   
    
}

    public void walkAroundFencedArea() {
    while (!onEgg() && !borderAhead()) {
        if (fenceAhead()) {
            turnLeft();
            move();
        } else {
            turnRight();
            move();
        }
    }
    turnRight();
}

    public void findNestInMaze(){
        while(!onNest()){
            if (fenceAhead()){
                turnLeft();
            }
            else{
                move();
                turnRight();
            }
        }
    }
    
    public void findNestInMazecompliment(){
         findNestInMaze();
         System.out.println("goedzo mimi");
    }
    
    public void wisslenVanWaarde(){
        int blauweEi =10;
        int goudeEi =2;
        int tijdelijkeEi =2;
        
        tijdelijkeEi = blauweEi;
        blauweEi = goudeEi;
        goudeEi = tijdelijkeEi;
        
        System.out.println("goude ei: "+ goudeEi + " blauwe ei: " + blauweEi);
    }
    
    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public int[] calculateDistance(int currentX, int currentY, int correctX, int correctY){
           int[] distance = {currentX - correctX, currentY - correctY};

           return distance;
            
    }
    public boolean validCoord(int coordX, int coordY){
        int maxX = getWorld().getWidth();
        int maxY = getWorld().getHeight();
        if(coordX < 0 || coordY < 0){
            showError("invalide coordinaten"); 
            return false;
        }
        if(coordX > maxX || coordY > maxY){
            showError("invalide coordinatenw"); 
            return false;
        }
        return true;
    }
    public void goToLocation(int coordX, int coordY){
        int currentX = getX();
        int currentY = getY();
        
        int correctX = coordX;
        int correctY = coordY;
        
        if(!validCoord(coordX,coordY)){
        return;
        }
        while(getX() < coordX){
            setDirection(EAST);
            move();
            
        }
        while(getX() > coordX){
            setDirection(WEST);
            move();
            
        }
        while(getY() < coordY){
            setDirection(SOUTH);
            move();
            
        }
        while(getY() > coordY){
            setDirection(NORTH);
            move();
            
        }
    
    }
    
    public void gotoEgg() {
    while (!onEgg()) {
        move();
        if(borderAhead()){
            break;
        }
    }
}
    
    public void goBackToStartOfRowAndFaceBack() {
        turn180();
        walkToWorldEdge();
        turn180();
        
    }
    
    public void walkToWorldEdgeClimbingOverFences(){
        while(!borderAhead()){
            move();
            if(fenceAhead()){
                climbOverFence();
            }
            if (onNest() && canLayEgg()) {
            layEgg();
        }
        }
    }

    public boolean grainAhead() {
    move();           
    
    if (onGrain()) {
        stepOneCellBackwards();
        return true;
    } else {
        stepOneCellBackwards();
        return false;
    }
}
    
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdge( ){
        while( ! borderAhead() ){
            move();
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
     if( onEgg() ){
         return false;
        }
        else{
            return true;
    }
    }  
    
 public void pickupNearestEggInList() {
    List<Egg> eggs = getListOfEggsInWorld();
    
    Egg nearestEgg = eggs.get(0);
    
    int eiTeller = 0;
    while (eiTeller < eggs.size()) {
        Egg currentEgg = eggs.get(eiTeller);
        
        int huidigeAfstand = Math.abs(currentEgg.getX() - getX()) + Math.abs(currentEgg.getY() - getY());
        int kortsteAfstand = Math.abs(nearestEgg.getX() - getX()) + Math.abs(nearestEgg.getY() - getY());
        
        if (huidigeAfstand < kortsteAfstand) {
            nearestEgg = currentEgg;
        }
        
        eiTeller++;
    }
    
    goToLocation(nearestEgg.getX(), nearestEgg.getY());
    
    if (onEgg()) {
        pickUpEgg();
    }
}
public void dodoRace() {
    myNrOfStepsTaken = Mauritius.MAXSTEPS;     
    
    while (myNrOfStepsTaken > 0) {
        List<Egg> eggs = getListOfEggsInWorld();
        
        if (eggs.isEmpty()) {
            break;
        }
        
        Egg dichtstbijzijndeEi = eggs.get(0);
        int kortsteAfstand = Math.abs(dichtstbijzijndeEi.getX() - getX()) + Math.abs(dichtstbijzijndeEi.getY() - getY());
        
        int i = 1;
        while (i < eggs.size()) {
            Egg checkEi = eggs.get(i);
            int afstand = Math.abs(checkEi.getX() - getX()) + Math.abs(checkEi.getY() - getY());
            
            if (afstand < kortsteAfstand) {
                kortsteAfstand = afstand;
                dichtstbijzijndeEi = checkEi;
            }
            i++;
        }
        
        if (kortsteAfstand > myNrOfStepsTaken) {
            break;
        }
        
        goToLocation(dichtstbijzijndeEi.getX(), dichtstbijzijndeEi.getY());
        
        if (onEgg()) {
            pickUpEgg();
            
            if (dichtstbijzijndeEi.getClass() == GoldenEgg.class) {
                myScore += 5;
            } else {
                myScore += 1;
            }
            
            if (!getWorld().getObjects(Scoreboard.class).isEmpty()) {
                Scoreboard sb = (Scoreboard) getWorld().getObjects(Scoreboard.class).get(0);
                sb.getScore(myNrOfStepsTaken, myScore);
            }
        }
    }
}
}
    