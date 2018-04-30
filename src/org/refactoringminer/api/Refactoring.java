package org.refactoringminer.api;

import java.io.Serializable;

public interface Refactoring extends Serializable {

	RefactoringType getRefactoringType();
	
	String getName();

	String toString();

	void accept(RefactoringVisitor visitor);

}