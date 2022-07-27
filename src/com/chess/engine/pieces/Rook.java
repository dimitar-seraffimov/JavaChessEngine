package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Rook extends Piece{
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -8, -1, 1, 8 };

	
	public Rook(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.ROOK, piecePosition, pieceAlliance);
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
		return PieceType.ROOK.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (cadnidateOffset == - 1 );
	}
	private static boolean isEighthColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (cadnidateOffset == 1 );
	}
}
