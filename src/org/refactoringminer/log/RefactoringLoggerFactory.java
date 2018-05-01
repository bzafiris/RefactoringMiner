package org.refactoringminer.log;

public class RefactoringLoggerFactory {

    private static RefactoringLoggerFactory theInstance;

    public static RefactoringLoggerFactory getInstance(){
        if (theInstance == null){
            theInstance = new RefactoringLoggerFactory();
        }
        return theInstance;
    }

    public AbstractCSVLogger getDefaultLogger(){
        return new DefaultCSVLogger();
    }

    public AbstractCSVLogger getDetailedCSVLogger(){
        return new DefaultCSVLogger();
    }

}
