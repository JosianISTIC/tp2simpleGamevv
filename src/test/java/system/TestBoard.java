package system;

import org.junit.Before;
import org.junit.Test;
import simpleGame.core.Board;
import simpleGame.core.Direction;
import simpleGame.core.Pawn;

import static org.junit.Assert.assertTrue;

/**
 * Created by josian on 07/10/14.
 */
public class TestBoard
{
    //bon à savoir: les indices de Board commencent à 1
    // 1< indexX <= board.getXSize()
    // 1< indexY <= board.getYSize()

    Board board; //le mockito

    @Before
    public void SimpleBoard()
    {
        // Creating context
        board = new Board(3,4,5,2,2);

    }

    /**
     * @see simpleGame.core.Board#Board(int, int, int, int, int)
     */
    @Test
    public void TestInit()
    {
        Board board1 = new Board(0,5,5,2,2);
        assertTrue(board1.numberOfPawns()==0); //bug here
        //ligne : currentPawn = pawns.get(0); impossile si pawns.size()==0
    }
    /**
     * @see simpleGame.core.Board#removeAllPawns()
     */
    @Test
    public void TestRemoveAllPawns()
    {
        //bug détecter dans ce code
        //risque de division par 0, si pas de pawns dans la list<Pawns>
        board.removeAllPawns();
        for(int i=1;i<=4;i++)
        {
            for(int j=1;j<=5;j++)
            {
                assertTrue(board.getSquareContent(i,j)==null);
            }
        }

    }

    /**
     * @see simpleGame.core.Board#getXSize()
     */
    @Test
    public void TestGetX()
    {
        assertTrue(board.getXSize()==4);
    }

    /**
     * @see simpleGame.core.Board#getYSize()
     */
    @Test
    public void TestGetY()
    {
        assertTrue(board.getYSize()==5);
    }

    /**
     * @see simpleGame.core.Board#numberOfPawns()
     */
    @Test
    public void TestNumberOfPawns()
    {
        assertTrue(board.numberOfPawns()<=3);
    }

    /**
     * @see simpleGame.core.Board#isBonusSquare(int, int)
     */
    @Test
    public void TestIsBonusSquare()
    {
        assertTrue(board.isBonusSquare(2,2));
        assertTrue(!board.isBonusSquare(1,2));
    }

    /**
     * @see simpleGame.core.Board#addPawn(simpleGame.core.Pawn)
     */
    @Test
    public void TestAddPawns()
    {
        board.removeAllPawns();
        assertTrue(board.numberOfPawns()==0);
        Pawn p1 = new Pawn('A',1,1,board);

        assertTrue(board.getSquareContent(1,1)==null);//teste avant si il n'y a rien dans la case
        board.addPawn(p1);
        assertTrue(board.numberOfPawns()==1);

        //teste l'ajout la ou il y a deja un pawn
        Pawn p10 = new Pawn('B',1,1,board);
        board.addPawn(p10);
        assertTrue(board.numberOfPawns()==1); //on verifie qu'il a pas ajouté le pawn , car case deja occupé

    }
    /**
     *@see simpleGame.core.Board#getSquareContent(int, int)
     */
    @Test
    public void TestGetSquareContent()
    {
        board.removeAllPawns();
        Pawn p1 = new Pawn('A',1,1,board);
        Pawn p2 = new Pawn('B',2,1,board);
        Pawn p3 = new Pawn('C',3,1,board);
        board.addPawn(p1);
        board.addPawn(p2);
        board.addPawn(p3);
        assertTrue(board.getSquareContent(1,1)==p1);
        assertTrue(board.getSquareContent(2,1)==p2);
        assertTrue(board.getSquareContent(3,1)==p3);

    }

    /**
     * @see simpleGame.core.Board#getNextPawn()
     */
    @Test
    public void TestGetNextPawn()
    {
        board.removeAllPawns();
        assertTrue(board.getNextPawn()==null);//bug find here

        Pawn p1 = new Pawn('A',1,1,board);
        board.addPawn(p1);
        assertTrue(board.getNextPawn()==p1);


        Pawn p2 = new Pawn('B',2,1,board);
        Pawn p3 = new Pawn('C',3,1,board);
        board.addPawn(p2);
        board.addPawn(p3);
        assertTrue(board.numberOfPawns()==3);

        assertTrue(board.getNextPawn()==p1); //probleme ici
        assertTrue(board.getNextPawn()==p2);
        assertTrue(board.getNextPawn()==p3);
        assertTrue(board.getNextPawn()==p1);

    }

    @Test
    public void TestContentSprite()
    {
        //teste 123456
        board = new Board(0,3,3,2,2);
        assertTrue(board.numberOfPawns()==0);
        board.removeAllPawns();
        Pawn p1 = new Pawn('A',1,1,board);
        Pawn p2 = new Pawn('B',2,1,board);
        Pawn p3 = new Pawn('C',3,1,board);
        board.addPawn(p1);
        board.addPawn(p2);
        board.addPawn(p3);
        board.getNextPawn();
        assertTrue(board.squareContentSprite(1,1) == 'c');
        assertTrue(board.squareContentSprite(2,2)=='#');
        assertTrue(board.squareContentSprite(3,3)=='.');
    }

    /**
     * @see simpleGame.core.Board#removePawn(simpleGame.core.Pawn)
     */
    @Test
    public void TestRemovePawn()
    {

        board = new Board(0,3,3,2,2);
        board.removeAllPawns();
        Pawn p1 = new Pawn('A',1,1,board);
        Pawn p2 = new Pawn('B',2,1,board);
        Pawn p3 = new Pawn('C',3,1,board);
        board.addPawn(p1);
        board.addPawn(p2);
        board.addPawn(p3);

        assertTrue(board.numberOfPawns()==3);
        board.removePawn(p3);
        assertTrue(board.numberOfPawns()==2);
    }

    /**
     * @see simpleGame.core.Board#maxGold()
     */
    @Test
    public void TestMaxGold() throws Exception
    {
        board = new Board(0,3,3,2,2);
        board.removeAllPawns();
        Pawn p1 = new Pawn('A',1,1,board);
        Pawn p2 = new Pawn('B',2,1,board);
        Pawn p3 = new Pawn('C',3,1,board);
        board.addPawn(p1);
        board.addPawn(p2);
        board.addPawn(p3);

        //p1,p2,p3 ont chacun 2 hitpoints par defaut

        //P1 move en (2,1)
        p1.move(Direction.Right);//p1 attaque P2
        //P2 perd 1 hitpoint (car pas sur une bonusSquare(==(2,2)))
        assertTrue(p1.getGold()==0);
        assertTrue(p2.getGold()==0);
        assertTrue(p3.getGold()==0);


        p1.move(Direction.Right);//p1 attaque P2 une nouvelle fois
        //P2 perd 1 hitpoint encore une fois, P1 gagne un gold

        //P2 isdead car plus de gold
        assertTrue(board.numberOfPawns()==2);


        assertTrue(p1.getGold()==1);
        assertTrue(p2.getGold()==0);


        assertTrue(board.maxGold()==1); //car aucune collision avec personne
    }

    /**
     * @see simpleGame.core.Board#toString()
     */
    @Test
    public void TestToString()
    {
        board = new Board(0,3,3,2,2);
        board.removeAllPawns();
        Pawn p1 = new Pawn('A',1,3,board);
        Pawn p2 = new Pawn('B',3,2,board);
        Pawn p3 = new Pawn('C',3,1,board);
        board.addPawn(p1);
        board.addPawn(p2);
        board.addPawn(p3);

        assertTrue(board.toString().trim().equals("A..\n.#B\n..C"));
    }
}
