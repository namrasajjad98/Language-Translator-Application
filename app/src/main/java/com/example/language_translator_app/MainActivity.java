package com.example.language_translator_app;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MainActivity extends AppCompatActivity {

    String TAG = "LanguageTranslator";
    EditText inputText;
    Spinner toSpinner, fromSpinner;
    TextView translatedText;
    Button translateButton;
    Translator translator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        toSpinner = findViewById(R.id.languageSpinner1);
        fromSpinner = findViewById(R.id.languageSpinner);
        translateButton = findViewById(R.id.translateButton);
        translatedText = findViewById(R.id.translatedText);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateText();
            }
        });
    }

    private void translateText() {
        String sourceLang = fromSpinner.getSelectedItem().toString();
        String targetLang = toSpinner.getSelectedItem().toString();
        String textToTranslate = inputText.getText().toString().trim();

        if (textToTranslate.isEmpty()) {
            translatedText.setText("Please enter text to translate.");
            return;
        }

        String sourceLangCode = TranslateLanguage.ENGLISH; // Default to English
        String targetLangCode = TranslateLanguage.ENGLISH; // Default to English

        // Determine source language code
        switch (sourceLang) {
            case "Spanish (es)":
                sourceLangCode = TranslateLanguage.SPANISH;
                break;
            case "French (fr)":
                sourceLangCode = TranslateLanguage.FRENCH;
                break;
            case "German (de)":
                sourceLangCode = TranslateLanguage.GERMAN;
                break;
            case "Chinese (zh)":
                sourceLangCode = TranslateLanguage.CHINESE;
                break;
            case "Hindi (hi)":
                sourceLangCode = TranslateLanguage.HINDI;
                break;
            case "Arabic (ar)":
                sourceLangCode = TranslateLanguage.ARABIC;
                break;
            case "Russian (ru)":
                sourceLangCode = TranslateLanguage.RUSSIAN;
                break;
            case "Japanese (ja)":
                sourceLangCode = TranslateLanguage.JAPANESE;
                break;
            case "Portuguese (pt)":
                sourceLangCode = TranslateLanguage.PORTUGUESE;
                break;
            case "Urdu (ur)":
                sourceLangCode = TranslateLanguage.URDU; // Code for Urdu
                break;
            // Add more languages here if needed
        }

        // Determine target language code
        switch (targetLang) {
            case "Spanish (es)":
                targetLangCode = TranslateLanguage.SPANISH;
                break;
            case "French (fr)":
                targetLangCode = TranslateLanguage.FRENCH;
                break;
            case "German (de)":
                targetLangCode = TranslateLanguage.GERMAN;
                break;
            case "Chinese (zh)":
                targetLangCode = TranslateLanguage.CHINESE;
                break;
            case "Hindi (hi)":
                targetLangCode = TranslateLanguage.HINDI;
                break;
            case "Arabic (ar)":
                targetLangCode = TranslateLanguage.ARABIC;
                break;
            case "Russian (ru)":
                targetLangCode = TranslateLanguage.RUSSIAN;
                break;
            case "Japanese (ja)":
                targetLangCode = TranslateLanguage.JAPANESE;
                break;
            case "Portuguese (pt)":
                targetLangCode = TranslateLanguage.PORTUGUESE;
                break;
            case "Urdu (ur)":
                targetLangCode = TranslateLanguage.URDU; // Code for Urdu
                break;
            // Add more languages here if needed
        }

        Log.d(TAG, "Source Language Code: " + sourceLangCode + ", Target Language Code: " + targetLangCode + ", Text: " + textToTranslate);

        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLangCode)
                .setTargetLanguage(targetLangCode)
                .build();

        translator = Translation.getClient(options);

        // Download model if needed
        translator.downloadModelIfNeeded(new DownloadConditions.Builder().requireWifi().build())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        translator.translate(textToTranslate)
                                .addOnSuccessListener(new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedTextResult) {
                                        translatedText.setText(translatedTextResult);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Translation failed: " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Model couldn't be downloaded: " + e.getMessage());
                    }
                });
    }
}