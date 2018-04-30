package org.refactoringminer.rm1;

import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.util.filter.FileFilterFactory;
import org.refactoringminer.util.filter.FileNameFilter;

public class RefactoringMinerFactory {

    public static GitHistoryRefactoringMiner createDefaultGitHistoryMiner(){
        return new GitHistoryRefactoringMinerImpl();
    }

    public static GitHistoryRefactoringMiner createProductionCodeGitHistoryMiner(){
        FileNameFilter fileNameFilter = FileFilterFactory.getProductionCodeFilter();
        GitHistoryRefactoringMinerImpl gitHistoryRefactoringMiner = new GitHistoryRefactoringMinerImpl();
        gitHistoryRefactoringMiner.setSourceFileFilter(fileNameFilter);
        // configure the refactoring miner with an appropriate filter
        return gitHistoryRefactoringMiner;
    }

}
