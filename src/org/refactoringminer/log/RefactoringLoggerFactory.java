package org.refactoringminer.log;

import org.refactoringminer.RefactoringMiner;

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
        return new DetailedCSVLogger();
    }

    public AbstractCSVLogger getCommitAggregateCSVLogger(){
        return new CommitAggregateCSVLogger();
    }

    public AbstractCSVLogger getCSVLogger(String type){
        if (RefactoringMiner.LOGGER_TYPE_AGGREGATE.equals(type)){
            return new CommitAggregateCSVLogger();
        } else if (RefactoringMiner.LOGGER_TYPE_DEFAULT.equals(type)){
            return new DefaultCSVLogger();
        } else if (RefactoringMiner.LOGGER_TYPE_DETAILED.equals(type)){
            return new DetailedCSVLogger();
        }

        return new DefaultCSVLogger();

    }



}
