package org.refactoringminer.util.filter;

public class FileFilterFactory {

    public static FileNameFilter getProductionCodeFilter(){
        SimpleFileNameFilter fileNameFilter = new SimpleFileNameFilter();
        fileNameFilter.addExclusionPattern("src/test");
        return fileNameFilter;
    }
}
