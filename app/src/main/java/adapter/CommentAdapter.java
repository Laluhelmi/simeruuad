package adapter;

import android.content.Context;
import android.pkklppuad.uad4students.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import pojo.CommentPojo;
import server.Account;

/**
 * Created by L on 10/7/17.
 */

public class CommentAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<CommentPojo> commentPojos;
    private Context context;

    public CommentAdapter(Context context,List<CommentPojo> commentPojos){
        this.commentPojos = commentPojos;
        this.context      = context;
        layoutInflater    = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return commentPojos.size();
    }

    @Override
    public Object getItem(int i) {
        return commentPojos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.comment_kiri,viewGroup,false);
        TextView kommentKiri   = (TextView)view.findViewById(R.id.commentKiri);
        TextView kommentKanan  = (TextView)view.findViewById(R.id.commentKanan);
        TextView usernameKanan = (TextView)view.findViewById(R.id.usernameKanan);
        TextView usernameKiri = (TextView)view.findViewById(R.id.username);

        RelativeLayout rKiri  = (RelativeLayout)view.findViewById(R.id.RKiri);
        RelativeLayout rKanan = (RelativeLayout)view.findViewById(R.id.Rkanan);

        String idUser         = new Account().getNiy(this.context);
        CommentPojo commentPojo = (CommentPojo)getItem(i);
        if(idUser.equals(commentPojo.id_user)){
            rKiri.setVisibility(View.INVISIBLE);

            usernameKanan.setText(commentPojo.username);
            kommentKanan.setText(commentPojo.comment);
        }else{
            rKanan.setVisibility(View.INVISIBLE);

            usernameKiri.setText(commentPojo.username);
            kommentKiri.setText(commentPojo.comment);
        }


        return view;
    }
}
