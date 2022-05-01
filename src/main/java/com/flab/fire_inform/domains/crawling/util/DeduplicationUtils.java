package com.flab.fire_inform.domains.crawling.util;

import java.util.ArrayList;
import java.util.List;

public class DeduplicationUtils {

    public static <T> List<T> deduplicate(List<T> addList, List<T> presentList) {
        ArrayList<T> deduplicatedList = new ArrayList<>();
        for (T t : addList) {
            if (!presentList.contains(t)) {
                deduplicatedList.add(t);
            }
        }

        return deduplicatedList;
    }
}
