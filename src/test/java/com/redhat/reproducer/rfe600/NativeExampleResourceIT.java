package com.redhat.reproducer.rfe600;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeExampleResourceIT extends ExampleResourceTest {

    // Execute the same tests but in native mode.
}