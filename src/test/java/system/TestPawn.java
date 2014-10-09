package system;

import junit.framework.TestCase;

//It is recommended to import Mockito statically so that the code looks clearer
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
 */
public class TestPawn
{
    Pawn p;
    Board board; //le mockito

    @Before
    public void SimplePawn()
    {
        // Creating context
        board = mock(Board.class);
        p = new Pawn('P',5,6,board);
    }

    @Test
    public void testGetX() throws Exception {
        assertTrue(p.getX()==5);
    }
    @Test
    public void testGetY() throws Exception {
        assertTrue(p.getY()==6);
    }
    @Test
    public void testGetLetter() throws Exception {
        assertTrue(p.getLetter()=='P');
    }

    @Test
    public void testGetGold() throws Exception {
        assertTrue(p.getGold()==0);
    }

    /** la méthode move()
     *
     * Test move avec appel a la methode attack
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @throws Exception
     */
    @Test
    public void testMove() throws Exception {
        //teste dans un board 10x10
        // rappel: p = new Pawn('r',5,6,board);
        Direction d = Direction.Down; //position y--
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        Pawn p1 = new Pawn('A',5,5,board);
        Mockito.when(board.getSquareContent(5, 5)).thenReturn(p1);
        //sans attaque
        assertTrue(!p.move(d).equals(""));
        assertTrue(p.move(d).contains("P attacks!"));
    }

    /** la méthode move()
     *
     * Test move simple sans attack
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @throws Exception
     */
    @Test
    public void testMoveUpLeftRight() throws Exception {
        //teste dans un board 10x10
        // rappel: p = new Pawn('r',5,6,board);
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        Pawn p1 = new Pawn('A',6,7,board);
        Mockito.when(board.getSquareContent(6, 7)).thenReturn(p1);
        //sans attaque
        assertTrue(p.move(Direction.Up).equals(""));//en bougeant p se deplace a (5,7)
        assertTrue(p.move(Direction.Left).equals(""));//en bougeant p se deplace a (6,7) //attack
        assertTrue(p.move(Direction.Right).equals(""));//se deplace en (7,7)
    }


    /** la méthode move()
     *
     * Test move simple avec attack
     * board.isBonusSquare(x, y) == true
     * @see simpleGame.core.Pawn#move(simpleGame.core.Direction)
     * @throws Exception
     */
    @Test
    public void testMoveEnemySuffer() throws Exception {
        //teste dans un board 10x10
        // rappel: p = new Pawn('r',5,6,board);
        assertTrue((p.getX()==5));
        assertTrue((p.getY()==6));

        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        Pawn p1 = new Pawn('A',6,6,board);
        Mockito.when(board.getSquareContent(6, 6)).thenReturn(p1);
        Mockito.when(board.isBonusSquare(5, 6)).thenReturn(true); //la ou se trouve le pawn est un square bonus
        /**assertTrue(p.move(Direction.Up).contains(p1.getLetter()+" loses "+2
                +" hitpoints."));//en bougeant p se deplace a (5,7)
        //sans attaque
        **/
       // assertTrue(p.move(Direction.Right).contains(p.getLetter()+" is dead."));
        assertTrue((p.getX()==5));
        assertTrue((p.getY()==6));
        assertTrue(p.move(Direction.Right).contains("attack"));
        assertTrue(p.move(Direction.Right).contains("loses 2"));
    }

    @Test(expected = OutOfBoardException.class)
    public void testMoveException() throws Exception
    {
        // rappel: p = new Pawn('r',5,6,board);
        assertTrue((p.getX()==5));
        assertTrue((p.getY()==6));
        p = new Pawn('P',10,10,board);
        Mockito.when(board.getYSize()).thenReturn(10);
        Mockito.when(board.getXSize()).thenReturn(10);
        assertTrue(p.move(Direction.Right).contains("attack")); //exception car on sort du board
    }
}