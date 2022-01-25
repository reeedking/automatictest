package com.reeedking.automatictest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.reeedking.automatictest.config.DefaultTestWatcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ExtentReportParent {

    private static ExtentReports extent;

    //注册扩展模型，用于捕获测试状况
    @RegisterExtension
    public DefaultTestWatcher testWatcher = new DefaultTestWatcher(extent);

    @BeforeAll
    static void init() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/index2.html");
        extent.attachReporter(spark);
    }
}
