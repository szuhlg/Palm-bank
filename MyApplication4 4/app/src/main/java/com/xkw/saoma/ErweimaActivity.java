package com.xkw.saoma;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hlg.myapplication.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.HashMap;

public class ErweimaActivity extends Activity {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    /**
     * 显示扫描结果
     */
    private TextView mTextView ;
    /**
     * 显示扫描拍的图片
     */
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erweima);

        mTextView = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
        Button b_qr=(Button)findViewById(R.id.b_create);
        b_qr.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                HashMap hints = new HashMap();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                try {
                   EditText edit=(EditText)findViewById(R.id.editQr);
                    DisplayMetrics  dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                    int screenWidth = dm.widthPixels;
                    BitMatrix bitMatrix = new MultiFormatWriter().encode(edit.getText().toString(), BarcodeFormat.QR_CODE, screenWidth*2/3,screenWidth*2/3, hints);
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();
                    int[] pixels = new int[width * height];
                    for (int y = 0; y < height; y++) {
                        int offset = y * width;
                        for (int x = 0; x < width; x++) {
                            pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK :Color.WHITE;
                        }
                    }

                    mImageView.setImageBitmap( Bitmap.createBitmap(pixels,screenWidth*2/3,screenWidth*2/3, Bitmap.Config.RGB_565));
                }catch (Exception e)
                {
                }
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    mTextView.setText(bundle.getString("result"));
                    //显示
                    mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }

}
