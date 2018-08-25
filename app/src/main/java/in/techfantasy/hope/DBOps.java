package in.techfantasy.hope;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DBOps {
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    public void UserRegistration(final Context ctx, String HttpUrl, final User user){
        requestQueue = Volley.newRequestQueue(ctx);

        progressDialog = new ProgressDialog(ctx);
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(ctx, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(ctx, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("username",user.getUsername());
                params.put("useremail", user.getUseremail());
                params.put("userphone", user.getUserphone());
                params.put("useraltphone", user.getUseraltphone());
                params.put("usercity",user.getUsercity());
                params.put("userdistrict",user.getUserlocality());
                params.put("userstate",user.getUserstate());
                params.put("usercountry",user.getUsercountry());
                params.put("userpincode",user.getUserpincode());
                params.put("op","join");

                return params;
            }

        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
}
