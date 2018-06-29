package com.ikechukwuakalu.journalapp.entry_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.add_entry.AddEntryActivity;
import com.ikechukwuakalu.journalapp.base.BaseFragment;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EntryDetailsFragment extends BaseFragment implements EntryDetailsContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.entryLoadingBar) ProgressBar progressBar;
    @BindView(R.id.entryView) ScrollView scrollView;
    @BindView(R.id.entry_title) TextView entryTitle;
    @BindView(R.id.entry_date) TextView entryDate;
    @BindView(R.id.entry_text) TextView entryText;

    private EntryDetailsActivity activity;

    private EntryDetailsPresenter presenter = null;

    public EntryDetailsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (EntryDetailsActivity) getActivity();
        if (activity != null) {
            presenter = activity.getPresenter();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_entry_details, container, false);
        ButterKnife.bind(this, layout);
        setUpToolbar(toolbar, null);
        setHasOptionsMenu(true);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) presenter.attach(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) presenter.detach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_entry_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            if (presenter != null) presenter.deleteEntry(
                    Integer.valueOf(activity.getEntryId()
                    ));
        } else if (id == R.id.action_edit) {
            Intent intent = new Intent(getContext(), AddEntryActivity.class);
            intent.putExtra(AddEntryActivity.IS_EDIT, activity.getEntryId());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        showLongToast(getContext(), message);
    }

    @Override
    public void showDeleteSuccess() {
        showShortToast(getContext(), "Entry was successfully deleted");
    }

    @Override
    public void showEntryDetails(JournalEntry journalEntry) {
        entryTitle.setText(journalEntry.getTitle());
        entryDate.setText(journalEntry.getCreatedAt());
        entryText.setText(journalEntry.getText());
    }
}