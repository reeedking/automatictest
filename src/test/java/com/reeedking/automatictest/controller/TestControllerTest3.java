package com.reeedking.automatictest.controller;

import com.alibaba.fastjson.JSON;
import com.reeedking.automatictest.exception.ResourceNotFoundException;
import com.reeedking.automatictest.model.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest //使用该注解只会启动web层
class TestControllerTest3 {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DisplayName("测试获取所有2")
    void testGetAll() {
        this.mockMvc.perform(get("/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("getAll success")));
    }

    @SneakyThrows
    @Test
    @DisplayName("测试获取接口2")
    void testGetOne() {
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString("/test/getOne")
                        .build();

        URI uri = uriComponents.toUri();

        this.mockMvc.perform(get(uri).param("id", "233"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("getOne success")));
    }

    @SneakyThrows
    @Test
    @DisplayName("测试添加接口2")
    void testAdd() {
        Person person = new Person("1", "reeedking", 27);
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString("/test")
                        .build();

        URI uri = uriComponents.toUri();
        this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(person)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("add success")));
    }

    @SneakyThrows
    @Test
    @DisplayName("测试更新所有2")
    void testUpdate() {
        Person person = new Person("1", "reeedking", 27);
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString("/test")
                        .build();

        URI uri = uriComponents.toUri();
        this.mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(person)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("update success")));
    }

    @Test
    @SneakyThrows
    @DisplayName("测试删除2")
    void testDelete() {

        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString("/test/{id}")
                        .build()
                        .expand("123");

        URI uri = uriComponents.toUri();
        this.mockMvc.perform(delete(uri))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("delete success")));
    }

    @SneakyThrows
    @Test
    @DisplayName("测试能成功捕获异常")
    void testAddException() {
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString("/test/getOne")
                        .build();

        URI uri = uriComponents.toUri();

        this.mockMvc.perform(get(uri).param("id", "123"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("未找到id：123", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

//    @SneakyThrows
//    @Test
//    @DisplayName("测试抛出异常")
//    void testError(){
//        UriComponents uriComponents =
//                UriComponentsBuilder.fromUriString("/test/getOne")
//                        .build();
//
//        URI uri = uriComponents.toUri();
//
//        this.mockMvc.perform(get(uri).param("id", "123"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("getOne success")));
//    }

}