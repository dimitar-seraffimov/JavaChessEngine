package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {
	
	/* defining list of candidate destinations in respect to the current position*/
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
	
	public Knight (final Alliance pieceAlliance, final int piecePosition){
		super(piecePosition, pieceAlliance);
	}
	
	@Override
	public Collection<Move> calculateLegalMoves(final Board board){
		
		final List<Move> legalMoves = new ArrayList<>();
		
		/* looping through the coordinate offsets*/
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {

			/* candidate destination is the current position + current candidate*/
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			/*	if it isValidTileCoordinate*/
			if( BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)|| 
						isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)|| 
							isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
								isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}
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
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	
	/*column exclusions - exclude these candidateOffsets for the specific column*/
	private static boolean isFirstColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (cadnidateOffset == - 17 || cadnidateOffset == - 10||
				cadnidateOffset == 6 || cadnidateOffset == 15);
	}
	private static boolean isSecondColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (cadnidateOffset == - 10 || cadnidateOffset == 6);
	}
	
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int cadnidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] &&  (cadnidateOffset == - 15 || cadnidateOffset == - 6||
				cadnidateOffset == 10 || cadnidateOffset == 17);
	}

}
