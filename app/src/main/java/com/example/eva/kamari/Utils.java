package com.example.eva.kamari;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by danleyb2 on 9/3/17.
 */

public class Utils {

    static Drawable loadDrawable(Context context, String name) {
        Drawable drawable = context.getResources()
                .getDrawable(
                        context.getResources().getIdentifier(
                                name,
                                "drawable",
                                context.getPackageName()
                        )
                );

        return drawable;

    }
}
