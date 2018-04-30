package org.refactoringminer.util;

public class StringUtils {

    /**
     * Replace new line characters, with space
     * @param input
     * @return
     */
    public static String trimWhitespaces(String input){
        return  input.replaceAll("\\s+"," ");
    }

}
