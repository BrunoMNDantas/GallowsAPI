/***********************************************************************************
 Copyright (c) 2017, ISEL

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 **************************************************************************************/
package pt.ipl.isel.gallows_game_bot.transversal;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    public static boolean equals(Object a, Object b){
        if(a==b)
            return true;

        if((a == null && b != null) || (a != null && b == null))
            return false;

        return a.equals(b);
    }

    public static <T> boolean equalsCollections(Collection<T> a, Collection<T> b){
        if(equals(a,b))
            return true;

        if(a.size() != b.size())
            return false;

        List<T> aList = new LinkedList<>(a);
        List<T> bList = new LinkedList<>(b);

        for(int i=0; i<aList.size(); ++i)
            if(!equals(aList.get(i), bList.get(i)))
                return false;

        return true;
    }

}