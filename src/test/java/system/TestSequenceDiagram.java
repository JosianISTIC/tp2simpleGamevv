package system;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import simpleGame.core.Board;
import simpleGame.core.Game;
import simpleGame.core.Pawn;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the sequence diagrams.
 * @author Leite NA
 * @author Josian MARINIER
 */

@RunWith(MockitoJUnitRunner.class) // This is required for mocks to work
public class TestSequenceDiagram extends Game {

    Game game;
    Board board;
    Pawn p0,p1;


    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        game=new Game();
        board=mock(Board.class);
        //board= new Board(2,7,7,4,4);
        p0=mock(Pawn.class);
        p1=mock(Pawn.class);




    }

    @Test
    public void testIsGameOverMaxGold() throws Exception{

        Field boardField=game.getClass().getDeclaredField("board");
        boardField.setAccessible(true);
        boardField.set(game, board);
        when(board.maxGold()).thenReturn(3);
        boolean res=game.isGameOver();
        Mockito.verify(board).maxGold();
        //Mockito.verify(p0).getGold();
        //Mockito.verify(p1).getGold();
        assertTrue(res);

//        board=new Board(2,5,5,3,3);
//        ArrayList<Pawn> pawns=new ArrayList<>();
//        pawns.add(p0);
//        pawns.add(p1);
//        Field pawnField=board.getClass().getDeclaredField("pawns");
//        pawnField.setAccessible(true);
//        pawnField.set(board,pawns);
//
//        when(p0.getGold()).thenReturn(1);
//        when(p1.getGold()).thenReturn(3);
//        int max=board.maxGold();
//        InOrder order=inOrder(p0,p1);
//        order.verify(p0).getGold();
//        order.verify(p1).getGold();
//        assertEquals(3,max);

    }

    @Test
    public void testIsGameOverMaxGoldNotOver() throws Exception {

        Field boardField = game.getClass().getDeclaredField("board");
        boardField.setAccessible(true);
        boardField.set(game, board);
        when(board.maxGold()).thenReturn(2);
        boolean res = game.isGameOver();
        Mockito.verify(board).maxGold();
        //Mockito.verify(p0).getGold();
        //Mockito.verify(p1).getGold();
        assertFalse(res);
    }


    @Test
    public void testIsGameOverNumberPawnsNotOne() throws Exception {

        Field boardField = game.getClass().getDeclaredField("board");
        boardField.setAccessible(true);
        boardField.set(game, board);
        when(board.maxGold()).thenReturn(2);
        when(board.numberOfPawns()).thenReturn(2);
        boolean res = game.isGameOver();
        Mockito.verify(board).maxGold();
        //Mockito.verify(p0).getGold();
        //Mockito.verify(p1).getGold();
        assertFalse(res);

    }

    @Test
    public void testIsGameOverNumberPawns() throws Exception {

        Field boardField = game.getClass().getDeclaredField("board");
        boardField.setAccessible(true);
        boardField.set(game, board);
        when(board.maxGold()).thenReturn(2);
        when(board.numberOfPawns()).thenReturn(1);
        boolean res = game.isGameOver();
        Mockito.verify(board).numberOfPawns();// MaxGold will not be called because the first condition is verified.
        //Mockito.verify(p0).getGold();
        //Mockito.verify(p1).getGold();
        assertTrue(res);

    }


    @Test
    public void testIsMaxGoldGetGold() throws Exception{
        board=new Board(2,5,5,3,3);
        ArrayList<Pawn> pawns=new ArrayList<>();
        pawns.add(p0);
        pawns.add(p1);
        Field pawnField=board.getClass().getDeclaredField("pawns");
        pawnField.setAccessible(true);
        pawnField.set(board,pawns);

        when(p0.getGold()).thenReturn(1);
        when(p1.getGold()).thenReturn(3);
        int max=board.maxGold();
        InOrder order=inOrder(p0,p1);
        order.verify(p0).getGold();
        order.verify(p1).getGold();
        assertEquals(3,max);
    }

// Belows are Josian's tests


    /**
     * @see simpleGame.core.Game#isGameOver()
     */
    @Test
    public void TestIsGameOver() throws Exception
    {

        Game g1 = new Game();

        //utilisation de la reflection pour set le board a un mockitos
        Field field = g1.getClass().getDeclaredField("board");//recupere l'attibut de Game
        field.setAccessible(true);//on le rend accessible
        Object b = mock(Board.class);//instancie un mockito
        field.set(g1,(Board)b); //

        when(((Board) b).numberOfPawns()).thenReturn(2);
        when(((Board) b).maxGold()).thenReturn(3);
        //tester appel a numberofpawns
        // puis maxgold()

        org.junit.Assert.assertTrue(g1.isGameOver());

        //dans l'ordre il va executer numberofpawns() puis mawGold
        InOrder mocksWithOrder = inOrder(b);

        mocksWithOrder.verify((Board) b).numberOfPawns();
        mocksWithOrder.verify((Board) b).maxGold();


    }



    /**
     * @see simpleGame.core.Game
     */
    @Test
    public void TestMaxGold()
    {
        Board b = new Board(0,4,4,2,2);
        b.removeAllPawns();

        Pawn p1 = mock(Pawn.class);
        Pawn p2 = mock(Pawn.class);

        //pour etre sur qu'on va bien ajouter
        //deux pawns(deux position differentes)

        when(p1.getX()).thenReturn(3);
        when(p1.getY()).thenReturn(3);
        when(p2.getX()).thenReturn(2);
        when(p2.getY()).thenReturn(2);


        //on ajoute les pawn au board
        b.addPawn(p1);
        b.addPawn(p2);

        when(p1.getGold()).thenReturn(1);
        when(p2.getGold()).thenReturn(3);

        org.junit.Assert.assertTrue(b.maxGold() == 3);

        //dans l'ordre il va executer p1.getGold, puis p2.getGold()
        InOrder mocksWithOrder = inOrder(p1, p2);
        mocksWithOrder.verify(p1).getGold();
        mocksWithOrder.verify(p2).getGold();
    }

}
