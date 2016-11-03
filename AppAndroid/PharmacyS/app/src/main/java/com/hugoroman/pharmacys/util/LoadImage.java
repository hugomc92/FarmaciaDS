package com.hugoroman.pharmacys.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.hugoroman.pharmacys.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;

    public LoadImage(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            return downloadBitmap(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled()) {
            bitmap = null;
        }

        if(imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Drawable placeholder;

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        placeholder = imageView.getContext().getDrawable(R.mipmap.logo);
                    else
                        placeholder = imageView.getContext().getResources().getDrawable(R.mipmap.logo);

                    imageView.setImageDrawable(placeholder);
                }
            }
        }
    }

    private Bitmap downloadBitmap(String url) {

        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}