package org.refactoringminer.utils.filter;

import org.junit.Before;
import org.junit.Test;
import org.refactoringminer.util.filter.SimpleFileNameFilter;

import static org.hamcrest.CoreMatchers.is;
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