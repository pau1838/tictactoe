import java.util.*;
import java.io.*;

public class tictactoe
{
	static char game [][];
	static int chance_counter,winner;
	static Scanner sc = new Scanner(System.in);
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	tictactoe()
	{
		game=new char[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				game[i][j] = ' ';
		chance_counter=0;
		winner=0;
	}
	
	private static void printGame()
	{
		System.out.print(String.format("\033[H\033[2J"));
		System.out.println(ANSI_GREEN+"\n\n\t\t\tCurrent Game Status:"+ANSI_RESET+"\n\n");
		for(int i=0;i<3;i++)
		{
			System.out.print("\t\t\t\t");
			for(int j=0;j<3;j++)
			{
				if(j!=2)
				{
					if(game[i][j]=='X')
						System.out.print(ANSI_RED+game[i][j]+ANSI_RESET+"|");
					else
						System.out.print(ANSI_GREEN+game[i][j]+ANSI_RESET+"|");
				}
				else
				{
					if(game[i][j]=='X')
						System.out.print(ANSI_RED+game[i][j]+ANSI_RESET);
					else
						System.out.print(ANSI_GREEN+game[i][j]+ANSI_RESET);
				}
			}
			System.out.println();	
			if(i!=2)
			{
				System.out.print("\t\t\t\t");
				System.out.println("______");
			}
		}
		System.out.println("\n\n\t"+ANSI_YELLOW+"Enter Position Eg- 1 2 (For 1st Row, 2nd Column)"+ANSI_RESET);	
	}
	
	private static void instructions()
	{
		System.out.print(String.format("\033[H\033[2J"));
		System.out.println(ANSI_YELLOW+"\n\n\t\t| | (_)    | |           | |            ");
		System.out.println("\t\t| |_ _  ___| |_ __ _  ___| |_ ___   ___ ");
		System.out.println("\t\t| __| |/ __| __/ _` |/ __| __/ _ \\ / _\\");
		System.out.println("\t\t| |_| | (__| || (_| | (__| || (_) |  __/");
 		System.out.println("\t\t\\__ |_|\\___|\\__\\__,_|\\___|\\__\\___/ \\___|\n\n");
                                        
		System.out.println("Welcome to TIC TAC TOE Game");
		System.out.println("It is a 2 player game.");
		System.out.println("The first player is known as X and the second is O.");
		System.out.println("Players play Xs and Os on the game board until all nine squares are filled.");
		System.out.println("Or anyone wins before it.\n"+ANSI_RESET);
		
	}
	
	private static void play()throws IOException
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int player=1;
		String input;
		chance_counter=0;
		System.out.print(ANSI_PURPLE+"\tEnter name of Player 1:\t"+ANSI_RESET);
		String name1=br.readLine();
		System.out.print(ANSI_PURPLE+"\tEnter name of Player 2:\t"+ANSI_RESET);
		String name2=br.readLine();
		while(chance_counter!=9)
		{
			printGame();
			String tempPlayer=(player==1)?name1:name2;
			tempPlayer=tempPlayer.toUpperCase();
			System.out.println("\n\n\t"+tempPlayer);
			System.out.print(ANSI_CYAN+"\tINPUT:\t"+ANSI_RESET);
			input=br.readLine();
			int x=Integer.parseInt(input.substring(0,input.indexOf(' ')));
			int y=Integer.parseInt(input.substring(input.indexOf(' ')+1));
			if(checkValidity(x,y))
			{
				game[x-1][y-1]=(player==1)?'X':'O';
				if(winnerCheck())
				{
					printGame();
					System.out.println("\n\n\t\t"+tempPlayer+" Wins");
					System.exit(0);
				}
				player=(player==1)?2:1;
				chance_counter++;
			}
			else
			{
				System.out.println("\tInvalid Position.\n\tPosition Doesn't Exist or is Already Occupied.");
				System.out.print(ANSI_CYAN+"\tINPUT:\t"+ANSI_RESET);
				input=br.readLine();
				x=Integer.parseInt(input.substring(0,1));
				y=Integer.parseInt(input.substring(2));
				if(checkValidity(x,y))
				{
					game[x-1][y-1]=(player==1)?'X':'O';
					chance_counter++;
					if(winnerCheck())
					{
						printGame();
						System.out.println("\n\n\t\t"+tempPlayer+" Wins");
						System.exit(0);
					}
					player=(player==1)?2:1;
				}
			}
		}
		if(chance_counter==9)
		{
			printGame();
			System.out.println("\n\n\tGAME IS DRAW!!!");
		}
	}
				
	private static boolean checkValidity(int i,int j)
	{
		boolean validity=false;
		if((i>=1&&i<=3) && (j>=1&&j<=3) && (game[i-1][j-1]==' '))
			validity=true;
		return validity;
	}
	
	private static boolean rowCheck()
	{
		boolean status=false;
		if(game[0][0]==game[0][1] && game[0][1]==game[0][2] && game[0][0]!=' ')
			status=true;
		else if(game[1][0]==game[1][1] && game[1][1]==game[1][2] && game[1][0]!=' ')
			status=true;
		else if(game[2][0]==game[2][1] && game[2][1]==game[2][2] && game[2][0]!=' ')
			status=true;
		return status;
	}
	
	private static boolean columnCheck()
	{
		boolean status=false;
		if(game[0][0]==game[1][0] && game[1][0]==game[2][0] && game[0][0]!=' ')
			status=true;
		else if(game[0][1]==game[1][1] && game[1][1]==game[2][1] && game[0][1]!=' ')
			status=true;
		else if(game[0][2]==game[1][2] && game[1][2]==game[2][2] && game[0][2]!=' ')
			status=true;
		return status;
	}
					
	private static boolean diagonalCheck()
	{
		boolean status=false;
		if(game[0][0]==game[1][1] && game[1][1]==game[2][2] && game[0][0]!=' ')
			status=true;
		else if(game[0][2]==game[1][1] && game[1][1]==game[2][0] && game[0][2]!=' ')
			status=true;
		return status;
	}
	
	private static boolean winnerCheck()
	{
		boolean status=false;
		if(rowCheck() || columnCheck() || diagonalCheck())
			status=true;
		return status;	
	}
	
	public static void main(String args[])throws IOException
	{
		tictactoe obj=new tictactoe();
		obj.instructions();
		obj.play();
	}
	
}
