package com.chess.engine;

import com.chess.engine.board.Board;

public class JavaChess {
	public static void main (String[] args) {
		Board board = Board.createStandarBoard();
		
		System.out.println(board);
	}
}
