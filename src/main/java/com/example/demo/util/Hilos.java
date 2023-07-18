/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.util;

import com.example.demo.dominio.Objecto;
import com.example.demo.dominio.Respuesta;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.example.demo.web.ControloadorCosumo;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author Pedro Medina
 */
public class Hilos extends Thread {

    private ArrayList<Objecto> listaObjecto = new ArrayList();

    public Hilos() {

    }
    public List<Objecto> getListaObjecto(){
        return listaObjecto;
    }
    @Override
    public void run() {
        String url = "https://api.chucknorris.io/jokes/random";
        String res = "";
        try {
            CloseableHttpClient httpClient;
            httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
            System.out.println("res" + response.toString());
            System.out.println("----------------------------------------");
            System.out.println(" prueba" + response.getStatusLine());
            // System.out.println(EntityUtils.toString(response.getEntity()));
            res = EntityUtils.toString(response.getEntity());
            Objecto object = new ObjectMapper().readValue(res, Objecto.class);
            Respuesta o = new Respuesta();
            CloseableHttpClient httpClientP = HttpClients.createDefault(); 
            o.setId(object.getId());
            o.setUrl(object.getUrl());
            o.setValue(object.getValue());
            String ob=new ObjectMapper().writeValueAsString(o);
            HttpPost post = new HttpPost("http://localhost:8080/api/agregar");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            System.out.println("");
            StringEntity stringEntity = new StringEntity(ob);
            post.setEntity(stringEntity);
            System.out.println("post");
            System.out.println(post);
            CloseableHttpResponse response1 = httpClientP.execute(post);
            
       
            
           // listaObjecto.add(object);
           //ControloadorCosumo cosumo=new ControloadorCosumo(object);
            //re.add(object);
            // return response.getFirstHeader("WWW-Authenticate");
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
            //return null;
        }

    }
}
