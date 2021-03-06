package com.ghostbusters.queuer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.ghostbusters.queuer.interfaces.RearrangementListener;
import com.ghostbusters.queuer.models.Project;
import com.ghostbusters.queuer.R;

import java.util.ArrayList;

/**
 * Created by blakemackall on 1/15/14.
 */
public class FeedAdapter extends BaseAdapter implements RearrangementListener{
    private Context context;
    private ArrayList<Project> projects = new ArrayList<Project>();

    public FeedAdapter(Context context, ArrayList<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    public void remove(int position) {
        projects.remove(position);
        notifyDataSetChanged();
    }

    public void insert(Project project, int position) {
        projects.add(position, project);
        notifyDataSetChanged();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Project getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getLocalId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_project, null);
        }
        ((TextView)convertView.findViewById(R.id.tv_title)).setText(getItem(position).getName());
        convertView.findViewById(R.id.ll_project).setBackgroundColor(getItem(position).getColor());
        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return projects.isEmpty();
    }


    @Override
    public void onStartedRearranging() {

    }

    public void swapElements(int indexOne, int indexTwo) {
        Project temp1 = projects.get(indexOne);
        Project temp2 = projects.get(indexTwo);

        projects.remove(indexOne);
        projects.add(indexOne, temp2);

        projects.remove(indexTwo);
        projects.add(indexTwo, temp1);
    }

    @Override
    public void onFinishedRearranging() {
        notifyDataSetChanged();
    }
}
