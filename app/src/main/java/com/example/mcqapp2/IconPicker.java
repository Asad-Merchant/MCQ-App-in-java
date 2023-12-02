package com.example.mcqapp2;

public class IconPicker {
    static int[] icon = {R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,R.drawable.icon5,R.drawable.icon6,R.drawable.icon7};
    static int currentIcon = 0;

    public static int getCurrentIcon() {
        currentIcon = (currentIcon+1)%icon.length;
        return icon[currentIcon];
    }
}
