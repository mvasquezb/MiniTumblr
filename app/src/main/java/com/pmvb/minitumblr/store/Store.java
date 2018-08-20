package com.pmvb.minitumblr.store;

import android.content.Context;

import com.pmvb.minitumblr.model.MyObjectBox;

import io.objectbox.BoxStore;

public class Store {
    private static Store ourInstance;
    private final BoxStore boxStore;

    public static Store getInstance() {
        return ourInstance;
    }

    private Store(Context context) {
        this.boxStore = MyObjectBox.builder().androidContext(context).build();
    }

    public static void create(Context context) {
        ourInstance = new Store(context);
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
