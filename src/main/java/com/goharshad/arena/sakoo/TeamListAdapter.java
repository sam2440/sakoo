package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class TeamListAdapter extends RecyclerView.Adapter{

    private final static int LIST_TYPE_ITEM=0;
    private final static int LIST_TYPE_HEADER=1;

    private List<TeamMember> list;
    private Context context;

    public TeamListAdapter(Context context, List<TeamMember> list) {
        this.list=list;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position)!=null ? LIST_TYPE_ITEM : LIST_TYPE_HEADER ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case LIST_TYPE_ITEM :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false);
                return new ItemViewHolder(view);
            case LIST_TYPE_HEADER :
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_header,parent,false);
                return new HeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/IRAN-SANS.TTF");
        if (holder instanceof ItemViewHolder){
            ItemViewHolder holder1= (ItemViewHolder) holder;
            TeamMember member=list.get(holder.getAdapterPosition());
            int padding=(int) ((DimenHelper.getDeviceWidth(((Activity) context))-85)/9);
            holder1.imageView.setImageResource(member.getImg());

            holder1.name.setText(member.getName());
            holder1.name.setTypeface(typeface);
            holder1.name.setPadding(0,0,padding,0);
            holder1.name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13.15f);

            holder1.occ.setText(member.getOcc());
            holder1.occ.setTypeface(typeface);
            holder1.occ.setPadding(0,0,padding,0);
            holder1.occ.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13.15f);

            holder1.relativeLayout.getLayoutParams().height = (int) ( (2/10.0) * DimenHelper.getDeviceWidth((Activity) context));
        }else if (holder instanceof HeaderViewHolder){
            HeaderViewHolder holder2= (HeaderViewHolder) holder;
            if (position==0){
                holder2.head.setText("تیم اجرایی");
            }else if (position==5){
                holder2.head.setText("تیم نرم افزاری");
            }
            holder2.head.setTypeface(typeface);
        }
    }

    @Override
    public int getItemCount() {
        return list!=null ? list.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout relativeLayout;
        private ImageView imageView;
        private TextView name,occ;

        public ItemViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.contaniner);
            imageView= (ImageView) itemView.findViewById(R.id.team_img);
            name= (TextView) itemView.findViewById(R.id.txt_name);
            occ= (TextView) itemView.findViewById(R.id.txt_occ);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        private TextView head;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            head= (TextView) itemView.findViewById(R.id.team_list_header);
        }
    }

}
