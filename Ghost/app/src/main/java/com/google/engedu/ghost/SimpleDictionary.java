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

package com.google.engedu.ghost;

import android.support.v4.app.FragmentManagerNonConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private Random random = new Random();
    private int start ;
    private int end ;
    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if( prefix == "" )
        {
            int index = random.nextInt(words.size());
            return  words.get(index);
        }else{
            return binarySearch(prefix);
        }
//        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = "";
        List<String> prefList = words.subList(start, end);

        return selected;
    }

    private String binarySearch(String prefix){
        String result = "";
        int low = 0;
        int high = words.size();



        while (low < high){
            int mid = (low + high)/2;
            result = words.get(mid);

            if( result.startsWith(prefix) )
            {
//                low = mid;
//                hi
                start = mid;
                end = high;
                return result;
            }else if( result.compareToIgnoreCase(prefix) > 0 ){
                high = mid;
            }else {
                low = mid +1;
            }
        }

        return  "";
    }
}
