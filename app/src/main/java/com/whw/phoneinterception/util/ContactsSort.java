package com.whw.phoneinterception.util;

import java.util.Comparator;

/**
 * Created by wuhaiwen on 2017/9/6.
 */

public class ContactsSort implements Comparator {
    public ContactsSort() {
    }

    @Override
    public int compare(Object o1, Object o2) {
        String name1 = (String) o1;
        String name2 = (String) o1;
        char[] c1 = name1.toCharArray();
        char[] c2 = name2.toCharArray();
        for (int i = 0; i < (c1.length > c2.length ? c2.length : c1.length); i++) {
            if (c1[i] > c2[i]) {
                return 1;
            }
            if (c1[i] < c2[i]) {
                return -1;
            }
        }
        return c1.length > c2.length ? 1 : -1;
    }
}
