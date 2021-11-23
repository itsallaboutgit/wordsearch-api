package com.JavaWordSearchProgram.wordsearchapi.Services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {

    private enum Direction{
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE,
    }

    private class Coordinate{
        int x;
        int y;
        Coordinate(int x, int y){
            this.x=x;
            this.y=y;
        }
    }

    public char[][] generateGrid(int gridsize , List<String> words){
        List<Coordinate> coordinates =new ArrayList<>();
        char[][] contents = new char[gridsize][gridsize];
        for(int i=0; i<gridsize; i++) {
            for (int j=0; j<gridsize; j++) {
                coordinates.add(new Coordinate(i ,j));
                contents[i][j] = '_';
            }
        }
        for(String word: words)
        {
            Collections.shuffle(coordinates);
            for(Coordinate coordinate: coordinates){
                int x =coordinate.x;
                int y =coordinate.y;
                Direction selectedDirection = getDirectionForFit(contents,word ,coordinate);
                if(selectedDirection!= null){
                    switch (selectedDirection){
                        case HORIZONTAL:
                            for(char c: word.toCharArray()){
                                contents[x][y++] =c;
                            }
                            break;
                        case VERTICAL:
                            for(char c: word.toCharArray()){
                                contents[x++][y] =c;
                            }
                            break;
                        case DIAGONAL:
                            for(char c: word.toCharArray()){
                                contents[x++][y++] =c;
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for(char c: word.toCharArray()){
                                contents[x][y--] =c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for(char c: word.toCharArray()){
                                contents[x--][y] =c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for(char c: word.toCharArray()){
                                contents[x--][y--] =c;
                            }
                            break;
                    }
                    break;
                }
            }
        }
        randomGrid(contents);
        return contents;
    }

    private void randomGrid(char[][] contents){
        int gridSize =contents[0].length;
        String allCapitalLetters ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i=0; i<gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (contents[i][j] == '_') {
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapitalLetters.length());
                    contents[i][j] = allCapitalLetters.charAt(randomIndex);
                }
            }
        }
    }

    public void displayGrid(char[][] contents){
        int gridSize =contents[0].length;
        for(int i=0; i<gridSize; i++){
            for(int j=0; j<gridSize; j++) {
                System.out.print(contents[i][j]+ " ");
            }
            System.out.println("");
        }
    }

    private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate) {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for (Direction direction : directions) {
            if (doesFit(contents, word, coordinate, direction)) {
                return direction;
            }
        }
        return null;
    }

    private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {
        int gridSize =contents[0].length;
        int wordLength =word.length();
        switch (direction) {

            case HORIZONTAL:
                if (coordinate.y + wordLength > gridSize) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y + i] != '_')
                        return false;
                }
                break;

            case VERTICAL:
                if (coordinate.x + word.length() > gridSize)
                    return false;
                for (int i = 0; i < word.length(); i++) {
                    if (contents[coordinate.x + i][coordinate.y] != '_')
                        return false;
                }
                break;

            case DIAGONAL:
                if (coordinate.y + word.length() > gridSize || coordinate.x + word.length() > gridSize)
                    return false;
                for (int i = 0; i < word.length(); i++) {
                    if (contents[coordinate.x + i][coordinate.y + i] != '_') return false;
                }
                break;

            case HORIZONTAL_INVERSE:
                if (coordinate.y < word.length()) return false;
                for (int i = 0; i < word.length(); i++) {
                    if (contents[coordinate.x][coordinate.y - i] != '_')
                        return false;
                }
                break;

            case VERTICAL_INVERSE:
                if (coordinate.x <word.length())
                    return false;
                for (int i = 0; i < word.length(); i++) {
                    if (contents[coordinate.x - i][coordinate.y] != '_')
                        return false;
                }
                break;

            case DIAGONAL_INVERSE:
                if (coordinate.y < word.length() || coordinate.x <word.length())
                    return false;
                for (int i = 0; i < word.length(); i++) {
                    if (contents[coordinate.x - i][coordinate.y - i] != '_') return false;
                }
                break;
        }
        return true;
    }
}
