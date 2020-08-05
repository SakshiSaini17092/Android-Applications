/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private static int wordLength = DEFAULT_WORD_LENGTH;
    // MileStone 1
    private ArrayList<String> wordlist ;
    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;
    private HashMap<Integer, ArrayList<String>> sizeToWords;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        sizeToWords = new HashMap<>();

        String line;
        wordlist = new ArrayList<>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);
            String sorted = sortLetters(word);
            wordSet.add(word);
            if( lettersToWord.containsKey(sorted) ){
                ArrayList<String> value = lettersToWord.get(sorted);
                value.add(word);
            }else{
                ArrayList<String> value = new ArrayList<>();
                value.add(word);
                lettersToWord.put(sorted, value);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {

        if( word.contains(base) )
            return false;
        return true;
    }

    private static String sortLetters(String word){
        char[] charArray = word.toCharArray();
        Arrays.sort(charArray);
        return  new String(charArray);
    }
    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        targetWord = sortLetters(targetWord);
        for( String word : wordlist ){
            String sortedWord = sortLetters(word);
            if( word.length() == targetWord.length() && targetWord.equals(sortedWord) )
                result.add(word);
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();


        return result;
    }

    public String pickGoodStarterWord() {

        if( wordLength < MAX_WORD_LENGTH ){
            wordLength++;
        }

        boolean foundWord = false;
        while (!foundWord){

            int max = wordlist.size();
            int randomPoint = random.nextInt(max);
            String word = wordlist.get(randomPoint);
            int count = 0;

            word = sortLetters(word);
            if( word.length() == wordLength ){
                for( String ana : wordlist ){
                    String sorted = sortLetters(ana);
                    if( sorted.equals(word) )
                        count ++;
                }

                if( count >= MIN_NUM_ANAGRAMS )
                    return word;
            }
        }
        return "STOP";
    }
}
