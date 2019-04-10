package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass;

import java.util.ArrayList;
import java.util.List;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.api.RefactoringVisitor;

public class RenameClassRefactoring implements Refactoring {

	private UMLClass originalClass;
	private UMLClass renamedClass;
	
	public RenameClassRefactoring(UMLClass originalClass,  UMLClass renamedClass) {
		this.originalClass = originalClass;
		this.renamedClass = renamedClass;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("\t");
		sb.append(originalClass.getName());
		sb.append(" renamed to ");
		sb.append(renamedClass.getName());
		return sb.toString();
	}

    @Override
    public void accept(RefactoringVisitor visitor) {
        visitor.visitRenameClass(this);
    }

    public String getName() {
		return this.getRefactoringType().getDisplayName();
	}

	public RefactoringType getRefactoringType() {
		return RefactoringType.RENAME_CLASS;
	}

	public String getOriginalClassName() {
		return originalClass.getName();
	}

	public String getRenamedClassName() {
		return renamedClass.getName();
	}

	public UMLClass getOriginalClass() {
		return originalClass;
	}

	public UMLClass getRenamedClass() {
		return renamedClass;
	}

	public List<String> getInvolvedClassesBeforeRefactoring() {
		List<String> classNames = new ArrayList<String>();
		classNames.add(getOriginalClass().getName());
		return classNames;
	}

	public List<String> getInvolvedClassesAfterRefactoring() {
		List<String> classNames = new ArrayList<String>();
		classNames.add(getRenamedClass().getName());
		return classNames;
	}
}
