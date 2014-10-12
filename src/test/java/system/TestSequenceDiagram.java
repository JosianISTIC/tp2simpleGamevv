package system;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import simpleGame.core.Board;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import simpleGame.core.*;
/**
 * Created by josian on 12/10/14.
 */
public class TestSequenceDiagram{


    /**
     * @see simpleGame.core.Game#isGameOver()
     */
    @Test
    public void TestIsGameOver()
    {

        Game g = new Game();

        /**Game g1 = mock(Game.class);

        when(g1.).thenReturn(1);
        when(g2.getGold()).thenReturn(2);

        InOrder mocksWithOrder = inOrder(p1, p2);
        mocksWithOrder.verify(p1).getGold();
        mocksWithOrder.verify(p2).getGold();**/
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
