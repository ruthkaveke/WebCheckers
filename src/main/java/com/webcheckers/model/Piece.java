package com.webcheckers.model;

import java.util.Objects;

/**
 * class that represents a piece on the board
 * @author Zeke Miller
 * @author Nick Sander
 */
public class Piece {
    public enum Type{
        SINGLE, KING
    }

    /**
     * Tells the current state of a piece:
     * BLOCKED - All surrounding spaces are occupied so the piece is unable to move
     * JUMP - The piece is able to make a jump, and therefore a jump should be made
     * OPEN - The piece is able to make at least 1 valid non-jump move
     */
    public enum State{
        BLOCKED, JUMP, OPEN
    }

    private Color color;
    private Type type;
    private State currentState;

    /**
     * constructor for a default piece, created with Type SINGLE since default pieces are not kings
     * @param color - an enumeration for the color of the piece, red or white
     */
    public Piece(Color color, State state){
        this.color = color;
        this.currentState = state;
        this.type = Type.SINGLE;
    }


    /**
     * Copy constructor
     * @param piece the piece to copy
     */
    public Piece(Piece piece){
        this.color = piece.getColor();
        this.type = piece.getType();
        this.currentState = piece.getState();
    }


    /**
     * checks if the piece can take the given Move as a step
     * @param move the move to check
     * @return true if can take
     */
    public boolean isValidStep( Move move ) {
        int startRow = move.getStart().getRow();
        int endRow = move.getEnd().getRow();
        return move.isStep() && ( endRow - startRow == color.getIncrement() );
    }


    /**
     * checks if the piece can take the given Move as a jump
     * @param move the move to check
     * @return true if can take
     */
    public boolean isValidJump( Move move ) {
        int startRow = move.getStart().getRow();
        int endRow = move.getEnd().getRow();
        return move.isJump() && ( endRow - startRow == color.getIncrement() * 2 );
    }


    /**
     * getter for the color of the piece
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * getter for the type of the piece
     * @return type
     */
    public Type getType() {
        return type;
    }

    public State getState(){
        return currentState;
    }

    public void setState(State state){
        this.currentState = state;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Piece piece = (Piece) other;
        return color == piece.color && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }
}
