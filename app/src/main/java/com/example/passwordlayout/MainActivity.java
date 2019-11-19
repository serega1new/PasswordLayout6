package com.example.passwordlayout;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private SystemConvert mSystemConvert;
    private SystemPassword mSystemPassword;

    private TextView mResultTextView;
    private TextView mSymbolsTextView;

    private EditText mSourceTextView;

    private View mCopyButton;
    private ImageView mImageLock;

    private Button mTest;

    private CheckBox mUppercaseCheckBox;
    private CheckBox mNumberCheckBox;
    private CheckBox mSpecialCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSystemConvert = new SystemConvert(
          getResources().getString(R.string.dict_rus),
          getResources().getString(R.string.dict_eng)
        );

        mSystemPassword = new SystemPassword(
                getResources().getString(R.string.dict_rus),
                getResources().getString(R.string.dict_num),
                getResources().getString(R.string.dict_spec)
        );

        mResultTextView = findViewById(R.id.result_text);
        mSymbolsTextView = findViewById(R.id.simvols);

        mSourceTextView = findViewById(R.id.source_text);

        mImageLock = findViewById(R.id.box_lock);

        mTest = findViewById(R.id.test);
        mCopyButton = findViewById(R.id.button_copy);
        mCopyButton.setEnabled(false);

        mUppercaseCheckBox = findViewById(R.id.isUppercase);
        mNumberCheckBox = findViewById(R.id.isNumber);
        mSpecialCheckBox = findViewById(R.id.isSpecial);


        mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText(
                        MainActivity.this.getString(R.string.clipboard_title),
                        mResultTextView.getText()
                ));
                Toast.makeText(MainActivity.this, R.string.message_copied, Toast.LENGTH_SHORT).show();
            }
        }
        );

        mSourceTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mResultTextView.setText(mSystemConvert.convert(s));
                mSymbolsTextView.setText(getResources().getQuantityString(R.plurals.simvols, mResultTextView.getText().length(), mResultTextView.getText().length() ));
                mImageLock.setImageLevel(mSystemConvert.getValueSafety(mResultTextView.getText())*1000);
                mCopyButton.setEnabled(s.length() > 0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSystemPassword.setUppercase(mUppercaseCheckBox.isChecked());
                mSystemPassword.setNumbers(mNumberCheckBox.isChecked());
                mSystemPassword.setSpecial(mSpecialCheckBox.isChecked());
                mResultTextView.setText(mSystemPassword.getGeneratePassword());


            }
        });


    }
}
