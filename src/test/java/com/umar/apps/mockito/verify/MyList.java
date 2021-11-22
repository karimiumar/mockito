package com.umar.apps.mockito.verify;

import java.util.AbstractList;

public class MyList extends AbstractList<String> {

    @Override
    public String get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
