package com.example.covid_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JavaAdapter extends RecyclerView.Adapter<JavaAdapter.ViewHolder> {

    List<Model> covidList;

    public JavaAdapter(List<Model> covidList)
    {
        this.covidList = covidList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testing2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String city = covidList.get(position).getCity();
        String confirmedIndian = covidList.get(position).getConfirmedCasesIndian();
        String confirmedForeign = covidList.get(position).getConfirmedCasesForeign();
        String discharged = covidList.get(position).getDischarged();
        String deaths = covidList.get(position).getDeaths();
        String totalConfirmed = covidList.get(position).getTotalConfirmed();

        holder.setData(city,confirmedIndian,confirmedForeign,discharged,deaths,totalConfirmed);

    }

    @Override
    public int getItemCount() {
        return covidList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView confirmedCasesIndian;
        TextView confirmedCasesForeign;
        TextView dis;
        TextView dt;
        TextView total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView1);
            confirmedCasesIndian = itemView.findViewById(R.id.textView2);
            confirmedCasesForeign = itemView.findViewById(R.id.textView3);
            dis = itemView.findViewById(R.id.textView4);
            dt = itemView.findViewById(R.id.textView5);
            total = itemView.findViewById(R.id.textView6);
        }

        public void setData(String city, String confirmedIndian, String confirmedForeign, String discharged, String deaths, String totalConfirmed) {

            name.setText(city);
            confirmedCasesIndian.setText(confirmedIndian);
            confirmedCasesForeign.setText(confirmedForeign);
            dis.setText(discharged);
            dt.setText(deaths);
            total.setText(totalConfirmed);
        }
    }


}


















//    public View getList(int position, View convertView, ViewGroup parent)
//    {
//        //getting the layoutinflater
//        LayoutInflater inflater = LayoutInflater.from(cnt);
////
////        //creating a view with our xml layout
//        View listView = inflater.inflate(R.layout.testing2,null,true);
//
////        View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.testing2,null,true);
//
//
//        city = listView.findViewById(R.id.textView1);
//        confirmedCasesIndian = listView.findViewById(R.id.textView2);
//        confirmedCasesForeign = listView.findViewById(R.id.textView3);
//        discharged = listView.findViewById(R.id.textView4);
//        deaths = listView.findViewById(R.id.textView5);
//        totalConfirmed = listView.findViewById(R.id.textView6);
////        delta = listView.findViewById(R.id.textView7);
//
//
//        Model m = covidList.get(position);
//
//        city.setText(m.getCity());
//        confirmedCasesIndian.setText(m.getConfirmedCasesIndian());
//        confirmedCasesForeign.setText(m.getConfirmedCasesForeign());
//        discharged.setText(m.getDischarged());
//        deaths.setText(m.getDeaths());
//        totalConfirmed.setText(m.getTotalConfirmed());
////        delta.setText(m.getDelta());
//
//        return listView;
//    }





