package com.abmodbusmaster;

public class ABTaskCallbackInterface {
    public interface ABTaskCallback
    {
        void UpdateABUI(String val1, String val2, String val3);
    }

    public static ABTaskCallback ABtaskCallback;

    public static ABTaskCallback getHandler() {
        return ABtaskCallback;
    }
}
