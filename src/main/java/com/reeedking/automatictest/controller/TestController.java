package com.reeedking.automatictest.controller;

import com.reeedking.automatictest.exception.ResourceNotFoundException;
import com.reeedking.automatictest.model.Person;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public Object getAll() {
        return "getAll success";
    }

    @SneakyThrows
    @GetMapping("/getOne")
    public Object getOne(@RequestParam String id) {
        System.out.println("传入的id为：" + id);
        if (("123").equals(id)) {
            throw new ResourceNotFoundException("未找到id：123");
        }
        return "getOne success";
    }


    @PostMapping
    public Object add(@RequestBody Person person) {
        System.out.println("接收到的person为");
        System.out.println(person);
        return "add success";
    }

    @PutMapping
    public Object update(@RequestBody Person person) {
        System.out.println("接收到的person为");
        System.out.println(person);
        return "update success";
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable String id) {
        System.out.println("接收到的id为:" + id);
        return "delete success";
    }

}
