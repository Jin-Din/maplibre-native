package org.maplibre.android.maps.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import org.maplibre.android.R;
import org.maplibre.android.maps.*;
import org.maplibre.android.camera.*;


public class ZoomInOutView extends LinearLayout {

    private Button btnZoomIn;
    private Button btnZoomOut;

    private Runnable onZoomInAction;
    private Runnable onZoomOutAction;

    public ZoomInOutView(Context context) {
        super(context);
        init(context, null);
    }

    public ZoomInOutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZoomInOutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        // 设置垂直排列
        setOrientation(VERTICAL);
        setElevation(4f);
        //设置内边距，避免按钮紧贴圆角（可根据视觉效果调整，这里设0让按钮填满）
        int padding = (int) context.getResources().getDimension(R.dimen.maplibre_four_dp);
        setPadding(padding, padding, padding, padding);

        // 如果 LayoutParams 还未设置（即外部没有通过 XML 指定宽高），则设置默认 wrap_content
        if (getLayoutParams() == null) {
            setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            ));
        }

        // 加载按钮布局
        LayoutInflater.from(context).inflate(R.layout.maplibre_zoom_in_out_view, this, true);

        // 应用圆角阴影背景
        setBackgroundResource(R.drawable.zoom_in_out_rounded_layout_bg);


        btnZoomIn = findViewById(R.id.btn_zoom_in);
        btnZoomOut = findViewById(R.id.btn_zoom_out);

        btnZoomIn.setOnClickListener(v -> {
            if (onZoomInAction != null) {
                onZoomInAction.run();
            }
        });

        btnZoomOut.setOnClickListener(v -> {
            if (onZoomOutAction != null) {
                onZoomOutAction.run();
            }
        });
    }
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            setAlpha(1.0f);
            setVisibility(View.VISIBLE);

        } else {

            setAlpha(0.0f);
            setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置放大按钮的点击回调
     */
    public void setOnZoomInClickListener(Runnable action) {
        this.onZoomInAction = action;
    }

    /**
     * 设置缩小按钮的点击回调
     */
    public void setOnZoomOutClickListener(Runnable action) {
        this.onZoomOutAction = action;
    }
}