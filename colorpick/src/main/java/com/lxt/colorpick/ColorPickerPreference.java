package com.lxt.colorpick;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class ColorPickerPreference extends Preference implements DialogInterface.OnDismissListener {

    private ImageView currentImage;
    private int currentColor;
    private GradientDrawable currentDrawable;

    private boolean isCircle;
    private float radius;

    public ColorPickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public ColorPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ColorPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ColorPickerPreference(Context context) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        setWidgetLayoutResource(R.layout.color_current_layout);
    }

    @Override
    protected void onBindView(final View view) {
        super.onBindView(view);

        currentImage = view.findViewById(R.id.image_view);

        currentColor = getSharedPreferences().getInt(getKey(), Color.BLACK);
        currentDrawable = new GradientDrawable();
        currentDrawable.setColor(currentColor);

            currentDrawable.setShape(GradientDrawable.OVAL);

        currentImage.setImageDrawable(currentDrawable);
    }

    private ColorPickDialog mDialog;

    @Override
    protected void onClick() {
        if (mDialog != null && mDialog.isShowing()) return;

        showDialog();
    }

    protected void showDialog() {

        if (mDialog == null) {
            mDialog = ColorPickDialog.create(getContext());
        }

        mDialog.setOnDismissListener(this);
        mDialog.show(currentColor);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        currentColor = mDialog==null?currentColor:mDialog.getCurrentColor();
        getEditor().putInt(getKey(), currentColor).commit();
        currentDrawable.setColor(currentColor);
    }

}
