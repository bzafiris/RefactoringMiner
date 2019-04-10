package org.refactoringminer.util.filter;

public class FileFilterFactory {

    public static FileNameFilter getProductionCodeFilter(){
        SimpleFileNameFilter fileNameFilter = new SimpleFileNameFilter();
        fileNameFilter.addExclusionPattern("src/test");
        fileNameFilter.addExclusionPattern("javatests");
        fileNameFilter.addExclusionPattern("examples");
        fileNameFilter.addExclusionPattern("tests");
        return fileNameFilter;
    }
}
