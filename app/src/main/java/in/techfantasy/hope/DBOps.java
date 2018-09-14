package in.techfantasy.hope;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class DBOps {
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    User u;
    SharedPreferences sharedPreferences;

    public void UserRegistration(final Context ctx, String HttpUrl, final User user){
        try {
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
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);
                                if(jsonObject.getString("code").equals("1")){
                                    ctx.startActivity(new Intent(ctx,MainContainer.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ctx,"From User Registration:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
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
                    params.put("username", user.getUsername());
                    params.put("useremail", user.getUseremail());
                    params.put("userphone", user.getUserphone());
                    params.put("useraltphone", user.getUseraltphone());
                    params.put("usercity", user.getUsercity());
                    params.put("userdistrict", user.getUserlocality());
                    params.put("userstate", user.getUserstate());
                    params.put("usercountry", user.getUsercountry());
                    params.put("userpincode", user.getUserpincode());
                    params.put("usercoord", user.getUsercoord());
                    params.put("googleID",user.getGoogleID());
                    params.put("op", "join");

                    return params;
                }

            };
            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            Toast.makeText(ctx,"From User Registration:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


    public void UpdateLocation(final Context ctx, String HttpUrl, final ReqModel reqModel){
        try {
            requestQueue = Volley.newRequestQueue(ctx);

            //progressDialog = new ProgressDialog(ctx);
            // Showing progress dialog at user registration time.
            //progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
            //progressDialog.show();

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            //progressDialog.dismiss();

                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(ctx, ServerResponse, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);
                                if(jsonObject.getString("code").equals("1")){
                                    // ctx.startActivity(new Intent(ctx,));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ctx,"From Location Updation (OR) :"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            //progressDialog.dismiss();

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
                    params.put("googleID",reqModel.getGoogleID());
                    params.put("usercoord",reqModel.getCoordinates());
                    params.put("op", "locationupdate");

                    return params;
                }

            };
            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            Toast.makeText(ctx,"From Location Updation :"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


    public void getSingleUsergid(final Context ctx, String HttpUrl, final String googleID){
        try {
            u=new User();
            requestQueue = Volley.newRequestQueue(ctx);

            progressDialog = new ProgressDialog(ctx);
            // Showing progress dialog at user registration time.
            progressDialog.setMessage("Please Wait, Connecting to server!");
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
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);
                                if(jsonObject.getString("code").equals("1")){
                                    //ctx.startActivity(new Intent(ctx,MainContainer.class));
                                    sharedPreferences=ctx.getSharedPreferences(Globals.sharedPrefName,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("myName",jsonObject.getString("username"));
                                    editor.putString("myPhone",jsonObject.getString("userphone"));
                                    editor.putString("myAltPhone",jsonObject.getString("useraltphone"));
                                    editor.putString("myEmail",jsonObject.getString("useremail"));
                                    editor.putString("firstTime","false");
                                    //editor.putString("loggedMode","");
                                    editor.commit();
                                    editor.apply();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ctx,"From getSingleUser:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
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

                    params.put("googleID",googleID);
                    params.put("op", "getsingleusergid");

                    return params;
                }

            };
            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            Toast.makeText(ctx,"From User Registration:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


    public void getSingleUseruid(final Context ctx, String HttpUrl, final String userID){
        try {
            u=new User();
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
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);
                                if(jsonObject.getString("code").equals("1")){
                                    //ctx.startActivity(new Intent(ctx,MainContainer.class));
                                    u.setUsername(jsonObject.getString("username"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ctx,"From getSingleUser:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
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

                    params.put("userID",userID);
                    params.put("op", "getsingleuseruid");

                    return params;
                }

            };
            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            Toast.makeText(ctx,"From get single user:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

}
