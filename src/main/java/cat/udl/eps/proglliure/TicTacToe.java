package cat.udl.eps.proglliure;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class TicTacToe
{
	static char game [][];
	static int chance_counter,winner;
	static final ResourceBundle i18n = ResourceBundle.getBundle("translations");

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	TicTacToe()
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
		System.out.println(ANSI_GREEN+"\n\n\t\t\t"+i18n.getString("GAME_STATUS_MSG")+ANSI_RESET+"\n\n");
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
		System.out.println("\n\n\t"+ANSI_YELLOW+i18n.getString("ENTER_POSITION")+ANSI_RESET);
	}

	private static void instructions()
	{
		System.out.print(String.format("\033[H\033[2J"));
		System.out.println(ANSI_YELLOW+"\n\n\t\t| | (_)    | |           | |            ");
		System.out.println("\t\t| |_ _  ___| |_ __ _  ___| |_ ___   ___ ");
		System.out.println("\t\t| __| |/ __| __/ _` |/ __| __/ _ \\ / _\\");
		System.out.println("\t\t| |_| | (__| || (_| | (__| || (_) |  __/");
		System.out.println("\t\t\\__ |_|\\___|\\__\\__,_|\\___|\\__\\___/ \\___|\n\n");

		System.out.println(i18n.getString("WELCOME"));
		System.out.println(i18n.getString("PLAYERS"));
		System.out.println(i18n.getString("PLAYERS_X_O"));
		System.out.println(i18n.getString("INSTRUCTIONS1"));
		System.out.println(i18n.getString("INSTRUCTIONS2")+"\n"+ANSI_RESET);

	}

	private static void play()throws IOException
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int player=1;
		boolean isValid = false;
		String input;
		chance_counter=0;
		System.out.print(ANSI_PURPLE+"\t"+i18n.getString("ENTER_P1")+"\t"+ANSI_RESET);
		String name1=br.readLine();
		System.out.print(ANSI_PURPLE+"\t"+i18n.getString("ENTER_P2")+"\t"+ANSI_RESET);
		String name2=br.readLine();
		while(chance_counter!=9)
		{
			printGame();
			String tempPlayer=(player==1)?name1:name2;
			tempPlayer=tempPlayer.toUpperCase();
			System.out.println("\n\n\t"+tempPlayer);
			System.out.print(ANSI_CYAN+"\t"+i18n.getString("INPUT")+"\t"+ANSI_RESET);
			isValid = false;
			do {
				input=br.readLine();
				if (formatValid(input)) {
					int x=Integer.parseInt(input.substring(0,input.indexOf(' ')).trim());
					int y=Integer.parseInt(input.substring(input.indexOf(' ')+1).trim());

					if (checkValidity(x, y)) {
						game[x-1][y-1]=(player==1)?'X':'O';
						if(winnerCheck())
						{
							printGame();
							System.out.println("\n\n\t\t"+tempPlayer+i18n.getString("WINS"));
							System.exit(0);
						}
						player=(player==1)?2:1;
						chance_counter++;
						isValid = true;
					} else {
						System.out.println("\t"+i18n.getString("INVALID_POS"));
						System.out.println("\n\t"+ANSI_YELLOW+i18n.getString("ENTER_POSITION")+ANSI_RESET);
						System.out.print(ANSI_CYAN+"\t"+i18n.getString("INPUT")+"\t"+ANSI_RESET);
					}
				} else {
					System.out.print(ANSI_RED+"\t"+i18n.getString("INVALID_FORMAT")+"\n\t");
					System.out.println("\n\t"+ANSI_YELLOW+i18n.getString("ENTER_POSITION")+ANSI_RESET);
					System.out.print(ANSI_CYAN+"\t"+i18n.getString("INPUT")+"\t"+ANSI_RESET);
				}
			} while (!isValid);
		}
		if(chance_counter==9)
		{
			printGame();
			System.out.println("\n\n\t"+i18n.getString("DRAW"));
		}
	}

	private static boolean formatValid(String input) {
		String[] result = input.split("\\s+");
		if (result.length != 2) {
			return false;
		} else {
			return NumberUtils.isCreatable(result[0]) && NumberUtils.isCreatable(result[1]);
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
		TicTacToe obj=new TicTacToe();
		obj.instructions();
		obj.play();
	}

}