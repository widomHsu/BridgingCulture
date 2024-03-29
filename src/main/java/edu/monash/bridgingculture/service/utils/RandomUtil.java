package edu.monash.bridgingculture.service.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Take a specified number of random elements from a given collection.
 */
@Component
public class RandomUtil {
    /**
     * Returns a list of randomly selected elements from the given list within the specified range,
     * with a probability controlled by the provided seed value.
     *
     * @param list the list from which elements will be randomly selected
     * @param low the lower bound (inclusive) of the range for selecting elements
     * @param high the upper bound (inclusive) of the range for selecting elements
     * @param seed the probability seed controlling the selection of elements
     * @return a list containing randomly selected elements from the original list
     */
    public List getRandomElement(List list, int low, int high, double seed){
        Random random = new Random();
        // [0, n] -> [l, h]
        int number = random.nextInt(high-low+1) + low;

        Iterator iterator = list.iterator();
        List res = new ArrayList<>();
        while(res.size() != number){
            if(iterator.hasNext()){
                Object next = iterator.next();
                if(random.nextDouble() < seed){
                    res.add(next);
                    iterator.remove();
                }
            }else{
                iterator = list.iterator(); // reset iterator
            }
        }
        return res;
    }
}
