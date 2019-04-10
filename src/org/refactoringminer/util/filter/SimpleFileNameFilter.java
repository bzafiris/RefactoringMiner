package org.refactoringminer.util.filter;

import org.refactoringminer.util.filter.FileNameFilter;

import java.util.ArrayList;
import java.util.List;

public class SimpleFileNameFilter implements FileNameFilter {

    private List<String> exclusionPatterns = new ArrayList<>();

    @Override
    public boolean accept(String fileName) {

        if (exclusionPatterns.isEmpty()){
            return true;
        }
        for(String pattern: exclusionPatterns){
            if (fileName.contains(pattern)){
                return false;
            }
        }

        return true;
    }

    public void addExclusionPattern(String pattern){
        exclusionPatterns.add(pattern);
    }
}
