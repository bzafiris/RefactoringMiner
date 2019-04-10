package org.refactoringminer.log;

import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;

import java.util.List;

public interface RefactoringLogger {

    void log(RevCommit commitData, List<Refactoring> refactorings);

}
