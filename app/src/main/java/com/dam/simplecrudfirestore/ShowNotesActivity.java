package com.dam.simplecrudfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowNotesActivity extends AppCompatActivity {

    private static final String TAG = "ShowNotesActivity";

    private RecyclerView rvShowNotes;
    private FirebaseFirestore db;

    private NoteAdapter noteAdapter;
    private List<ModelNote> notesList;

    private void initUI(){
        rvShowNotes = findViewById(R.id.rvShowNotes);
        rvShowNotes.hasFixedSize();
        rvShowNotes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initFirestore(){
        db = FirebaseFirestore.getInstance();
    }

    private void initAdapter(){
        notesList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, notesList);
        rvShowNotes.setAdapter(noteAdapter);
    }

    public void readAllNotesFromFirestore(){
        db.collection("NOTES").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        notesList.clear();
                        for (DocumentSnapshot documentSnapshot: task.getResult()) {
                            ModelNote modelNote = new ModelNote(
                                    documentSnapshot.getString("id") ,
                                    documentSnapshot.getString("title") ,
                                    documentSnapshot.getString("content"));
                            notesList.add(modelNote);
                            Log.i(TAG, "onComplete: " + documentSnapshot.getId());
                        }
                        noteAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShowNotesActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        initUI();
        initFirestore();
        initAdapter();
        readAllNotesFromFirestore();
    }
}