package com.example.fitnesstrackerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.taskName.setText(notification.getTaskName());
        holder.time.setText(notification.getTime());
        holder.activityType.setText(notification.getActivityType());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView time;
        TextView activityType;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            time = itemView.findViewById(R.id.time);
            activityType = itemView.findViewById(R.id.activityType);
        }
    }
}
