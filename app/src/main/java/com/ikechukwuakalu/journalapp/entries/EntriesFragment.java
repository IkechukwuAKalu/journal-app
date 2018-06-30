package com.ikechukwuakalu.journalapp.entries;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.add_entry.AddEntryActivity;
import com.ikechukwuakalu.journalapp.auth.AuthActivity;
import com.ikechukwuakalu.journalapp.base.BaseFragment;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.data.models.User;
import com.ikechukwuakalu.journalapp.entry_details.EntryDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntriesFragment extends BaseFragment implements EntriesContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.entries_rv) RecyclerView entriesList;
    @BindView(R.id.no_entry_tv) TextView noEntryView;
    @BindView(R.id.add_new_entry) FloatingActionButton addNewEntry;

    private EntriesPresenter presenter = null;
    private EntriesActivity activity;

    public EntriesFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (EntriesActivity) getActivity();
        if (activity != null) presenter = activity.getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_entries, container, false);
        ButterKnife.bind(this, layout);
        activity.setSupportActionBar(toolbar);
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
        inflater.inflate(R.menu.menu_entries, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all) {
            Context context = getContext();
            if (context != null) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete all your Journal entries?")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (presenter != null) presenter.deleteAllEntries();
                            }
                        })
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_delete_forever_black_24dp)
                        .show();
            }
        } else if (id == R.id.action_signout) {
            Context context = getContext();
            if (context != null) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Sign out")
                        .setMessage("Signing out deletes all your Journal entries. Continue?")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (presenter != null) presenter.signOutUser(getActivity());
                            }
                        })
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_account_circle_black_24dp)
                        .show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_new_entry)
    public void createNew() {
        startActivity(new Intent(getContext(),
                AddEntryActivity.class));
        activity.overridePendingTransition(R.anim.slide_up, android.R.anim.fade_out);
    }

    @Override
    public void showSignOutSuccess() {
        showShortToast(getContext(), "Sign out successful");
        showAuthScreen();
    }

    @Override
    public void showError(String message) {
        showLongToast(getContext(), message);
    }

    @Override
    public void showAuthScreen() {
        startActivity(new Intent(getContext(), AuthActivity.class));
        activity.finish();
    }

    @Override
    public void showUserDetails(User user) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            String subtitle = user.getName() + " - " + user.getEmail();
            actionBar.setSubtitle(subtitle);
        }
    }

    @Override
    public void showNoEntryFound() {
        entriesList.setVisibility(View.GONE);
        noEntryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEntries(List<JournalEntry> entries) {
        noEntryView.setVisibility(View.GONE);
        entriesList.setVisibility(View.VISIBLE);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        entriesList.setLayoutManager(llm);
        EntriesAdapter adapter = new EntriesAdapter(entries, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.valueOf((int) v.getTag());
                Intent intent = new Intent(getContext(), EntryDetailsActivity.class);
                intent.putExtra(EntryDetailsActivity.ENTRY_ID, id);
                startActivity(intent);
            }
        });
        entriesList.setAdapter(adapter);
    }
}
