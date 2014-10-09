package system;

import org.junit.Before;
import org.junit.Test;
import simpleGame.core.Board;
import simpleGame.core.Direction;
import simpleGame.core.Pawn;
import simpleGame.exception.OutOfBoardException;

import static org.junit.Assert.assertTrue;

/**
 * Test cases for the class Board
 * @see simpleGame.core.Board
 * @author Josian MARINIER
 * @author Leite NA
 */
public class TestBoard
{
    //bon à savoir: les indices de Board commencent à 1
    // 1< indexX <= board.getXSize()
    // 1< indexY <= board.getYSize()

    Board board; //le mockito

    /**
     * Do something we must do before each test beginning: A new Board(3,4,5,2,2)
     */
    @Before
    public void SimpleBoard()
    {
        // Creating context
        board = new Board(3,4,5,2,2);

    }

    /**
     * Test the constructor in the class Board
     * @see simpleGame.core.Board#Board(int, int, int, int, int)
     * @input 0,5,5,2,2
     * @oracle An board will be created that the number of Pawns equals zero.But a bug found: can not set the 0 pawn as the current one.
     * @passed Yes with code changed in the class Board, No if not.
     */
    @Test
    public void TestInit()
    {
        Board board1 = new Board(0,5,5,2,2);
        assertTrue(board1.numberOfPawns()==0); //bug here
        //ligne : currentPawn = pawns.get(0); impossile si pawns.size()==0
    }
    /**
     * Test for the method removeAllPawns.
     * @see simpleGame.core.Board#removeAllPawns()
     * @input
     * @oracle After removing all pawns, there must be null in each square in the board.
     * @passed Yes
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
     * Test for getXSize.
     * @see simpleGame.core.Board#getXSize()
     * @input the board created before.
     * @oracle The result is 4.
     * @passed Yes
     */
    @Test
    public void TestGetX()
    {
        assertTrue(board.getXSize()==4);
    }

    /**
     * Test for getYSize.
     * @see simpleGame.core.Board#getYSize()
     * @input the board created before.
     * @oracle The result is 5.
     * @passed Yes
     * */
    @Test
    public void TestGetY()
    {
        assertTrue(board.getYSize()==5);
    }

    /**
     * Test for the mothod numberOfPawns.
     * @see simpleGame.core.Board#numberOfPawns()
     * @input the board created before.
     * @oracle The result is 3.
     * @passed Yes
     */
    @Test
    public void TestNumberOfPawns()
    {
        assertTrue(board.numberOfPawns()==3); //@TODO To discus a little why <=3.
    }

    /**
     * Test for the method isBonusSquare.
     * @see simpleGame.core.Board#isBonusSquare(int, int)
     * @input the board created before. The squares tested: (2,2) (1,2)
     * @oracle (2,2) is the bonus square and (1,2) is not.
     * @passed Yes
     */
    @Test
    public void TestIsBonusSquare()
    {
        assertTrue(board.isBonusSquare(2,2));
        assertTrue(!board.isBonusSquare(1,2));
    }

    /**
     * Test for the method addPawns. We remove all pawns first and add some. We still test if the square has one already.
     * @see simpleGame.core.Board#addPawn(simpleGame.core.Pawn)
     * @input the board created before. Pawn1=Pawn('A',1,1,board) Pawn2=Pawn('B',1,1,board).
     * @oracle The number of pawns for the first time is 1 and the second time is 1 too.
     * @passed Yes
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
     * Test for the method getSquareContent if we add some pawns in squares
     * @see simpleGame.core.Board#getSquareContent(int, int)
     * @input three pawns
     * @oracle Each pawn has its right postion and the square must be have the right pawns
     * @passed Yes
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
     * Test for method getNextPawn
     * @see simpleGame.core.Board#getNextPawn()
     * @input 3 pawns added to the board
     * @oracle the next pawn of p1 is p2, the next of p2 is p3 and the next of p3 is p1
     * @passed Yes after modifying codes, No if not.
     */
    @Test
    public void TestGetNextPawn()
    {
        board.removeAllPawns();
        assertTrue(board.getNextPawn()==null);//bug find here

        Pawn p1 = new Pawn('A',1,1,board);
        board.addPawn(p1);
        assertTrue(board.getNextPawn() == p1);


        Pawn p2 = new Pawn('B',2,1,board);
        Pawn p3 = new Pawn('C',3,1,board);
        board.addPawn(p2);
        board.addPawn(p3);
        assertTrue(board.numberOfPawns()==3);

        assertTrue(board.getNextPawn() == p1); //probleme ici
        assertTrue(board.getNextPawn()==p2);
        assertTrue(board.getNextPawn()==p3);
        assertTrue(board.getNextPawn()==p1);

    }

    /**
     * Test for method squareContentSprite
     * @see simpleGame.core.Board#squareContentSprite(int, int)
     * @input 3 pawns
     * @oracle The right char should be presented for some squares.
     * @passed Yes
     */
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
        assertTrue(board.squareContentSprite(1, 1) == 'c');
        assertTrue(board.squareContentSprite(2, 2)=='#');
        assertTrue(board.squareContentSprite(3, 3)=='.');
    }

    /**
     * Test for the method removePawn
     * @see simpleGame.core.Board#removePawn(simpleGame.core.Pawn)
     * @input 3 pawns
     * @oracle The number of pawns must be decreased after remove.
     * @passed Yes
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
     * Test for method MaxGold
     * @throws simpleGame.exception.OutOfBoardException If a pawn moves out of the board, an exception will be thrown.
     * @see simpleGame.core.Board#maxGold()
     * @input 3 pawns with some moves
     * @oracle The max gold will be rightly computed.
     * @passed Yes
     */
    @Test
    public void TestMaxGold() throws OutOfBoardException
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
     * Test for method toString
     * @see simpleGame.core.Board#toString()
     * @input 3 pawns
     * @oracle Right sentences.
     * @passed Yes
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
