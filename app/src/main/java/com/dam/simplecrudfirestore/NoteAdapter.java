package com.dam.simplecrudfirestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ShowNotesActivity allNotesActivity;
    private List<ModelNote> notesListe;

    public NoteAdapter() {
    }

    public NoteAdapter(ShowNotesActivity allNotesActivity, List<ModelNote> notesListe) {
        this.allNotesActivity = allNotesActivity;
        this.notesListe = notesListe;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNoteTitle, tvNoteContent;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteContent = itemView.findViewById(R.id.tvNoteContent);

        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(allNotesActivity).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.tvNoteTitle.setText(notesListe.get(position).getTitle());
        holder.tvNoteContent.setText(notesListe.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
