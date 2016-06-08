package com.klymchuk.test;

import com.klymchuk.util.Data;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

/**
 * Created by iklymchuk on 6/8/16.
 */
public class BasicTest {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = Data.BASE_URI;
    }
}
