package com.uoa.compsci_702_g12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.uoa.compsci_702_g12.MainActivity.OCR_TEXT;

public class TextActivity extends AppCompatActivity {

    EditText ocrTextView;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        String ocrText = getIntent().getExtras().getString(OCR_TEXT);
        ocrTextView = (EditText) findViewById(R.id.ocr_edit_text);
        ocrTextView.setText(ocrText);

        shareButton = (Button) findViewById(R.id.share_btn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToShare = ocrTextView.getText().toString();
                // share the textToShare implicitly with other applications
            }
        });

    }
}
