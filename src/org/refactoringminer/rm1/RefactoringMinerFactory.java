package org.refactoringminer.rm1;

import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.utils.filter.FileNameFilter;
import org.refactoringminer.utils.filter.PatternFileNameFilter;

public class RefactoringMinerFactory {

    public static GitHistoryRefactoringMiner createDefaultGitHistoryMiner(){
        return new GitHistoryRefactoringMinerImpl();
    }

    public static GitHistoryRefactoringMiner createProductionCodeGitHistoryMiner(){
        FileNameFilter fileNameFilter = new PatternFileNameFilter();
        GitHistoryRefactoringMinerImpl gitHistoryRefactoringMiner = new GitHistoryRefactoringMinerImpl();
        gitHistoryRefactoringMiner.setSourceFileFilter(fileNameFilter);
        // configure the refactoring miner with an appropriate filter
        return null;
    }

}
