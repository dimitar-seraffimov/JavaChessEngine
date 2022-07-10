package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class King extends Piece{
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };

	public King(final Alliance pieceAlliance, final int piecePosition) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final  Board board) {
		
		final List<Move> legalMoves = new ArrayList<>(); 
				
		for(final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)|| 
						isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}
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
				}
			}	
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.KING.toString();
	}
	/*column exclusions - exclude these candidateOffsets for the specific column*/
	private static boolean isFirstColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (cadnidateOffset == - 9 || cadnidateOffset == - 1 ||
				cadnidateOffset == 7);
	}
	private static boolean isEighthColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (cadnidateOffset == - 7 || cadnidateOffset == 1 ||
				cadnidateOffset == 9);
	}
}
