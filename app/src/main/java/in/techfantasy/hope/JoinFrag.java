package in.techfantasy.hope;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etxtName, etxtPhone, etxtLocation,etxtEmail,etxtAltPhone;
    Button btnJoin;
    Boolean permissionsGranted = false;
    String Address;
    Location mylocation;
    SharedPreferences sharedPreferences;

    private OnFragmentInteractionListener mListener;

    public JoinFrag() {
        // Required empty public constructor
    }


    private void compinit(View v) {
        etxtName = v.findViewById(R.id.etxtName);
        etxtPhone = v.findViewById(R.id.etxtPhone);
        etxtLocation = v.findViewById(R.id.etxtLocation);
        etxtEmail=v.findViewById(R.id.etxtEmail);
        etxtAltPhone=v.findViewById(R.id.etxtAltPhone);
        btnJoin = v.findViewById(R.id.btnJoin);
        sharedPreferences=this.getActivity().getSharedPreferences(Globals.sharedPrefName,MODE_PRIVATE);

        SmartLocation.with(this.getActivity()).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                etxtLocation.setText("Loc:" + location.getLatitude() + "," + location.getLongitude());
                mylocation = location;
                new LocationHelper().decodeLocation(mylocation,getActivity());
                //etxtName.setText(sharedPreferences.getString("googleID",""));
            }

        });

    }

    private boolean formOK(){
        if(TextUtils.isEmpty(etxtName.getText().toString().trim())||
                TextUtils.isEmpty(etxtPhone.getText().toString().trim())||
                TextUtils.isEmpty(etxtLocation.getText().toString().trim())||
                TextUtils.isEmpty(etxtAltPhone.getText().toString().trim())||
                TextUtils.isEmpty(etxtEmail.getText().toString().trim())){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkGPS() {
        if (etxtLocation.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "Please turn on GPS to use this App", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                        getActivity().finish();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinFrag newInstance(String param1, String param2) {
        JoinFrag fragment = new JoinFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_join, container, false);
        compinit(v);
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please grant the following permissions to continue";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(getActivity()/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(getActivity(), "Granted", Toast.LENGTH_SHORT).show();
                permissionsGranted = true;
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Sorry, You must grant permissions to use this app.", Toast.LENGTH_LONG).show();
                getActivity().finish();
                permissionsGranted = false;
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(getActivity()) && permissionsGranted) {
                    if (checkGPS()) {
                        Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
                        try{
                            if(formOK()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("myName",etxtName.getText().toString());
                                editor.putString("myNumber",etxtPhone.getText().toString());
                                editor.putString("firstTime","false");
                                editor.commit();
                                editor.apply();
                                User user = new User();
                                user.setUsername(etxtName.getText().toString());
                                user.setUserphone(etxtPhone.getText().toString());
                                user.setUseraltphone(etxtAltPhone.getText().toString());
                                user.setUseremail(etxtEmail.getText().toString());
                                user.setUsercity(sharedPreferences.getString("myCity", ""));
                                user.setUserlocality(sharedPreferences.getString("myDistrict", ""));
                                user.setUserstate(sharedPreferences.getString("myState", ""));
                                user.setUsercountry(sharedPreferences.getString("myCountry", ""));
                                user.setUserpincode(sharedPreferences.getString("myPincode", ""));
                                user.setUsercoord(sharedPreferences.getString("myCoord",""));
                                user.setGoogleID(sharedPreferences.getString("googleID",""));
                                new DBOps().UserRegistration(getActivity(),Globals.url,user);
                            }
                            else{
                                Toast.makeText(getActivity(),"Form not complete",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ex){
                            Toast.makeText(getActivity(), "From Join Btn Click:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Problem with GPS Connection", Toast.LENGTH_SHORT).show();
                        SmartLocation.with(getActivity()).location().start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                etxtLocation.setText("Loc:" + location.getLatitude() + "," + location.getLongitude());
                                mylocation = location;
                                new LocationHelper().decodeLocation(mylocation,getActivity());
                                Toast.makeText(getActivity(),"Location verification",Toast.LENGTH_SHORT).show();
                            }

                        });
                        if(etxtLocation.getText().toString().equals("")){
                            Toast.makeText(getActivity(),"Location verification",Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(getActivity(), "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
