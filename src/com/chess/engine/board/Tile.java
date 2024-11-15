package com.chess.engine.board;


import com.chess.engine.pieces.Piece;
/** imports guava google ImmutableMap **/
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;


/**
 *  creating a single class chess tile for all 64 tiles
 */
public abstract class Tile {
	
	protected final int tileCoordinate;
	
	private static final Map<Integer, EmtyTile> EMPTY_TILES_CACHE = createAllPosiibleEmptyTiles();
	
	private static Map<Integer, EmtyTile> createAllPosiibleEmptyTiles(){
		
		final Map<Integer, EmtyTile> emptyTileMap = new HashMap<>();
		
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmtyTile(i));
		}
		
		return ImmutableMap.copyOf(emptyTileMap);
	}
	/**
	 *  
	 */
	public static Tile createTile (final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	}
	/**
	 *  
	 */
	private Tile (final int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	/**	 */
	public abstract boolean isTileOccupied(); /** method which will check whether tile is occupied	 */
	
	public abstract Piece getPiece();
	
	public static final class EmtyTile extends Tile{
		
		private EmtyTile(final int coordinate){
			super(coordinate);
		}
		
		@Override
		public String toString() {
			return "-";
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
	public static final class OccupiedTile extends Tile{
		
		private final Piece pieceOnTile;
		
		private OccupiedTile(int tileCoordinate, final Piece pieceOnTile){
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase():
					getPiece().toString();
		}
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
		
	}
}
