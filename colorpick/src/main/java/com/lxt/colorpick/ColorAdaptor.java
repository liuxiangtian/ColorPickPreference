package com.lxt.colorpick;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class ColorAdaptor extends RecyclerView.Adapter<ColorAdaptor.VH> {

    public ColorAdaptor(Context context, int currentColor) {

        TypedArray ta = context.obtainStyledAttributes(R.styleable.color_picker_preference);

        int colorsId = ta.getResourceId(R.styleable.color_picker_preference_colors, R.array.color_picker_colors);
        colors = context.getResources().getIntArray(colorsId);

        ta.recycle();

        this.currentColor = currentColor;
    }

    public void replaceColor(int currentColor) {
        this.currentColor = currentColor;
        notifyDataSetChanged();
    }

    private int[] colors;
    private int currentColor;

    @Override
    public ColorAdaptor.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_color_picker, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(ColorAdaptor.VH holder, int position) {
        holder.currentDrawable.setColor(colors[position]);
        holder.checkBox.setChecked(colors[position]==currentColor);
        holder.checkBox.setVisibility((colors[position]==currentColor)?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return colors.length;
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CheckBox checkBox;
        public SquareImageView squareImage;
        public GradientDrawable currentDrawable;
        public VH(View itemView) {
            super(itemView);
            currentDrawable = new GradientDrawable();
            squareImage = itemView.findViewById(R.id.item_image);
            checkBox = itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(this);
            checkBox.setOnClickListener(this);
                currentDrawable.setShape(GradientDrawable.RECTANGLE);
                currentDrawable.setCornerRadius(8);
            squareImage.setImageDrawable(currentDrawable);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            currentColor = colors[position];
            notifyDataSetChanged();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(currentColor);
            }
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
