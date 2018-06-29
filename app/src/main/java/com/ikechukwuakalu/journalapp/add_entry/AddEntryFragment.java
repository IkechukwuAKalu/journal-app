package com.ikechukwuakalu.journalapp.add_entry;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.base.BaseFragment;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEntryFragment extends BaseFragment implements AddEntryContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.add_entry_title) EditText entryTitle;
    @BindView(R.id.add_entry_text) EditText entryText;

    private AddEntryActivity activity;
    private AddEntryPresenter presenter = null;
    private boolean isEdit;

    public AddEntryFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AddEntryActivity) getActivity();
        if (activity != null) presenter = activity.getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add_entry, container, false);
        ButterKnife.bind(this, layout);
        setUpToolbar(toolbar, getResources().getDrawable(R.drawable.ic_close_white_24dp));
        setHasOptionsMenu(true);
        isEdit = activity.getIntent().getBooleanExtra(AddEntryActivity.IS_EDIT, false);
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
        inflater.inflate(R.menu.menu_add_entry, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (entryTitle.getText().toString().trim().isEmpty() &&
                    entryText.getText().toString().trim().isEmpty()) {
                activity.onBackPressed();
                activity.overridePendingTransition(android.R.anim.fade_in, R.anim.slide_down);
                return true;
            }
            new AlertDialog.Builder(activity.getApplicationContext())
                    .setTitle("Confirm exit")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.onBackPressed();
                            activity.overridePendingTransition(android.R.anim.fade_in, R.anim.slide_down);
                        }
                    })
                    .show();
        } else if (id == R.id.action_save_entry) {
            String title = entryTitle.getText().toString();
            String text = entryText.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy", Locale.US);
            Calendar calendar = Calendar.getInstance();
            String createdAt = dateFormat.format(calendar.getTime());

            if (presenter != null){
                if (isEdit) presenter.editEntry(new JournalEntry(title, text));
                else presenter.saveEntry(new JournalEntry(title, text, createdAt));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void populateFields(JournalEntry journalEntry) {
        entryTitle.setText(journalEntry.getTitle());
        entryText.setText(journalEntry.getText());
    }

    @Override
    public void showSaveSuccess() {
        showShortToast(getContext(), "Saved successfully");
        activity.onBackPressed();
    }

    @Override
    public void showSaveError(String message) {
        showLongToast(getContext(), message);
    }
}
