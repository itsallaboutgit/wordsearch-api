package com.JavaWordSearchProgram.wordsearchapi.controllers;

import com.JavaWordSearchProgram.wordsearchapi.Services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController("/")
public class WordSearchController {

    @Autowired
    WordGridService wordGridService;

    @GetMapping("/wordgrid")
    public String createWordGrid(@RequestParam int gridSize, @RequestParam String wordlist) {
        List<String> words = Arrays.asList(wordlist.split(","));
        char[][]grid =wordGridService.generateGrid(gridSize,words);
        String gridToString="";
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                gridToString+= grid[i][j] + " ";
            }
            gridToString+="\r\n";
        }
        return gridToString;
    }
}
