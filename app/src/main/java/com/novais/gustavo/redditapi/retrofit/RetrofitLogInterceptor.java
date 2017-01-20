package com.novais.gustavo.redditapi.retrofit;

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class RetrofitLogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private boolean enableLog = false;


    public RetrofitLogInterceptor(boolean enableLog) {
        this.enableLog = enableLog;
    }

    @Override
    public Response intercept(Chain chain) throws IOException, NullPointerException {
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
        Request request = chain.request();
        if(enableLog) {
            Log.d("DEBUG", "**************************");
            Log.d("DEBUG", "INTERCEPTOR REQUEST");

            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;

            Connection connection = chain.connection();
            Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            if (hasRequestBody) {
                requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
                Log.d("DEBUG", requestStartMessage);
            }
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    Log.d("DEBUG", "Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    Log.d("DEBUG", "Content-Length: " + requestBody.contentLength());
                    long contentLength = requestBody.contentLength();
                    if (contentLength != 0) {
                        Buffer buffer = new Buffer();
                        requestBody.writeTo(buffer);
                        if (isPlaintext(buffer)) {
                            MediaType contentType = requestBody.contentType();
                            Charset charset = contentType.charset(UTF8);
                            Log.d("DEBUG", "[REQUEST]" + buffer.clone().readString(charset));
                        }
                    }
                }
                Headers headers = request.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        Log.d("DEBUG", name + ": " + headers.value(i));
                    }
                }
            }

            if (hasRequestBody) {
                Log.d("DEBUG", "--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                Log.d("DEBUG", "--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                long contentLength = requestBody.contentLength();
                if (contentLength != 0) {
                    Log.d("DEBUG", "[REQUEST]" + buffer.clone().readString(charset));
                }
                if (isPlaintext(buffer)) {
                    Log.d("DEBUG", buffer.readString(charset));
                    Log.d("DEBUG", "--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    Log.d("DEBUG", "--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }

            long startNs = System.nanoTime();
            Response response;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                Log.d("DEBUG", "<-- HTTP FAILED: " + e);
                throw e;
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
            Log.d("DEBUG", "<-- " + response.code() + ' ' + response.message() + ' '
                    + response.request().url() + " (" + tookMs + "ms" + (!true ? ", "
                    + bodySize + " body" : "") + ')');

            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                Log.d("DEBUG", headers.name(i) + ": " + headers.value(i));
            }

            if (bodyEncoded(response.headers())) {
                Log.d("DEBUG", "<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        Log.d("DEBUG", "");
                        Log.d("DEBUG", "Couldn't decode the response body; charset is likely malformed.");
                        Log.d("DEBUG", "<-- END HTTP");

                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {
                    Log.d("DEBUG", "");
                    Log.d("DEBUG", "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0) {
                    Log.d("DEBUG", "");
                    Log.d("DEBUG", "[RESPONSE]" + buffer.clone().readString(charset));
                }

                Log.d("DEBUG", "<-- END HTTP (" + buffer.size() + "-byte body)");


                Log.d("DEBUG", "**************************");
            }
            return response;
        }else {
            return chain.proceed(request);
        }
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
