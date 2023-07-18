/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.demo.dominio.Objecto;
import com.example.demo.dominio.Respuesta;
import com.example.demo.util.Hilos;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Pedro Medina
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ControloadorCosumo {

    public ArrayList<Respuesta> re = new ArrayList<>();
   
    @GetMapping("/jokes")
    public ArrayList lista() throws InterruptedException {
          re.clear();
        for (int i = 0; i < 26; i++) {
          
          Hilos  h = new Hilos();
           
            h.start();
           
            
        }
        Thread.sleep(2700);
        
        

        
        return re;
    }
   
    @PostMapping(value = "/agregar", consumes = "application/json", produces = "application/json")
    public void  encontrarUsuario(@RequestBody Respuesta respuesta) {
        re.add(respuesta);
    }

    

}
