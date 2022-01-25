package com.reeedking.automatictest.controller;

import com.alibaba.fastjson.JSON;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.reeedking.automatictest.exception.ResourceNotFoundException;
import com.reeedking.automatictest.model.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
class TestControllerTest2 {

    private static ExtentReports extent;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void init() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/index.html");
        extent.attachReporter(spark);
    }

    @SneakyThrows
    @Test
    @DisplayName("测试获取所有")
    void testGetAll() {
        this.mockMvc.perform(get("/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("getAll success")));
        extent.createTest("测试获取所有，展示图片")
                .addScreenCaptureFromPath("test.jpeg")
                .pass(MediaEntityBuilder.createScreenCaptureFromPath("test.jpeg").build());
    }

    @SneakyThrows
    @Test
    @DisplayName("测试获取接口")
    void testGetOne() {
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString("/test/getOne")
                        .build();

        URI uri = uriComponents.toUri();

        this.mockMvc.perform(get(uri).param("id", "233"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("getOne success")));

        extent.createTest("测试获取接口，展示对比框").generateLog(
                Status.PASS,
                MarkupHelper.createCodeBlock("内容展示框1", "内容展示框2"));


    }

    @SneakyThrows
    @Test
    @DisplayName("测试添加接口")
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

        extent.createTest("测试添加接口，不同的status展示")
                .info("info")
                .pass("pass")
                .warning("warn")
                .skip("skip")
                .fail("fail");
    }

    @SneakyThrows
    @Test
    @DisplayName("测试更新所有")
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


        extent.createTest("测试更新所有，添加作者")
                .assignAuthor("reeedking")
                .pass("报告里面的包含了作者");

    }

    @Test
    @SneakyThrows
    @DisplayName("测试删除")
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

        extent.createTest("测试删除")
                .assignDevice("添加设备信息")
                .pass("测试删除接口，在报告中添加了设备信息");
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
//    void testError() {
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
//
//        extent.createTest("测试抛出异常")
//                .fail(new RuntimeException("我抛出了异常"));
//    }

    @AfterAll
    static void afterAll() {
        extent.flush();
    }
}