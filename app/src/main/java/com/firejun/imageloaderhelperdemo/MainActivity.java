package com.firejun.imageloaderhelperdemo;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.firejun.imageloaderhelperdemo.util.AliImageLoaderUtil;
import com.firejun.imageloaderhelperdemo.util.SizeUtil;

public class MainActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "http://img0.ph.126.net/oUV3P2J3oNbWrYHOO73tVg==/3017411750438813949.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int width, height;
        width = SizeUtil.getScreenWidthInPx(this) - SizeUtil.dp2px(10);
        height = width * 6 / 5;
        ImageView imageView = (ImageView) findViewById(R.id.main_image_view);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        imageView.setLayoutParams(layoutParams);

        AliImageLoaderUtil.loadImage(IMAGE_URL, R.drawable.il_placeholder_default, imageView);
    }
}
