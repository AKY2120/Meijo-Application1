package jp.ac.meijou.android.s241205066;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s241205066.databinding.ActivityMain2Binding;
import jp.ac.meijou.android.s241205066.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    private final ActivityResultLauncher<Intent>getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
             result -> {
                switch(result.getResultCode()){
                    case RESULT_OK -> {
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> "Result: " + text)
                                .ifPresent(text -> binding.textViewResult.setText(text));
                    }
                    case RESULT_CANCELED -> {
                        binding.textViewResult.setText("Result: Canceled");
                    }
                    default -> {
                        binding.textViewResult.setText("Result: Unknown(" + result.getResultCode() + ")");
                    }
                }
             }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.buttonA.setOnClickListener(View -> {
            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        binding.buttonB.setOnClickListener(View ->{
            var intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://abehiroshi.la.coocan.jp/"));
            startActivity(intent);
        });
        binding.buttonSend.setOnClickListener(View ->{
            var text = binding.editTextText2.getText().toString();
            var intent = new Intent(this, MainActivity4.class);
            intent.putExtra("title",text);
            startActivity(intent);
        });
        binding.buttonExec.setOnClickListener(View -> {
            var intent = new Intent(this,MainActivity4.class);
            getActivityResult.launch(intent);
        });
    }
}