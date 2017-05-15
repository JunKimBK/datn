package ninja.gdg.learnjapanese.votungdh.example.com.finalproject.title;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.R;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.config.Config;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.listening.ListeningActivity;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.model.Title;
import ninja.gdg.learnjapanese.votungdh.example.com.finalproject.review.ChooseTypeReviewActivity;

/**
 * Created by VoTungDH on 17/03/12.
 */
public class TitleAdapter extends BaseAdapter {

    Context context;
    List<Title> titles;
    String type;

    public TitleAdapter(Context context, List<Title> titles) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.titles=titles;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tvTitle, tvMeaning;
        ImageView imgTitle;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TitleAdapter.Holder holder = new TitleAdapter.Holder();
        View rowView;
        final Title title = titles.get(position);
        rowView = LayoutInflater.from(context).inflate(R.layout.title_adapter, null);

        holder.tvTitle = (TextView) rowView.findViewById(R.id.tvTitleRv);
        holder.tvMeaning = (TextView) rowView.findViewById(R.id.tvMeaning);
        holder.imgTitle = (ImageView) rowView.findViewById(R.id.imgPicture);

        holder.tvTitle.setText(title.getTitle());
        holder.tvMeaning.setText(title.getMeaning());
        Picasso.with(context).load(Config.HOST_PATH_TITLE + title.getPath()).into(holder.imgTitle);
        rowView.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
           Bundle bundle;
           Intent in = ((ChooseTitleActitvity) context).getIntent();
           bundle = in.getBundleExtra("Data");
           type = bundle.getString("Type");
           if (type.equals("review")) {
               bundle.putInt("ID", title.getIdtitle());
               in = new Intent(v.getContext(), ChooseTypeReviewActivity.class);
               in.putExtra("Data", bundle);
               context.startActivity(in);
           } else if (type.equals("listening")) {
               bundle.putInt("ID", title.getIdtitle());
               in = new Intent(v.getContext(), ListeningActivity.class);
               in.putExtra("Data", bundle);
               context.startActivity(in);
           }
        }
        }
        );
        return rowView;
    }
}
