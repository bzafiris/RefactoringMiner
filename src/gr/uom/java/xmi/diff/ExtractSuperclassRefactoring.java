package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.api.RefactoringVisitor;

public class ExtractSuperclassRefactoring implements Refactoring {
	private UMLClass extractedClass;
	private Set<UMLClass> subclassSet;
	
	public ExtractSuperclassRefactoring(UMLClass extractedClass, Set<UMLClass> subclassSet) {
		this.extractedClass = extractedClass;
		this.subclassSet = subclassSet;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("\t");
		sb.append(extractedClass);
		sb.append(" from classes ");
		sb.append(subclassSet);
		return sb.toString();
	}

    @Override
    public void accept(RefactoringVisitor visitor) {
        visitor.visitExtractSuperclass(this);
    }

    public String getName() {
		return this.getRefactoringType().getDisplayName();
	}

	public RefactoringType getRefactoringType() {
		if(extractedClass.isInterface())
			return RefactoringType.EXTRACT_INTERFACE;
		else
			return RefactoringType.EXTRACT_SUPERCLASS;
	}

	public UMLClass getExtractedClass() {
		return extractedClass;
	}

	public Set<String> getSubclassSet() {
		Set<String> subclassSet = new LinkedHashSet<String>();
		for(UMLClass umlClass : this.subclassSet) {
			subclassSet.add(umlClass.getName());
		}
		return subclassSet;
	}

	public Set<UMLClass> getUMLSubclassSet() {
		return new LinkedHashSet<UMLClass>(subclassSet);
	}

	public List<String> getInvolvedClassesBeforeRefactoring() {
		List<String> classNames = new ArrayList<String>();
		classNames.addAll(getSubclassSet());
		return classNames;
	}

	public List<String> getInvolvedClassesAfterRefactoring() {
		List<String> classNames = new ArrayList<String>();
		classNames.add(getExtractedClass().getName());
		return classNames;
	}
}
