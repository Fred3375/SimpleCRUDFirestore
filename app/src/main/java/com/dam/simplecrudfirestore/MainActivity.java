package com.dam.simplecrudfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText etNoteTitle;
    private EditText etNoteContent;
    private Button btnSave;
    private Button btnShowAllNotes;

    private FirebaseFirestore db;

    private void initUI(){
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        btnSave = findViewById(R.id.btnSave);
        btnShowAllNotes = findViewById(R.id.btnShowAllNotes);
    }

    private void initFirebaseTools(){
        db = FirebaseFirestore.getInstance();
    }

    private void onClickBtnSaveNote(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "SAVE", Toast.LENGTH_LONG).show();
                String id = UUID.randomUUID().toString();
                String title = etNoteTitle.getText().toString();
                String content = etNoteContent.getText().toString();

                createDocumentInFirestore(id, title, content);
            }
        });
    }

    private void onBtnClickShowAllNotes(){
        btnShowAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "SHOW ALL", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, ShowNotesActivity.class));
            }
        });
    }

    private void createDocumentInFirestore(String id, String title, String content){
        if (!title.isEmpty() && !content.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("title", title);
            map.put("content", content);
            db.collection("NOTES").document(id).set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Document added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error adding document : " + e, Toast.LENGTH_SHORT).show();
                        }
                    })
                ;
        } else {
            Toast.makeText(MainActivity.this, "Empty fields aren't allowed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initFirebaseTools();

        onClickBtnSaveNote();
        onBtnClickShowAllNotes();
    }
}