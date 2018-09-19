package in.techfantasy.hope;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class cardMenuAdapter extends RecyclerView.Adapter<cardMenuAdapter.myMenuVH>{

    private Context ctx;
    private List<cardMenuItem> menuItemList;
    @NonNull
    @Override
    public myMenuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmenu,parent,false);

        return new myMenuVH(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull myMenuVH holder, final int position) {

        cardMenuItem cmi = menuItemList.get(position);
        holder.menuText.setText(cmi.getMenuText());
        holder.menuImage.setImageResource(cmi.getMenuImage());
        holder.menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx,position+"",Toast.LENGTH_SHORT).show();
                if (position==0){
                    loadFragment(new ReqListFrag());
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = ((FragmentActivity) ctx).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }


    public cardMenuAdapter(Context ctx, List<cardMenuItem> menuItemList) {
        this.ctx = ctx;
        this.menuItemList = menuItemList;
    }

    public class myMenuVH extends RecyclerView.ViewHolder{
        public TextView menuText;
        public ImageView menuImage;

        public myMenuVH(View itemView) {
            super(itemView);
            menuImage = itemView.findViewById(R.id.menuimage);
            menuText = itemView.findViewById(R.id.menutext);
        }
    }
}
