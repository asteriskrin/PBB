package com.example.pbb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pbb.jsonparser.JSONParserActivity;
import com.example.pbb.xmlparser.XMLParserActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoToXMLParser = findViewById(R.id.btnGoToXMLParser);
        btnGoToXMLParser.setOnClickListener(buttonDo);

        Button btnGoToJSONParser = findViewById(R.id.btnGoToJSONParser);
        btnGoToJSONParser.setOnClickListener(buttonDo);
    }

    @SuppressLint("NonConstantResourceId")
    View.OnClickListener buttonDo = view -> {
        switch (view.getId()) {
            case R.id.btnGoToXMLParser: goToXMLParser(); break;
            case R.id.btnGoToJSONParser: goToJSONParser(); break;
        }
    };

    private void goToXMLParser() {
        Intent intent = new Intent(this, XMLParserActivity.class);
        startActivity(intent);
    }

    private void goToJSONParser() {
        Intent intent = new Intent(this, JSONParserActivity.class);
        startActivity(intent);
    }
}