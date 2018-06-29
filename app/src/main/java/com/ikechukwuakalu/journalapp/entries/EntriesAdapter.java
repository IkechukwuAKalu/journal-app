package com.ikechukwuakalu.journalapp.entries;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder> {

    private List<JournalEntry> entries;
    private View.OnClickListener clickListener;

    EntriesAdapter(List<JournalEntry> entries, View.OnClickListener clickListener) {
        this.entries = entries;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public EntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_item, parent, false);
        return new EntriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntriesViewHolder holder, int position) {
        JournalEntry journalEntry = entries.get(position);
        holder.bind(journalEntry);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    class EntriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.entry_title) TextView title;
        @BindView(R.id.entry_text) TextView text;

        EntriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(clickListener);
        }

        void bind(JournalEntry journalEntry) {
            itemView.setTag(journalEntry.getId());
            title.setText(journalEntry.getTitle());
            text.setText(journalEntry.getText());
        }
    }
}