package simpleGame.core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Describes the board on which Pawns can move.
 * It is of rectangular shape, and is made of squares.
 * One of the square is a "bonus" square: it allows pawns to be stronger.
 * @author Erwan Bousse
 *
 */
public class Board {

    /**
     * The number of squares on the x axis.
     */
    private int xSize;

    /**
     * The number of squares on the y axis.
     */
    private int ySize;

    /**
     * The Pawns that currently are on the board.
     */
    private ArrayList<Pawn> pawns;

    /**
     * The x position of the bonus square
     */
    private int xBonusSquare;

    /**
     * the y position of the bonus square
     */
    private int yBonusSquare;

    /**
     * An iterator pointing towards the current pawn that must play.
     */
    private Pawn currentPawn;



    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    
    /**
     * Constructs a board, with a number of pawns and a size.
     * The pawns are spread randomly.
     * The bonus square is selected randomly.
     * @param numberOfPawns The number of pawns.
     * @param sizeX The number of squares on the x axis.
     * @param sizeY The number of squares on the y axis.
     */
    public Board(int numberOfPawns, int sizeX, int sizeY, int xBonus, int yBonus) {
        Random random = new Random();
        this.xSize = sizeX;
        this.ySize = sizeY;
        this.xBonusSquare = xBonus;
        this.yBonusSquare = yBonus;
        this.pawns = new ArrayList<Pawn>();

        //Bug ici, on est obligé de tester si numberOfPawns != 0
        //Sinon on pourrait pas executer: pawns.get(0)
        if(numberOfPawns > 0)
        {
            for(int i = 0; i<numberOfPawns; i++)
            {
                Pawn pawn = new Pawn(Character.forDigit(i, 10),
                                     random.nextInt(xSize),random.nextInt(ySize),this);
                this.addPawn(pawn);
            }
            currentPawn = pawns.get(0);// can not set the 0 pawn as the current because there is no pawn in the list.
        }

    }

    /**
     * Finds the content of a square.
     * @param x The x axis value.
     * @param y The y axis value.
     * @return The pawn found, or null if no pawn.
     */
    public Pawn getSquareContent(int x, int y) {
        for (Pawn p : pawns) {
            if ((p.getX() == x) &&(p.getY() == y)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Removes a pawn from the board.
     * @param pawn The pawn to remove.
     */
    public void removePawn(Pawn pawn) {
        pawns.remove(pawn);
    }

    /**
     * Adds a pawn to the board.
     * @param pawn The pawn to add.
     */
    public void addPawn(Pawn pawn) {
        if (getSquareContent(pawn.getX(),
                             pawn.getY()) == null)

            this.pawns.add(pawn);


    }

    /**
     * Decides whether a square is bonus or not.
     * @param x The x axis value.
     * @param y The y axis value.
     * @return True if the square is bonus, false otherwise.
     */
    public boolean isBonusSquare(int x, int y) {
        return x==xBonusSquare && y==yBonusSquare;
    }


    /**
     * Finds the number of pawns on the board.
     * @return The number of pawns on the board.
     */
    public int numberOfPawns() {
        return pawns.size();
    }

    /**
     * Computes the maximum amount of golf that a Pawn has.
     * @return The maximum amount of golf that a Pawn has.
     */
    public int maxGold() {
        int max = 0;
        for (Pawn p : pawns) {
            max = Math.max(max, p.getGold());
        }
        return max;
    }

    /**
     * Picks the next pawn that is allowed to play.
     * @return The next pawn that is allowed to play.
     */
    public Pawn getNextPawn() {

        //code ajouté ici
        if(this.pawns.size()==0) //sinon division par 0
        {
            return null;
        }

        else if (pawns.size() == 1) {
            currentPawn = pawns.get(0);
            return pawns.get(0);
        }
        else {
            Pawn result = currentPawn;


            //**********
            currentPawn = this.pawns.get((this.pawns.indexOf(
                                              currentPawn)+1)%this.pawns.size());//bug here
            return result;
        }
    }

    /**
     * Computes the char that should be displayed to represent the square or its content.
     * @param x The x axis value.
     * @param y The y axis value.
     * @return # if bonus, . if empty, c if current Pawn, a number for a non-current Pawn
     */
    public char squareContentSprite(int x, int y) {
        char result;
        Pawn content = getSquareContent(x,y);
        if (content == null) {
            if (isBonusSquare(x, y)) {
                result = '#';
            } else
                result = '.';
        } else {
            if (content == currentPawn) {
                result = 'c';
            } else
                result = content.getLetter();
        }
        return result;
    }

    /**
     * Computes a String that represents the whole board.
     */
    public String toString() {
        String result = "";
        //bug here: int y= ySize; y>=0; et aussi int x = 0; x<xSize
        for (int y= ySize; y>0; y--) {
            for(int x = 1; x<=xSize; x++) {
                result += squareContentSprite(x,y);
            }
            result+="\n";
        }
        return result;
    }

    
    /**
     * Removes all the pawns.
     */
	public void removeAllPawns() {
		pawns.clear();
		currentPawn = null;
	}
}
