package com.altitude.careerintelligence.pret;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.careerintelligence.R;
import com.bumptech.glide.Glide;

import java.io.File;

public class PRETUploadCV extends AppCompatActivity {

    private Button bPretCVUpload;
    private TextView tvCVTitle;
    private ImageView ivCVImage;
    private static int PDF_INT = 1212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretupload_cv);

        bPretCVUpload = (Button) findViewById(R.id.bPretCVUpload);
        tvCVTitle = (TextView) findViewById(R.id.tvCVTitle);
        ivCVImage = (ImageView) findViewById(R.id.ivCVImage);

        ivCVImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, PDF_INT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = PRETUploadCV.this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Glide.with(PRETUploadCV.this).load(R.drawable.ic_pdf).into(ivCVImage);

                                tvCVTitle.setText(displayName);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        tvCVTitle.setText(displayName);
                        Glide.with(PRETUploadCV.this).load(R.drawable.ic_pdf).into(ivCVImage);


                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
