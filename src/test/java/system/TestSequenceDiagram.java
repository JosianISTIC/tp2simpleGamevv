package system;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import simpleGame.core.Board;
import simpleGame.core.Game;
import simpleGame.core.Pawn;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockingDetails;

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


}
