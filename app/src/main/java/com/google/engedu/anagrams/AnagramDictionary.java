package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet;// Added this
    private HashMap<String, ArrayList<String>> lettersToWord;// Added this
    private ArrayList<String> wordList; // Added this
    private HashMap<String,ArrayList<String>> sizeToWords;


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        wordList = new ArrayList<>();// Added this
        sizeToWords = new HashMap<>();
        wordSet = new HashSet<>();//added
        lettersToWord = new HashMap<>();
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if (lettersToWord.containsKey(sortedWord)) {
                ArrayList<String> listWords = lettersToWord.get(sortedWord);
                listWords.add(word);
                lettersToWord.put(sortedWord, listWords);
            } else {
                ArrayList<String> listWords = new ArrayList<>();
                listWords.add(word);
                lettersToWord.put(sortedWord, listWords);
            }// Added this

        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);

    }

    public ArrayList<String> getAnagrams(String targetWord) {
       // ArrayList<String> result = new ArrayList<String>();
        String word;
        String sortedTargetWord = sortLetters(targetWord);// added this
       // for (String word : wordList) {
          //  if (sortLetters(word).equals(sortedTargetWord)) {
               // Log.v("Game", word);
            //    result.add(word);

        return lettersToWord.get(sortLetters(targetWord));

    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<>();
        char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (char letter : alphabets) {
            if (lettersToWord.containsKey(sortLetters(word + letter))) {
                ArrayList<String> tempArray = lettersToWord.get(sortLetters(word + letter));
                for (String anagramWord : tempArray) {
                    Log.v("game", anagramWord);
                    result.add(anagramWord);
                }
            }
        }
        return result;
    }
    public String pickGoodStarterWord() {
        int length = 0;
        while (length < MIN_NUM_ANAGRAMS) {
            String word = wordList.get(new Random().nextInt(wordList.size()));
            length = lettersToWord.get(sortLetters(word)).size();
            if (length >= MIN_NUM_ANAGRAMS) {
                return word;
            }
        }
        return "stop";
    }
    private String sortLetters(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        String sortedLetters = new String(letters);
        return sortedLetters;
    }// added this
}
