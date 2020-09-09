package com.abmodbusmaster;

public class MBTaskCallbackInterface {
    public interface MBTaskCallback
    {
        void UpdateMBUI(String val1, String val2, String val3);
    }

    public static MBTaskCallback MBtaskCallback;

    public static MBTaskCallback getHandler() {
        return MBtaskCallback;
    }
}
