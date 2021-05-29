package problem1_tictactoe;

import javax.swing.JFrame;

public class PlayerX {

	public static void main(String[] args) {
		JFrame myWindow = new GameBoard(GameManager.X);		
		myWindow.setVisible(true);
	}

}
