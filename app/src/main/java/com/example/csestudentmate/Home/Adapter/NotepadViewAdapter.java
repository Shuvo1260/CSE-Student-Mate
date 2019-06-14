package com.example.csestudentmate.Home.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.csestudentmate.Home.NotepadPage.ShowNote;
import com.example.csestudentmate.R;

public class NotepadViewAdapter extends RecyclerView.Adapter<NotepadViewAdapter.ViewHolder> {
    private String[] noteTitle;
    private String[] noteDescription;
    private boolean[] isChecked;
    private boolean anyItemChecked;
    private FragmentActivity fragmentActivity;
    private FloatingActionButton floatingActionButton;


    // Constructor
    public NotepadViewAdapter(FragmentActivity fragmentActivity, String[] noteTitle, String[] noteDescription, FloatingActionButton addNote) {
        this.fragmentActivity = fragmentActivity;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        isChecked = new boolean[noteTitle.length];
        anyItemChecked = false;
        floatingActionButton = addNote;
    }


    @NonNull
    @Override
    public NotepadViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notepad_view,viewGroup, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final CardView cardView = viewHolder.cardView;

        final TextView title, Description;

        title = cardView.findViewById(R.id.titleId);
        Description = cardView.findViewById(R.id.summeryId);

        title.setText(noteTitle[i]);
        Description.setText(noteDescription[i]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isChecked[i] && !anyItemChecked){
                    Intent intent = new Intent(fragmentActivity, ShowNote.class);
                    intent.putExtra("title", noteTitle[i]);
                    intent.putExtra("description", noteDescription[i]);
                    fragmentActivity.startActivity(intent);
                }
                else if(isChecked[i] && anyItemChecked){
                    cardView.setCardBackgroundColor(Color.WHITE);
                    isChecked[i] = false;
                }else if(anyItemChecked){
                    cardView.setCardBackgroundColor(Color.GRAY);
                    isChecked[i] = true;
                }

                for(int index = 0; index < noteTitle.length; index++){
                    if(isChecked[index]){
                        anyItemChecked = true;
                        break;
                    }else{
                        anyItemChecked = false;
                    }
                }

                if(!anyItemChecked){
                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_add_white));
                }

                notifyDataSetChanged();
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!anyItemChecked){
                    cardView.setCardBackgroundColor(Color.GRAY);
                    isChecked[i] = true;
                    anyItemChecked = true;
                    notifyDataSetChanged();
                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_delete_white));
                }

                return true;
            }
        });

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

    public boolean isDeletion(){
        return anyItemChecked;
    }
}
