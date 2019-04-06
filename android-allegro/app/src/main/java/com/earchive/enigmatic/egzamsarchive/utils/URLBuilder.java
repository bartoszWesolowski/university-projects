package com.earchive.enigmatic.egzamsarchive.utils;

import android.net.Uri;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Created by Bartek on 2016-05-20.
 */
public class URLBuilder {

    /**
     * Returns new URL with query parameters specified in <code>queryParams</code> appended
     * @param url - base url
     * @param queryParams - map of parrams to append to uri
     * @return new URL with parameters appended
     * @throws MalformedURLException
     */
    public static URL addUrlQueryParams(URL url, Map<String, String> queryParams) throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        String path = url.getPath();
        builder.scheme(url.getProtocol())
                .encodedAuthority(url.getAuthority())
                .appendEncodedPath(StringUtils.startsWith(path, "/") ? StringUtils.replace(path, "/", "") : path);
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        String myUrl = builder.build().toString();
        Log.i("URL:", "Build url: " + myUrl);
        return new URL(myUrl);
    }
}
