package in.techfantasy.hope;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class cardRequestAdapter extends RecyclerView.Adapter<cardRequestAdapter.myMenuVH> {

    private Context ctx;
    private List<cardRequestItem> cardRequestList;
    SharedPreferences sharedPreferences;
    float toLat, toLon, myLat, myLon;
    String myCoord = "", distanceString = "";
    Location toLocation = new Location(""), myLocation = new Location("");
    double distance;

    @NonNull
    @Override
    public myMenuVH onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrequest, parent, false);
        sharedPreferences = ctx.getSharedPreferences(Globals.sharedPrefName, MODE_PRIVATE);
        myCoord = sharedPreferences.getString("myCoord", "");
        myMenuVH mvh = new myMenuVH(itemview);

        if (myCoord.equals("")) {
            myCoord = "00,00";
        }
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(ctx);
                dialog.setContentView(R.layout.acceptaknwldgmntform);
                dialog.setTitle("Accept Request ?");
                dialog.setCanceledOnTouchOutside(false);
                final TextView etxtRemarks = dialog.findViewById(R.id.etxtRemarks);
                Button btnAcceptReq = dialog.findViewById(R.id.btnAcceptReq);
                Button btnRejectReq = dialog.findViewById(R.id.btnRejectReq);
                btnAcceptReq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String remarks = etxtRemarks.getText().toString();
                        new DBOps().modifyRequest(ctx,Globals.url,"ACCEPTED",sharedPreferences.getString("googleID",""),remarks,cardRequestList.get(viewType).getRequestID());
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                btnRejectReq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
                Window win = dialog.getWindow();
                win.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        return new myMenuVH(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull myMenuVH holder, final int position) {

        final cardRequestItem cri = cardRequestList.get(position);
        holder.txtRequestee.setText(cri.getRequesteeName());
        holder.txtRequest.setText(cri.getRequest());
        toLat = Float.parseFloat(cri.getDistanceFromMe().split(",")[0].trim());
        toLon = Float.parseFloat(cri.getDistanceFromMe().split(",")[1].trim());
        myLat = Float.parseFloat(myCoord.split(",")[0].trim());
        myLon = Float.parseFloat(myCoord.split(",")[1].trim());
        toLocation.setLatitude(toLat);
        toLocation.setLongitude(toLon);
        myLocation.setLatitude(toLat);
        myLocation.setLongitude(toLon);
        distance = distance(myLat, myLon, toLat, toLon);
        if (distance > 1) {
            distance = distance * 0.001;
            distanceString = String.valueOf(distance).substring(0, 4) + " KM";
        } else {
            distanceString = "< 1 meters";
        }
        holder.txtDistanceFromMe.setText("" + distanceString);
        holder.txtRequesteeContact.setText(cri.getRequesteeContact());
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cri.getRequesteeContact().equals("")) {
                    Toast.makeText(ctx, "No contact number available", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cri.getRequesteeContact()));
                    if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                       // startInstalledAppDetailsActivity(ctx);
                    }
                    ctx.startActivity(intent);
                }
            }
        });
        holder.btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cri.getDistanceFromMe().equals("")){
                    Toast.makeText(ctx,"No Location available",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/dir/"+cri.getDistanceFromMe()+"/"+myCoord));
                    ctx.startActivity(intent);

                }
            }
        });

    }



    public static float distance(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
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
        public TextView txtRequestee,txtRequesteeContact,txtDistanceFromMe,txtRequest;
        ImageButton btnCall,btnLocate;

        public myMenuVH(View itemView) {
            super(itemView);
            txtRequestee = itemView.findViewById(R.id.txtRequstee);
            txtRequesteeContact=itemView.findViewById(R.id.txtRequesteeContact);
            txtDistanceFromMe=itemView.findViewById(R.id.txtDistance);
            btnCall=itemView.findViewById(R.id.btnCall);
            btnLocate = itemView.findViewById(R.id.btnLocate);
            txtRequest = itemView.findViewById(R.id.txtRequest);
        }
    }
}
