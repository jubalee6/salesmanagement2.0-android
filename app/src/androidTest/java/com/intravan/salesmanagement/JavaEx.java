package com.intravan.salesmanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaEx {

    ArrayList<Integer> items = new ArrayList<Integer>(Arrays.asList(1, 2, 3));

    public void loopTest() {
        for (Integer item: items) {
            System.out.println(item);
        }

        // 1
        // 2
        // 3
    }
}
