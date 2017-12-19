package com.liyi.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.liyi.sutils.utils.DensityUtil;
import com.liyi.sutils.utils.graphic.ImageUtil;
import com.liyi.sutils.utils.QRCodeUtil;

/**
 * 二维码页面
 */
public class QRImageActivity extends Activity {
    private ImageView iv_qr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrimage);
        iv_qr = (ImageView) findViewById(R.id.iv_qicode);
        Bitmap qrImg = QRCodeUtil.createQRCode("liyi", DensityUtil.dp2px(400), DensityUtil.dp2px(400), 2);
        Bitmap qrLogo = QRCodeUtil.addLogoToQRCode(qrImg, ImageUtil.drawable2Bitmap(R.drawable.img_isooqi_holder));
        iv_qr.setImageBitmap(qrLogo);
    }
}
