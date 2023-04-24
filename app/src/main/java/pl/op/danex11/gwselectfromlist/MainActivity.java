package pl.op.danex11.gwselectfromlist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView list;
    TextView ABCTextView;
    ArrayAdapter adapter ;
    String selectedItem;

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ABCTextView = findViewById(R.id.textviewABC);
//        Button A =  (Button) findViewById(R.id.buttonA);
//        Button B =  (Button) findViewById(R.id.buttonB);
//        Button C =  (Button) findViewById(R.id.buttonC);

      String[] carsArray = {"AAA", "AAB", "AAC", "AAD", "AAE", "AAF", "AAG", "AAHh", "AAi"};
//
//        ArrayList carList = new ArrayList(Arrays.asList(carsArray));
//        adapter = new ArrayAdapter(this, R.layout.single_row, carList);

        ArrayList<String> carL = new ArrayList<String>();
        carL.addAll( Arrays.asList(carsArray) );
        adapter = new ArrayAdapter<String>(this, R.layout.single_row, carL);

//
        Log.e("mytag", "listview ID: " + String.valueOf(R.id.list_view));
        list = findViewById(R.id.list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                // MyClass selItem = (MyClass) myList.getSelectedItem(); //
                selectedItem = (String) list.getItemAtPosition(position); //getter method
                Toast.makeText(getApplicationContext(), "selected Item Name is " + selectedItem, Toast.LENGTH_LONG).show();
                ABCTextView.setText(selectedItem);
            }
    });

        previewView = findViewById(R.id.activity_main_previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        requestCamera();
    }

    public void  clickA(View view){
        ABCTextView.setText("A");
    }

   public void  clickB(View view){
       ABCTextView.setText("B");
    }


    public void  clickC(View view){
        ABCTextView.setText("C");
    }



    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    private void startCamera() {
        Toast.makeText(this, "Starting camera", Toast.LENGTH_SHORT).show();
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindCameraPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    Toast.makeText(this, "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, ContextCompat.getMainExecutor(this));
        }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
    }







    }

