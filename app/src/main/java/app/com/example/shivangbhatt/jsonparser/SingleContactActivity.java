package app.com.example.shivangbhatt.jsonparser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import info.androidhive.jsonparsing.R;

public class SingleContactActivity  extends Activity {

    GPSTracker gps;
    double latitude;
    double longitude;
    String url;
    String name;
    String email;
    String mobile;
    String id;
	
	// JSON node keys
	private static final String TAG_ID = "id";
//	private static final String TAG_EMAIL = "email";
//	private static final String TAG_PHONE_MOBILE = "mobile";
    private static final String TAG_AGE = "age";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);

        gps = new GPSTracker(SingleContactActivity.this);
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            url = "http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+12.964831+","+77.597466;

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            ClipboardManager _clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            _clipboard.setText(url);

        }else{
            gps.showSettingsAlert();
        }
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        id = in.getStringExtra(TAG_ID);
//        email = in.getStringExtra(TAG_EMAIL);
//        mobile = in.getStringExtra(TAG_PHONE_MOBILE);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblEmail = (TextView) findViewById(R.id.email_label);
        TextView lblMobile = (TextView) findViewById(R.id.mobile_label);
        
        lblName.setText(id);
//        lblEmail.setText(email);
//        lblMobile.setText(mobile);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //second working code with two locations
                String url = "http://maps.google.com/maps?saddr="+12.937034+","+77.777624+"&daddr="+12.964831+","+77.597466;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);



                // String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 10.10, 23.23);
//                String label = "Cinnamon & Toast";
                //first working code with single location
               /* String uriBegin = "geo:12.964831,77.597466";
                String query = "12.964831, 77.597466";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri = Uri.parse( uriString );
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                startActivity( intent ); */

               /* Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                Context context = getApplicationContext();
                context.startActivity(intent); */
            }
        });

        Button locbutton = (Button) findViewById(R.id.smsbutton);

        locbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+919470992979", null, url, null, null);
                Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_SHORT).show();

            }
        });

        Button selmbutton = (Button) findViewById(R.id.selmbutton);
        selmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to open contact in whatsapp
                Uri mUri = Uri.parse("smsto:+919470992979");
                Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
                //mIntent.setType("text/plain");
                mIntent.putExtra(mIntent.EXTRA_TEXT, email);
                mIntent.setPackage("com.whatsapp");



                // mIntent.putExtra("hello", "world");
                // mIntent.putExtra("chat",true);
                //mIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, "+919470992979");
                startActivity(mIntent);

            }
        });
    }
}
