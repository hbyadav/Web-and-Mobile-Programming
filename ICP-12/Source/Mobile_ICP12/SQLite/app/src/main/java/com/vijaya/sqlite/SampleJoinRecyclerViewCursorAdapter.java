package com.vijaya.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vijaya.sqlite.databinding.EmployeeListItemBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SampleJoinRecyclerViewCursorAdapter extends RecyclerView.Adapter<SampleJoinRecyclerViewCursorAdapter.ViewHolder> {

    Context mContext;
    Cursor mCursor;


    public SampleJoinRecyclerViewCursorAdapter(Context context, Cursor cursor, AdaptorListener listener) {
        onClickListener = listener;
        mContext = context;
        mCursor = cursor;
    }

    public interface AdaptorListener {              // listener interface for passing information back to activity
        void deleteButtonOnClick(View v, String name);
        void updateButtonOnClick(View v, String fn, String ln, String jdesc, String dob);
    }
    public static AdaptorListener onClickListener;  // listener instance

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EmployeeListItemBinding itemBinding;
        public ViewHolder(View itemView) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);


        }

        public void bindCursor(final Cursor cursor) {
            itemBinding.firstnameLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_FIRSTNAME)
            ));
            final String fn = itemBinding.firstnameLabel.getText().toString();         // pass string values to update method when called
            itemBinding.lastnameLabel.setText(cursor.getString(                        // to get current DB values (for populating update form)
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_LASTNAME)
            ));
            final String ln = itemBinding.lastnameLabel.getText().toString();
            itemBinding.jobDescLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION)
            ));
            final String jdesc = itemBinding.jobDescLabel.getText().toString();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_DATE_OF_BIRTH)));
            itemBinding.dobLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
            final String dob = itemBinding.dobLabel.getText().toString();

            itemBinding.nameLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_NAME)
            ));
            itemBinding.descLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_DESCRIPTION)
            ));

            calendar.setTimeInMillis(cursor.getLong(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_FOUNDED_DATE)));
            itemBinding.foundedLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));

            itemBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override                                       // implementing listener for delete button
                public void onClick(View view) {
                    onClickListener.deleteButtonOnClick(view, fn);
                }                                               // pass name value back to activity
            });

            itemBinding.updateButton.setOnClickListener(new View.OnClickListener() {
                @Override                                       // implementing listener for update button
                public void onClick(View view) {
                    onClickListener.updateButtonOnClick(view, fn, ln, jdesc, dob); // pass each value back to employee activity
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindCursor(mCursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.employee_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}