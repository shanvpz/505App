package in.techfantasy.hope;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReqHelpFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReqHelpFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReqHelpFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences sharedPreferences;
    EditText etxtName,etxtHelp;
    Button btnReqHelp;

    private OnFragmentInteractionListener mListener;

    public ReqHelpFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReqHelpFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ReqHelpFrag newInstance(String param1, String param2) {
        ReqHelpFrag fragment = new ReqHelpFrag();
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
        sharedPreferences=this.getActivity().getSharedPreferences(Globals.sharedPrefName,MODE_PRIVATE);
        View v = inflater.inflate(R.layout.fragment_req_help, container, false);

        etxtName=v.findViewById(R.id.etxtName);
        etxtHelp=v.findViewById(R.id.etxtHelp);
        btnReqHelp=v.findViewById(R.id.btnReqHelp);
        etxtName.setText(sharedPreferences.getString("myName",""));
        //etxtHelp.setText(sharedPreferences.getString("myNumber",""));


        btnReqHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnReqHelp.getText().equals("Request")) {
                    btnReqHelp.setText("Cancel");
                    Intent intentMyService = new Intent(getActivity(), LocationService.class);
                    getActivity().startService(intentMyService);
                }
                else {
                    btnReqHelp.setText("Request");
                    Intent intentMyService = new Intent(getActivity(), LocationService.class);
                    getActivity().stopService(intentMyService);
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
