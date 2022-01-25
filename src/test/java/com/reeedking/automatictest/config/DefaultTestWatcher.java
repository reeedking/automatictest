package com.reeedking.automatictest.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class DefaultTestWatcher implements TestWatcher {


    private ExtentReports extent;

    public DefaultTestWatcher(ExtentReports extent) {
        this.extent = extent;
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        ExtentTest test = extent.createTest(context.getDisplayName()).pass("success");
        flushReports(extent, test);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable e) {
        ExtentTest test = extent.createTest(context.getDisplayName()).fail(e);
        flushReports(extent, test);
    }

    private void flushReports(ExtentReports extent, ExtentTest test) {
        extent.flush();
    }

}
