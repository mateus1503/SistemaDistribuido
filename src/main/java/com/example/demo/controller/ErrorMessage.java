package com.example.demo.controller;

import org.springframework.http.HttpStatusCode;

public record ErrorMessage(String message, HttpStatusCode status) {

}
