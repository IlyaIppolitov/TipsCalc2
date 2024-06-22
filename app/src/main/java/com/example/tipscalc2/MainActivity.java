package com.example.tipscalc2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAmount;
    private SeekBar seekBarTip;
    private TextView textViewTipPercentage;
    private TextView textViewTipAmount;
    private TextView textViewTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Определение вьюшек через их идентификтор (id)
        editTextAmount = findViewById(R.id.editTextAmount);
        seekBarTip = findViewById(R.id.seekBarTip);
        textViewTipPercentage = findViewById(R.id.textViewTipPercentage);
        textViewTipAmount = findViewById(R.id.textViewTipAmount);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);

        // Подписка на событие изменения состояние SeekBar
        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int tipPercentage = progress + 10;
                textViewTipPercentage.setText(tipPercentage + "%");
                calculateTipAndTotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Подписка на изменение текст блока с суммой заказа
        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calculateTipAndTotal();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    // Функция расчёта суммы чаевых и итоговой суммы
    private void calculateTipAndTotal() {
        String amountText = editTextAmount.getText().toString();
        if (amountText.isEmpty()) {
            textViewTipAmount.setText("0.00");
            textViewTotalAmount.setText("0.00");
            return;
        }

        double amount = Double.parseDouble(amountText);
        int tipPercentage = seekBarTip.getProgress() + 10;
        double tipAmount = amount * tipPercentage / 100;
        double totalAmount = amount + tipAmount;

        textViewTipAmount.setText(String.format("%.2f", tipAmount));
        textViewTotalAmount.setText(String.format("%.2f", totalAmount));
    }
}
