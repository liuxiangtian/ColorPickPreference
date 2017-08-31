package com.lxt.colorpick;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class ColorPickDialog extends AlertDialog implements OnItemClickListener {

    private ColorAdaptor colorAdaptor;

    protected ColorPickDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    protected ColorPickDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected ColorPickDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.color_picker_list, null);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        colorAdaptor = new ColorAdaptor(context, Color.MAGENTA);
        colorAdaptor.setOnItemClickListener(this);
        recyclerView.setAdapter(colorAdaptor);
        setView(view);
        getWindow().getDecorView().setBackgroundResource(R.drawable.dialog_backgroud);
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public static ColorPickDialog create(Context context) {
        return new ColorPickDialog(context);
    }


    public void show(int currentColor) {
        this.currentColor = currentColor;
        colorAdaptor.replaceColor(currentColor);
        show();
    }

    private int currentColor;

    @Override
    public void onItemClick(int currentColor) {
        this.currentColor = currentColor;
        Log.i("main", "onItemClick: "+currentColor);
    }
}
