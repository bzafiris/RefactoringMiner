package org.refactoringminer.rm1;

import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.utils.filter.FileFilterFactory;
import org.refactoringminer.utils.filter.FileNameFilter;
import org.refactoringminer.utils.filter.SimpleFileNameFilter;

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
