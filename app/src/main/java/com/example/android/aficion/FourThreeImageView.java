package com.example.android.aficion;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;

/**
 * Created by Sylvana on 4/5/2018.
 */

public class FourThreeImageView extends AppCompatImageView {

    public FourThreeImageView(Context context) {
        super(context);
    }

    public FourThreeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FourThreeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int fourThreeHeight = MeasureSpec.getSize(widthMeasureSpec)*5/9;
        int fourThreeHeightSpec = MeasureSpec.makeMeasureSpec(fourThreeHeight,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, fourThreeHeightSpec);
    }
}
