package com.elkriefy.slackrofit;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface SlackApi {
    String TOKEN = "FILL_TOKEN_HERE";
    String MEMORY_LEAK_CHANNEL = "#FILL_CHANNEL_NAME_HERE";


    @Multipart
    @POST("api/files.upload")
    Call<UploadFileResponse> uploadFile(
            @Query("token") String token,
            @PartMap Map<String, RequestBody> params,
            @Query("filetype") String filetype,
            @Query("filename") String filename, @Query("title") String title,
            @Query("initial_comment") String initialComment, @Query("channels") String channels);


    public static class UploadFileResponse {
        boolean ok;
        String error;

        @Override
        public String toString() {
            return "UploadFileResponse{" +
                    "ok=" + ok +
                    ", error='" + error + '\'' +
                    '}';
        }
    }

    /**
     *
     *
     *
     Argument	Example	Required	Description
     xxxx-xxxxxxxxx-xxxx	Required
     Authentication token (Requires scope: files:write:user)
     file	...	Optional
     File contents via multipart/form-data.
     content	...	Optional
     File contents via a POST var.
     filetype	php	Optional
     Slack-internal file type identifier.
     filename	foo.txt	Required
     Filename of file.
     title	My File	Optional
     Title of file.
     initial_comment	Best!	Optional
     Initial comment to add to file.
     channels	C1234567890	Optional
     Comma-separated list of channel names or IDs where the file will be shared.
     */


}