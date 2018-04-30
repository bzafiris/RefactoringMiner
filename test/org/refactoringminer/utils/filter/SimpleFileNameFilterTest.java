package org.refactoringminer.utils.filter;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleFileNameFilterTest {

    SimpleFileNameFilter fileNameFilter;

    @Before
    public void setUp() {
        fileNameFilter = new SimpleFileNameFilter();
    }


    @Test
    public void rejectTestPaths(){

        assertThat(fileNameFilter.accept("src/test"), is(false));
    }
}