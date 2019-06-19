package com.example.csestudentmate.Home.NotepadPage.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csestudentmate.Home.NotepadPage.Features.Note;
import com.example.csestudentmate.Home.NotepadPage.Features.ShowNote;
import com.example.csestudentmate.R;

import java.util.ArrayList;
import java.util.List;

public class NotepadViewAdapter extends RecyclerView.Adapter<NotepadViewAdapter.ViewHolder> {

    private List<Note> noteList;
    private List<Boolean> isChecked;
    private boolean anyItemChecked;
    private FragmentActivity fragmentActivity;
    private FloatingActionButton floatingActionButton;



    /** Constructor
     */
    public NotepadViewAdapter(FragmentActivity fragmentActivity, List<Note> noteList, FloatingActionButton addNote) {
        this.fragmentActivity = fragmentActivity;
        this.noteList = noteList;
        anyItemChecked = false;
        floatingActionButton = addNote;
        isChecked = new ArrayList<>();

        for(int index = 0; index < noteList.size(); index++){
            isChecked.add(false);
        }
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

        title.setText(noteList.get(i).getTitle());
        Description.setText(noteList.get(i).getNote());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isChecked.get(i) && !anyItemChecked){
                    Intent intent = new Intent(fragmentActivity, ShowNote.class);
                    intent.putExtra("id", noteList.get(i).getId());
                    intent.putExtra("title", noteList.get(i).getTitle());
                    intent.putExtra("note", noteList.get(i).getNote());
                    fragmentActivity.startActivity(intent);
                }
                else if(isChecked.get(i) && anyItemChecked){
                    cardView.setCardBackgroundColor(Color.WHITE);
                    isChecked.set(i, false);
                }else if(anyItemChecked){
                    cardView.setCardBackgroundColor(fragmentActivity.getColor(R.color.noteSelectionColor));
                    isChecked.set(i, true);
                }

                for(int index = 0; index < noteList.size(); index++){
                    if(isChecked.get(index)){
                        anyItemChecked = true;
                        break;
                    }else{
                        anyItemChecked = false;
                    }
                }

                if(!anyItemChecked){
                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_add_white));
                }
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!anyItemChecked){
                    cardView.setCardBackgroundColor(fragmentActivity.getColor(R.color.noteSelectionColor));
                    isChecked.set(i, true);
                    anyItemChecked = true;
                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_delete_white));
                }

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
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

    public List<Boolean> getCheckedItem(){
        anyItemChecked = false;
        return isChecked;
    }

    public void isCheckedBuild(List<Boolean> isChecked){
        this.isChecked = isChecked;
    }
}
