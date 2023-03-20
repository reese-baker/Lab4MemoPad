package com.example.lab4memopad;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.lab4memopad.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = new DatabaseHandler(this, null, null, 1);
        updateMemoList();

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memoText = binding.memoText.getText().toString();
                if (!memoText.isEmpty()) {
                    db.addMemo(new Memo(memoText));
                    updateMemoList();
                    binding.memoText.getText().clear();
                }
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memoIdString = binding.memoDelete.getText().toString();
                if (!memoIdString.isEmpty()) {
                    int memoId = Integer.parseInt(memoIdString);
                    db.deleteMemo(memoId);
                    updateMemoList();
                    binding.memoDelete.getText().clear();
                }
            }
        });
    }

    private void updateMemoList() {
        String memos = db.getAllMemos();
        binding.textView.setText(memos);
    }
}