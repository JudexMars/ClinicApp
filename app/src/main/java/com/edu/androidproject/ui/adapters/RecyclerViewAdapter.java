package com.edu.androidproject.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.androidproject.R;
import com.edu.androidproject.data.model.Appointment;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Appointment> appointments;

    public RecyclerViewAdapter(Context context, List<Appointment> appointments) {
        this.appointments = appointments;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM", Locale.US);
        holder.dateTextView.setText(sdf.format(appointment.getDay()));
        int minute = appointment.getMinute();
        holder.timeTextView.setText(MessageFormat.format("{0}:{1}", appointment.getHour(),
                (minute > 9 ? minute : "0" + minute)));
        holder.drTextView.setText(appointment.getDr());
        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Информация")
                    .setMessage("Дата: " + holder.dateTextView.getText().toString() + ", время: "
                            + holder.timeTextView.getText().toString() + ", врач: "
                            + holder.drTextView.getText().toString())
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView dateTextView;
        final TextView timeTextView;
        final TextView drTextView;
        public ViewHolder(View view) {
            super(view);
            this.dateTextView = view.findViewById(R.id.dateTextView);
            this.timeTextView = view.findViewById(R.id.timeTextView);
            this.drTextView = view.findViewById(R.id.drTextView);
        }
    }
}
