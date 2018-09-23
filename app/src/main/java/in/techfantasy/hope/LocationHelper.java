package in.techfantasy.hope;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;



public class LocationHelper {
    public SharedPreferences sharedpreferences;
    public void decodeLocation(final Location location, final Context ctx) {
try {


    if (location != null) {
//            final String text = String.format("Latitude %.6f, Longitude %.6f",
//                    location.getLatitude(),
//                    location.getLongitude());
        // We are going to get the address for the current position
        SmartLocation.with(ctx).geocoding().reverse(location, new OnReverseGeocodingListener() {

            @Override
            public void onAddressResolved(Location original, List<Address> results) {
                if (results.size() > 0) {
                    Address result = results.get(0);


                    StringBuilder builder = new StringBuilder("");
                    //builder.append("\n[Reverse Geocoding] ");
                    List<String> addressElements = new ArrayList<>();
                    for (int i = 0; i <= result.getMaxAddressLineIndex(); i++) {
                        addressElements.add(result.getAddressLine(i));

                    }
                    builder.append(TextUtils.join(", ", addressElements));
                    sharedpreferences = ctx.getSharedPreferences(Globals.sharedPrefName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("myLon", "" + location.getLongitude());
                    editor.putString("myLat", "" + location.getLatitude());
                    editor.putString("myCoord", location.getLatitude() + "," + location.getLongitude());
                    editor.putString("myCountry", result.getCountryName());
                    editor.putString("myDistrict", result.getSubAdminArea());
                    editor.putString("myState", result.getAdminArea());
                    editor.putString("myCity", result.getFeatureName());
                    editor.putString("myPincode", result.getPostalCode());
                    editor.putString("myAddress", builder.toString());

                    editor.commit();
                    editor.apply();
                }
            }

        });
        SmartLocation.with(ctx).location().stop(); //to be checked
    } else {

    }
}
catch (Exception ex){
    Log.e("From Location helper",ex.getMessage());
}
    }

}
