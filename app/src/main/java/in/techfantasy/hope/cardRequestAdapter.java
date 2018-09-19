package in.techfantasy.hope;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class cardRequestAdapter extends RecyclerView.Adapter<cardRequestAdapter.myMenuVH>{

    private Context ctx;
    private List<cardRequestItem> cardRequestList;
    @NonNull
    @Override
    public myMenuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrequest,parent,false);

        return new myMenuVH(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull myMenuVH holder, final int position) {

        cardRequestItem cri = cardRequestList.get(position);
        holder.txtRequestee.setText(cri.getRequesteeName());
        holder.txtDistanceFromMe.setText(cri.getDistanceFromMe());
        holder.txtRequesteeContact.setText(cri.getRequesteeContact());
    }

    @Override
    public int getItemCount() {
        return cardRequestList.size();
    }


    public cardRequestAdapter(Context ctx, List<cardRequestItem> requestItemList) {
        this.ctx = ctx;
        this.cardRequestList = requestItemList;
    }

    public class myMenuVH extends RecyclerView.ViewHolder{
        public TextView txtRequestee,txtRequesteeContact,txtDistanceFromMe;

        public myMenuVH(View itemView) {
            super(itemView);
            txtRequestee = itemView.findViewById(R.id.txtRequstee);
            txtRequesteeContact=itemView.findViewById(R.id.txtRequesteeContact);
            txtDistanceFromMe=itemView.findViewById(R.id.txtDistance);
        }
    }
}
