package com.chess.engine.pieces;


import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Bishop extends Piece{

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -7, 7, 9 };
			
	public Bishop(final Alliance pieceAlliance, final int piecePosition) {
		super(piecePosition, pieceAlliance);

	}
	@Override 
	public Collection<Move> calculateLegalMoves(final Board board){
		
		final List<Move> legalMoves = new ArrayList<>();

		for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {			
				
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)|| 
								isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}
				candidateDestinationCoordinate += candidateCoordinateOffset;
				
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
	
					if(!candidateDestinationTile.isTileOccupied()) {
						/* adding non attacking legal move if the tile is not occupied*/
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}else {
						/* in the case when the tile is occupied */
						/* getting the alliance of the piece that is occupying the tile */
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						if(this.pieceAlliance != pieceAlliance ) {
							/* adding attacking legal move*/
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						}
						break;	
					}
				}	
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.BISHOP.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (cadnidateOffset == - 9 || cadnidateOffset == 7);
	}
	private static boolean isEighthColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (cadnidateOffset == - 7 || cadnidateOffset == 9);
	}
	
}
