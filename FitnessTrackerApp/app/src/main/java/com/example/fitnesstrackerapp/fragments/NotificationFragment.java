package com.example.fitnesstrackerapp.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesstrackerapp.AddReminderActivity;
import com.example.fitnesstrackerapp.Notification;
import com.example.fitnesstrackerapp.NotificationAdapter;
import com.example.fitnesstrackerapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView notificationRecyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notifications;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Enable options menu
        notifications = new ArrayList<>();
        // Populate with initial data if needed
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);

        // Set up RecyclerView
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificationAdapter(notifications);
        notificationRecyclerView.setAdapter(adapter);

        // Set toolbar as the action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_notifications, menu); // Create a menu XML file for the toolbar
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_reminder) {
            // Handle add reminder action
            // You can start a new Activity or show a dialog to add a new reminder
            Intent intent = new Intent(getActivity(), AddReminderActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
