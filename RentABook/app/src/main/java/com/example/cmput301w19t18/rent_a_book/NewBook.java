package com.example.cmput301w19t18.rent_a_book;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class NewBook extends AppCompatActivity implements OnClickListener {

    private Button scanButton;
    private TextView bookTitle, bookAuthor;

    public void onClick(View v){
        if(v.getId()==R.id.ScanButton){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbook);

        scanButton = findViewById(R.id.ScanButton);
        bookTitle = findViewById(R.id.TitleBox);
        bookAuthor = findViewById(R.id.AuthBox);

        scanButton.setOnClickListener(this);

    }
}