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

import org.json.JSONArray;
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
    VolleyResponseFetcher volleyResponseFetcher;

    public  DBOps(){

    }
    public DBOps(VolleyResponseFetcher vrf){
        this.volleyResponseFetcher = vrf;
    }

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
                                JSONArray jsonArray=jsonObject.getJSONArray("response");
                                if(jsonArray.getJSONObject(0).getString("code").equals("1")){
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
            requestQueue = Volley.newRequestQueue(ctx);

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
                                JSONArray jsonArray=jsonObject.getJSONArray("response");
                                if(jsonArray.getJSONObject(0).getString("code").equals("1")){
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
            requestQueue = Volley.newRequestQueue(ctx);

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
                            //Toast.makeText(ctx, ServerResponse, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);
                                JSONArray jsonArray=jsonObject.getJSONArray("response");
                                if(jsonArray.getJSONObject(0).getString("code").equals("1")){
                                    JSONArray data=jsonObject.getJSONArray("datas");
                                    jsonObject = data.getJSONObject(0);
                                    //ctx.startActivity(new Intent(ctx,MainContainer.class));
                                    sharedPreferences=ctx.getSharedPreferences(Globals.sharedPrefName,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    if(jsonObject.has("username")){
                                        editor.putString("myName",jsonObject.getString("username"));
                                    }
                                    if(jsonObject.has("userphone")){
                                        editor.putString("myPhone",jsonObject.getString("userphone"));
                                    }
                                    if(jsonObject.has("useraltphone")){
                                        editor.putString("myAltPhone",jsonObject.getString("useraltphone"));
                                    }
                                    if(jsonObject.has("useremail")){
                                        editor.putString("myEmail",jsonObject.getString("useremail"));
                                    }
                                    if (jsonObject.has("googleID")){
                                        editor.putString("firstTime","false");
                                    }else {
                                        editor.putString("firstTime","true");
                                    }

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
            requestQueue = Volley.newRequestQueue(ctx);

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
            requestQueue = Volley.newRequestQueue(ctx);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            Toast.makeText(ctx,"From get single user:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


    public void PostRequest(final Context ctx, String HttpUrl, final ReqModel reqModel){
        try {
            requestQueue = Volley.newRequestQueue(ctx);

            progressDialog = new ProgressDialog(ctx);
            // Showing progress dialog at user registration time.
            progressDialog.setMessage("Please Wait, Requesting help...");
            progressDialog.show();

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Showing Echo Response Message Coming From Server.
                            //Toast.makeText(ctx, ServerResponse, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);
                                if(jsonObject.getString("code").equals("1")){
                                    //ctx.startActivity(new Intent(ctx,MainContainer.class));
                                    Toast.makeText(ctx,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ctx,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ctx,"From Request Posting:"+e.getMessage(),Toast.LENGTH_SHORT).show();
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
                    params.put("googleID",reqModel.getGoogleID());
                    params.put("description",reqModel.getMyHelp());
                    params.put("status",reqModel.getStatus());
                    params.put("op", "postrequest");

                    return params;
                }

            };
            // Creating RequestQueue.
            requestQueue = Volley.newRequestQueue(ctx);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            Toast.makeText(ctx,"From Request posting:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


    public void CancelRequest(final Context ctx, String HttpUrl, final ReqModel reqModel){
        try {
            requestQueue = Volley.newRequestQueue(ctx);

            progressDialog = new ProgressDialog(ctx);
            // Showing progress dialog at user registration time.
            progressDialog.setMessage("Please Wait, Cancelling your Request...");
            progressDialog.show();

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Showing Echo Response Message Coming From Server.
                            //Toast.makeText(ctx, ServerResponse, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);
                                if(jsonObject.getString("code").equals("1")){
                                   // ctx.startActivity(new Intent(ctx,MainContainer.class));
                                    Toast.makeText(ctx,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ctx,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ctx,"From Request Cancelling:"+e.getMessage(),Toast.LENGTH_SHORT).show();
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
                    params.put("googleID",reqModel.getGoogleID());
                    params.put("op", "cancelrequest");

                    return params;
                }

            };
            // Creating RequestQueue.
            requestQueue = Volley.newRequestQueue(ctx);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            Toast.makeText(ctx,"From Request cancelling:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


    public void getAllRequests(final Context ctx, String HttpUrl){
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
                            //Toast.makeText(ctx, ServerResponse, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jobj = new JSONObject(ServerResponse);
                                JSONArray jsonArrayData = jobj.getJSONArray("datas");
                                JSONArray jsonArrayRes = jobj.getJSONArray("response");
                                if(jsonArrayRes.getJSONObject(0).getString("code").equals("1")){
                                    volleyResponseFetcher.onVolleyResponse(jsonArrayData.toString());
                                }else {
                                    Toast.makeText(ctx, jobj.getString("msg"), Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ctx,"From getAllrequests:"+e.getMessage(),Toast.LENGTH_SHORT).show();
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

                    params.put("op", "getallrequests");

                    return params;
                }

            };
            // Creating RequestQueue.
            requestQueue = Volley.newRequestQueue(ctx);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            Toast.makeText(ctx,"From getAllrequests:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

}
