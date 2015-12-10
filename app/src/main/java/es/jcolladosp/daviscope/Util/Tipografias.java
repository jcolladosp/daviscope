package es.jcolladosp.daviscope.Util;

import android.content.Context;
import android.graphics.Typeface;


public class Tipografias {

    public static Typeface getTypeface(Context context, String type) {
        Typeface mTypeFace;

            mTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/All.ttf");

        return mTypeFace;
    }
}
