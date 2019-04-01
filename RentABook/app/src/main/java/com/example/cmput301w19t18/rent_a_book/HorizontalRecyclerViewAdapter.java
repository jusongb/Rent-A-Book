package com.example.cmput301w19t18.rent_a_book;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The type Horizontal recycler view adapter.
 * The view adapter is able to get the arraylist to create the horizontal recyclerview in the nestedrecyclerview in the home activity
 *
 * https://stackoverflow.com/questions/35548110/setimageresource-
 */
public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.HorizontalRVViewHolder> {

    /**
     * The Context.
     */
    Context context;
    /**
     * The Array list.
     */
    ArrayList<Book> arrayList;

    //volley stuff
    private RequestQueue mQueue;

    /**
     * Instantiates a new Horizontal recycler view adapter.
     *
     * @param context   the context
     * @param arrayList the array list
     */
    public HorizontalRecyclerViewAdapter(Context context, ArrayList<Book> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HorizontalRVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_book_item, viewGroup, false);
        return new HorizontalRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalRVViewHolder horizontalRVViewHolder, final int i) {
        mQueue = Volley.newRequestQueue(context);
        final Book currentBook = arrayList.get(i);
        horizontalRVViewHolder.ratingBar.setRating(currentBook.getRating());

        String jsonText = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + currentBook.getISBN() + "&key=AIzaSyBazEyC2EkUpHmYKCh3NNS-Zq2inaSB7_0";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, jsonText, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    String json_img = jsonObject.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");

                    Picasso.get().load(json_img).into(horizontalRVViewHolder.bookCover);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

        horizontalRVViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookDetails.class);

                intent.putExtra("ratings", String.valueOf(arrayList.get(i).getRating()));
                intent.putExtra("btitle", arrayList.get(i).getBtitle());
                intent.putExtra("bookphoto","http://covers.openlibrary.org/b/isbn/" + arrayList.get(i).getISBN() + "-M.jpg");
                intent.putExtra("bstatus",arrayList.get(i).getBstatus());
                intent.putExtra("owner",arrayList.get(i).getbOwner());

                intent.putExtra("mode", "2");
                Activity origin = (Activity) context;
                origin.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /**
     * The type Horizontal rv view holder.
     */
    public class HorizontalRVViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Rating bar.
         */
        RatingBar ratingBar;
        /**
         * The Book cover.
         */
        ImageView bookCover;

        /**
         * Instantiates a new Horizontal rv view holder.
         *
         * @param itemView the item view
         */
        public HorizontalRVViewHolder(View itemView) {
            super(itemView);
            ratingBar = (RatingBar)itemView.findViewById(R.id.bookRating);
            bookCover = (ImageView)itemView.findViewById(R.id.bookCover);
        }
    }
}
