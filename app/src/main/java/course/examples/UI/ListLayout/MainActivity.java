package course.examples.UI.ListLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> schoolnames;
    ArrayList<String> schoolinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        schoolnames = new ArrayList<>();
        schoolinfo = new ArrayList<>();
        //lv = findViewById(R.id.listview);
        // Create a new Adapter containing a list of colors
        // Set the adapter on this ListView
        ListView lv = (ListView) findViewById(R.id.listview);

        //System.out.println(getHtmlByteArray("https://data.cityofnewyork.us/resource/s3k6-pzi2.json"));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder content = new StringBuilder();
        try
        {
            URL url = new URL("https://data.cityofnewyork.us/resource/s3k6-pzi2.json"); // creating a url object
            URLConnection urlConnection = url.openConnection(); // creating a urlconnection object

            // wrapping the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // reading from the urlconnection using the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
                System.out.println(line);
                String[] newtemp = line.split("\"school_name\":");
                System.out.println(newtemp[1]);
                String SchoolName = newtemp[1].split(",")[0];

                String SchoolInformation = newtemp[1].split("overview_paragraph")[1];
                System.out.println(SchoolInformation);
                System.out.println(SchoolName);
                schoolinfo.add(SchoolInformation.split("\"")[2]);
                schoolnames.add(SchoolName);
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                schoolnames));



        try{
            //used this and a ton of other useless stuff laying around for debugging sorry im not cleaning it all it shows my process and i have more work to do i do hope i get this opertunity though
            URL url=new URL("https://data.cityofnewyork.us/resource/s3k6-pzi2.json");
            System.out.println("Protocol: "+url.getProtocol());// Using getProtocol() method of the URL class
            System.out.println("Host Name: "+url.getHost()); // Using getHost() method
            System.out.println("Port Number: "+url.getPort());  // Using getPort() method
            System.out.println("File Name: "+url.getFile());    //Using getFile() method
        }
        catch(Exception e)
        {
            System.out.println(e);}

    // Enable filtering when the user types in the virtual keyboard
        lv.setTextFilterEnabled(true);

        // Set an setOnItemClickListener on the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Display a Toast message indicting the selected item
				Toast.makeText(getApplicationContext(),
                        schoolinfo.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

}