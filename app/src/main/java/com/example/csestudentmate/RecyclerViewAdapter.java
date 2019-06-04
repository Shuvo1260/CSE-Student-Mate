package com.example.csestudentmate;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private String[] noteTitle;
    private String[] noteSummery;


    // Constructor
    public RecyclerViewAdapter(String[] noteTitle, String[] noteSummery) {
        this.noteTitle = noteTitle;
        this.noteSummery = noteSummery;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notepad_view,viewGroup, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CardView cardView = viewHolder.cardView;

        TextView title, summery;

        title = cardView.findViewById(R.id.titleId);
        summery = cardView.findViewById(R.id.summeryId);

        title.setText(noteTitle[i]);
        summery.setText(noteSummery[i]);
    }

    @Override
    public int getItemCount() {
        return noteTitle.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
