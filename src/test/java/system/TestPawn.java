package system;

import junit.framework.TestCase;

//It is recommended to import Mockito statically so that the code looks clearer
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
// Eclipse might not find this one automatically:
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import simpleGame.core.Board;
import simpleGame.core.Direction;
import simpleGame.core.Pawn;
import simpleGame.exception.OutOfBoardException;

@RunWith(MockitoJUnitRunner.class) // This is required for mocks to work

/**
 * Test de Pawn en utilisant un Mock de Board
 * @see simpleGame.core.Pawn
 * @author Josian MARINIER
 * @author Leite NA
 */
public class TestPawn {
    Pawn p;
    Board board; //le mockito

    /**
     * This is the method which will be executed before each test.
     * We create an mock board and a Pawn.
     */
    @Before
    public void SimplePawn() {
        // Creating context
        board = mock(Board.class);
        p = new Pawn('P', 5, 6, board);
    }

    /**
     * Test for the method getX() using the Pawn exist.
     * @see simpleGame.core.Pawn#getX()
     * @input the created Pawn('P', 5, 6, board) in SimplePawn
     * @oracle The x of pawn must be 5
     * @passed Yes
     */
    @Test
    public void testGetX()  {
        assertTrue(p.getX() == 5);
    }

    /**
     * Test for the method getY() using the Pawn exist.
     * @see simpleGame.core.Pawn#getY()
     * @input the created Pawn('P', 5, 6, board) in SimplePawn
     * @oracle The y of pawn must be 6
     * @passed Yes
     */
    @Test
    public void testGetY() {
        assertTrue(p.getY() == 6);
    }

    /**
     * Test for the method getLetter() using the Pawn exist.
     * @see simpleGame.core.Pawn#getLetter()
     * @input the created Pawn('P', 5, 6, board) in SimplePawn
     * @oracle The letter of pawn must be P
     * @passed Yes
     */
    @Test
    public void testGetLetter() {
        assertTrue(p.getLetter() == 'P');
    }

    /**
     * Test for the method getGold() using the Pawn exist.
     * @see simpleGame.core.Pawn#getGold()
     * @input the created Pawn('P', 5, 6, board) in SimplePawn
     * @oracle The y of pawn must be 6
     * @passed Yes
     */
    @Test
    public void testGetGold()  {
        assertTrue(p.getGold() == 0);
    }

    /**
     * Test move avec appel a la methode attack
     * @throws Exception If the pawn moves out of the board, an OutOfBoardException will be thrown.
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @input mock board getYSize()=10 and getXSize()=10, a new pawn ('A', 5, 5, board), Direction=Down
     * @oracle With attacks and the message produced by the method move will contain "P attacks!"
     * @passed Yes
     */
    @Test
    public void testMove() throws Exception {
        //teste dans un board 10x10
        // rappel: p = new Pawn('r',5,6,board);
        Direction d = Direction.Down; //position y--
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        Pawn p1 = new Pawn('A', 5, 5, board);
        Mockito.when(board.getSquareContent(5, 5)).thenReturn(p1);
        //sans attaque
        assertTrue(!p.move(d).equals(""));
        assertTrue(p.move(d).contains("P attacks!"));
    }

    /**
     * Test move simple sans attack
     *
     * @throws Exception If the pawn moves out of the board, an OutOfBoardException will be thrown.
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @input mock board getYSize()=10 and getXSize()=10, a new pawn ('A', 6, 7, board), Direction=Up, Left, and Right
     * @oracle Without attacks this time and the message produced by the method move is ""
     * @passed Yes
     */
    @Test
    public void testMoveUpLeftRight() throws Exception {
        //teste dans un board 10x10
        // rappel: p = new Pawn('r',5,6,board);
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        Pawn p1 = new Pawn('A', 6, 7, board);
        Mockito.when(board.getSquareContent(6, 7)).thenReturn(p1);
        //sans attaque
        assertTrue(p.move(Direction.Up).equals(""));//en bougeant p se deplace a (5,7)
        assertTrue(p.move(Direction.Left).equals(""));//en bougeant p se deplace a (6,7) //attack
        assertTrue(p.move(Direction.Right).equals(""));//se deplace en (7,7)
    }


    /**
     * la méthode move()
     * Test move simple avec attack
     * board.isBonusSquare(x, y) == true
     * @throws Exception If the pawn moves out of the board, an OutOfBoardException will be thrown.
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @input mock board getYSize()=10 and getXSize()=10, a new pawn ('A', 6, 6, board), Direction=Right
     * @input mock getSquareContent(6,6)=p1 and (5,6) is the bonus square
     * @oracle With attacks and the message produced by the method move will contain "P attacks!" and p will lose 2 hitpoints.
     * @passed Yes
     */
    @Test
    public void testMoveEnemySuffer() throws Exception {
        //teste dans un board 10x10
        // rappel: p = new Pawn('r',5,6,board);
        assertTrue((p.getX() == 5));
        assertTrue((p.getY() == 6));

        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        Pawn p1 = new Pawn('A', 6, 6, board);
        Mockito.when(board.getSquareContent(6, 6)).thenReturn(p1);
        Mockito.when(board.isBonusSquare(5, 6)).thenReturn(true); //la ou se trouve le pawn est un square bonus
        /**assertTrue(p.move(Direction.Up).contains(p1.getLetter()+" loses "+2
         +" hitpoints."));//en bougeant p se deplace a (5,7)
         //sans attaque
         **/
        // assertTrue(p.move(Direction.Right).contains(p.getLetter()+" is dead."));
        assertTrue((p.getX() == 5));
        assertTrue((p.getY() == 6));
        assertTrue(p.move(Direction.Right).contains("attack"));
        assertTrue(p.move(Direction.Right).contains("loses 2"));
    }


    /**
     * Test move in the case that the pawn will move out of the board.
     * @throws Exception If the pawn moves out of the board, an OutOfBoardException will be thrown.
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @input mock board getYSize()=10 and getXSize()=10, a new pawn ('P', 10, 9, board), Direction=Right
     * @oracle An OutOfBoardException will be thrown.
     * @passed Yes
     */
    @Test(expected = OutOfBoardException.class)
    public void testMoveExceptionRight() throws Exception {
        // rappel: p = new Pawn('r',5,6,board);
        assertTrue((p.getX() == 5));
        assertTrue((p.getY() == 6));
        p = new Pawn('P', 10, 9, board);
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        p.move(Direction.valueOf("Right"));

    }


    /**
     * Test move in the case that the pawn will move out of the board.
     * @throws Exception If the pawn moves out of the board, an OutOfBoardException will be thrown.
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @input mock board getYSize()=10 and getXSize()=10, a new pawn ('P', 9, 10, board), Direction=Up
     * @oracle An OutOfBoardException will be thrown.
     * @passed Yes
     */
    @Test(expected = OutOfBoardException.class)
    public void testMoveExceptionUp() throws Exception {
        // rappel: p = new Pawn('r',5,6,board);
        assertTrue((p.getX() == 5));
        assertTrue((p.getY() == 6));
        p = new Pawn('P', 9, 10, board);
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        p.move(Direction.valueOf("Up"));

    }


    /**
     * Test move in the case that the pawn will move out of the board.
     * @throws Exception If the pawn moves out of the board, an OutOfBoardException will be thrown.
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @input mock board getYSize()=10 and getXSize()=10, a new pawn ('P', 0, 9, board), Direction=Left
     * @oracle An OutOfBoardException will be thrown.
     * @passed Yes
     */
    @Test(expected = OutOfBoardException.class)
    public void testMoveExceptionLeft() throws Exception {
        // rappel: p = new Pawn('r',5,6,board);
        assertTrue((p.getX() == 5));
        assertTrue((p.getY() == 6));
        p = new Pawn('P', 0, 9, board);
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        p.move(Direction.valueOf("Left"));

    }


    /**
     * Test move in the case that the pawn will move out of the board.
     * @throws Exception If the pawn moves out of the board, an OutOfBoardException will be thrown.
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @input mock board getYSize()=10 and getXSize()=10, a new pawn ('P', 9, 0, board), Direction=Down
     * @oracle An OutOfBoardException will be thrown.
     * @passed Yes
     */
    @Test(expected = OutOfBoardException.class)
    public void testMoveExceptionDown() throws Exception {
        // rappel: p = new Pawn('r',5,6,board);
        assertTrue((p.getX() == 5));
        assertTrue((p.getY() == 6));
        p = new Pawn('P', 9, 0, board);
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        p.move(Direction.valueOf("Down"));


    }





}