package ru.mmk.scriptmanager.server.util;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.sql.GroovyRowResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import ru.mmk.scriptmanager.server.domain.Source;

public class SourceDataGetter {
	private Source source;
	private List<String> idFields;

	public SourceDataGetter(Source source) {
		this.source = source;
	}

	@SuppressWarnings("unchecked")
	public List<GroovyRowResult> getExecutionResult() {
		Binding binding = new Binding();
		binding.setVariable("CONFIG", new Config());
		GroovyShell shell = new GroovyShell(binding);
		List<GroovyRowResult> executionResult = (List<GroovyRowResult>) shell.evaluate(source.getCode());
		if (binding.hasVariable("idFields")) {
			idFields = (List<String>) binding.getVariable("idFields");
		}
		return executionResult;
	}

	public String getExecutionOutput() {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		PrintStream savedSystemOut = redirectSysout(buffer);
		getExecutionResult();
		returnSysout(savedSystemOut);
		String output = buffer.toString();
		return output;
	}

	private PrintStream redirectSysout(ByteArrayOutputStream sysoutInterception) {
		PrintStream saveSystemOut = System.out;
		System.setOut(new PrintStream(sysoutInterception));
		return saveSystemOut;
	}

	private void returnSysout(PrintStream saveSystemOut) {
		System.setOut(saveSystemOut);
	}

	public List<String> getIdFields() {
		return idFields;
	}
}
