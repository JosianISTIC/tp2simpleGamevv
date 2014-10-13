package system;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.stubbing.OngoingStubbing;
import simpleGame.core.Board;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import simpleGame.core.*;

import java.lang.reflect.Field;

/**
 * Created by josian on 12/10/14.
 */
public class TestSequenceDiagram{


    /**
     * @see simpleGame.core.Game#isGameOver()
     * @oracle the method isGameOver must call board.numberOfPawns() and the board.maxGold()
     * @passed Yes
     * @input a Board as Mock where b.numberOfPawns()==2 and b.maxGold() == 3
     */
    @Test
    public void TestIsGameOver() throws Exception
    {

        Game g1 = new Game();


        Board b = mock(Board.class);//instancie un mockito
        g1.setBoard(b); //set un mock Board

        when(b.numberOfPawns()).thenReturn(2);
        when(b.maxGold()).thenReturn(3);
        //tester appel a numberofpawns
        // puis maxgold()

        org.junit.Assert.assertTrue(g1.isGameOver());

        //dans l'ordre il va executer numberofpawns() puis mawGold
        InOrder mocksWithOrder = inOrder(b);

        mocksWithOrder.verify((Board) b).numberOfPawns();
        mocksWithOrder.verify((Board) b).maxGold();


    }



    /**
     * @see simpleGame.core.Board#maxGold()
     * @input 2 Pawn as Mock: p1 and p2
     * @oracle the method board.maxGold() must call getGold() on  p1 and then on p2
     * @passed Yes
     *
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
