package in.co.praveendeshmane.blog.gsonwithvolleyexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ENDPOINT = "https://kylewbanks.com/rest/posts.json";

    private Gson gson;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GsonBuilder  gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        requestQueue = Volley.newRequestQueue(this);
        fetchPosts();
    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));

            Log.i("PostActivity", posts.size() + " posts loaded.");
            for (Post post : posts) {
                Log.i("PostActivity", post.ID + ": " + post.title);
                Toast.makeText(getApplicationContext(),post.ID + ": " + post.title,Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };
}
