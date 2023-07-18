/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DigestConect {

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    private HttpHost target;

    public HttpHost getTarget() {
        return target;
    }

    public void setTarget(HttpHost target) {
        this.target = target;
    }

//    public static void main(String[] args) throws IOException {
//
//        setup();
//        if (target == null) {
//            System.out.println("Setup was unsuccesfull01");
//            return;
//        }
//        Header challengeHeader = getAuthChallengeHeader();
//        if (challengeHeader == null) {
//            System.out.println("Setup was unsuccesfull");
//            return;
//        }
    // NOTE: challenge is reused for subsequent HTTP GET calls (typo corrected)
    // getWithDigestAuth(challengeHeader, "/", "/ISAPI/AccessControl/AcsEvent?format=json");
//       String re=getWithDigestAuth1(challengeHeader, "/", "/ISAPI/AccessControl/AcsEvent?format=json");
//        System.out.println(re);
//        JSONObject obj=new JSONObject(re);
//        JSONArray array=obj.getJSONObject("AcsEvent").getJSONArray("InfoList");
//        System.out.println(array.length());
//        
//    }
    public HttpHost setup() throws MalformedURLException {
        URL url = new URL(uri);
        System.out.println(url.getHost() + " " + url.getPort() + " " + url.getProtocol());
        return target = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
    }

    public Header getAuthChallengeHeaderPost(String json) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            System.out.println(json);
            HttpPost post = new HttpPost(uri);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            post.setEntity(stringEntity);
            System.out.println("post");
            System.out.println(post);
            CloseableHttpResponse response = httpClient.execute(post);
            return response.getFirstHeader("WWW-Authenticate");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Header getAuthChallengeHeaderPut(String json) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            System.out.println(json);
            HttpPut put = new HttpPut(uri);
            put.setHeader("Accept", "application/json");
            put.setHeader("Content-type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            put.setEntity(stringEntity);
            System.out.println("put");
            System.out.println(put);
            CloseableHttpResponse response = httpClient.execute(put);
            return response.getFirstHeader("WWW-Authenticate");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Header getAuthChallengeHeaderGet() {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(new HttpGet(uri));
            return response.getFirstHeader("WWW-Authenticate");
            

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getWithDigestAuthGet(Header challengeHeader, String... requests)
            throws IOException {
        String res="";
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("admin", "Adm1n199"));

        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build()) {

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate DIGEST scheme object, initialize it and add it to the local
            // auth cache
            DigestScheme digestAuth = new DigestScheme();
            digestAuth.processChallenge(challengeHeader);
            authCache.put(target, digestAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            for (String request : requests) {
                System.out.println("Executing request to target " + target + request);
                try (CloseableHttpResponse response = httpclient
                        .execute(target, new HttpGet(request), localContext)) {
                    System.out.println("----------------------------------------");
                    System.out.println(" prueba" + response.getStatusLine());
                 
                    res = EntityUtils.toString(response.getEntity());
                } catch (Exception e) {
                    System.out.println("Error while executing HTTP GET request");
                    e.printStackTrace();
                }
            }
        } catch (MalformedChallengeException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getWithDigestAuthPost(String json, Header challengeHeader, String... requests)
            throws IOException {
        String res = "";
        JSONObject obj = null;
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("admin", "Adm1n199"));

        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build()) {

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate DIGEST scheme object, initialize it and add it to the local
            // auth cache
            DigestScheme digestAuth = new DigestScheme();
            digestAuth.processChallenge(challengeHeader);
            authCache.put(target, digestAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);
            int i = 0;
            System.out.println(requests);
            // for (String request : requests) {
            System.out.println("Executing request to target " + target + requests[0]);

            System.out.println("json" + json);
            HttpPost post = new HttpPost(uri);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            post.setEntity(stringEntity);
            System.out.println("post");
            System.out.println(post);
            try {
                CloseableHttpResponse response = httpclient
                        .execute(target, post, localContext);
                System.out.println("----------------------------------------");
                System.out.println(i + " prueba" + response.getStatusLine());
                //System.out.println(EntityUtils.toString(response.getEntity()));
                // System.out.println(EntityUtils.toString(response.getEntity()));
                res = EntityUtils.toString(response.getEntity());

//                    
                i++;
            } catch (Exception e) {
                System.out.println("Error while executing HTTP GET request");
                e.printStackTrace();
            }
            // }

        } catch (MalformedChallengeException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getWithDigestAuthPut(String json, Header challengeHeader, String... requests)
            throws IOException {
        String res = "";
        JSONObject obj = null;
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("admin", "Adm1n199"));

        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build()) {

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate DIGEST scheme object, initialize it and add it to the local
            // auth cache
            DigestScheme digestAuth = new DigestScheme();
            digestAuth.processChallenge(challengeHeader);
            authCache.put(target, digestAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);
            int i = 0;
            // for (String request : requests) {
            System.out.println("Executing request to target " + target + requests[0]);

            System.out.println("json" + json);
            HttpPut put = new HttpPut(uri);
            put.setHeader("Accept", "application/json");
            put.setHeader("Content-type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            put.setEntity(stringEntity);
            System.out.println("post");
            System.out.println(put);
            try {
                CloseableHttpResponse response = httpclient
                        .execute(target, put, localContext);
                System.out.println("----------------------------------------");
                System.out.println(" prueba" + response.getStatusLine());
                //System.out.println(EntityUtils.toString(response.getEntity()));
                // System.out.println(EntityUtils.toString(response.getEntity()));
                res = EntityUtils.toString(response.getEntity());

//                    
                i++;
            } catch (Exception e) {
                System.out.println("Error while executing HTTP GET request");
                e.printStackTrace();
            }
            // }

        } catch (MalformedChallengeException e) {
            e.printStackTrace();
        }
        return res;
    }

}
