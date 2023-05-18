package pl.op.danex11.gwselectfromlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView ABCTextView;

    private CodeScanner mCodeScanner;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textviewABC);
        this.ABCTextView = textView;
        textView.setText("🦓🦓🦓");
        TextViewCompat.setAutoSizeTextTypeWithDefaults(this.ABCTextView, 1);


        if (ContextCompat.checkSelfPermission((Context)this, "android.permission.CAMERA") == -1) {
            ActivityCompat.requestPermissions((Activity)this, new String[] { "android.permission.CAMERA" }, 123);
            return;
        }
        startScanning();
    }

    private void startScanning() {
        CodeScannerView codeScannerView = (CodeScannerView)findViewById(R.id.scanner_view);
        CodeScanner codeScanner = new CodeScanner((Context)this, codeScannerView);
        this.mCodeScanner = codeScanner;

        codeScanner.setDecodeCallback(new DecodeCallback() {
            public void onDecoded(final Result result) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int i;
                        int j;
                        int k;
                        //Bitmap bitmap;
                        MainActivity.this.ABCTextView.setText(result.getText());
                        TextViewCompat.setAutoSizeTextTypeWithDefaults(MainActivity.this.ABCTextView, 1);
                        ImageView qr_imageView = (ImageView)MainActivity.this.findViewById(R.id.qrview);
                        //data fro qr generator
                        String data = MainActivity.this.ABCTextView.getText().toString().trim();

                        QRCodeWriter qRCodeWriter = new QRCodeWriter();
                        //bitmap = Bitmap.createBitmap(j, k, Bitmap.Config.RGB_565);

                        try {
                            BitMatrix result = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, 512, 512);
                            Bitmap    bitmap = Bitmap.createBitmap(result.getWidth(), result.getHeight(), Bitmap.Config.ARGB_8888);

                            for (int y = 0; y < result.getHeight(); y++) {
                                for (int x = 0; x < result.getWidth(); x++) {
                                    if (result.get(x, y)) {
                                        bitmap.setPixel(x, y, Color.BLACK);
                                    }
                                }
                            }
                            qr_imageView.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                             Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
                        }



                    }
                });
            }
        });
        codeScannerView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                MainActivity.this.mCodeScanner.startPreview();
            }
        });
    }

    public void clickA(View paramView) {
        this.ABCTextView.setText("A");
    }

    public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
        super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
        if (paramInt == 123) {
            if (paramArrayOfint[0] == 0) {
                Toast.makeText((Context)this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                startScanning();
                return;
            }
            Toast.makeText((Context)this, "Camera permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onPause() {
        CodeScanner codeScanner = this.mCodeScanner;
        if (codeScanner != null)
            codeScanner.releaseResources();
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        CodeScanner codeScanner = this.mCodeScanner;
        if (codeScanner != null)
            codeScanner.startPreview();
    }
}
