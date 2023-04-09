package com.winhealth.blood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<JSONObject> mList;
    private Context mContext;
    private ImageView img;
    private VideoView vd;
    String imgvd=null;
    String fich_joint;
    Picasso picasso;
    public MyAdapter(Context context, ArrayList<JSONObject> list) {
        mContext = context;
        mList = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JSONObject object1 = mList.get(viewType);
        View view = LayoutInflater.from(mContext).inflate(R.layout.myview2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONObject object = mList.get(position);
        try {
            fich_joint=object.getString("imgvd");
            if (fich_joint == "null"){
                holder.textViewTitle.setText(object.getString("contenu"));
                holder.pub.setText(" Publié le : "+object.getString("date"));
            }
            else {
                imgvd=fich_joint.substring(fich_joint.lastIndexOf("."));
                    if (imgvd.equals(".jpg") || imgvd.equals(".png")){
                        String uri="http:/192.168.43.238/api1/"+fich_joint;
                        holder.textViewTitle.setText(object.getString("contenu"));
                        holder.pub.setText("Publié le : "+object.getString("date"));
                        holder.textViewDescription.setVisibility(View.VISIBLE);
                        Picasso.get()
                                .load(uri)
                                .into(holder.textViewDescription);




                    }

            }
        } catch (JSONException e) {
            //throw new RuntimeException(e);
        }

        //
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public ImageView textViewDescription;
        public TextView pub;
        public VideoView vd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.contenu);
            textViewDescription = itemView.findViewById(R.id.image);
            pub=itemView.findViewById(R.id.publie);
            vd=itemView.findViewById(R.id.video);
        }
    }
}
