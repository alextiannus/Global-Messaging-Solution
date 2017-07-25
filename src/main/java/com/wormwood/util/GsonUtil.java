package com.wormwood.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Donnie on 2017/2/21.
 */
public class GsonUtil {

    private static class GsonHolder {
        private static final Gson INSTANCE = new Gson();
        private static final Gson INSTACE_HTML_TAG = new GsonBuilder().disableHtmlEscaping().create();
    }

    public static Gson getInstance() {
        return GsonHolder.INSTANCE;
    }

    public static Gson getInstanceHtml() {
        return GsonHolder.INSTACE_HTML_TAG;
    }
}
