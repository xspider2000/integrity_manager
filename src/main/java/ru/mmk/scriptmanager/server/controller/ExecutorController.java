package ru.mmk.scriptmanager.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import ru.mmk.scriptmanager.server.domain.Source;
import ru.mmk.scriptmanager.server.util.SourceDataGetter;

@Controller
public class ExecutorController {
	@RequestMapping("/execute/source")
	public @ResponseBody
	ResponseOutput getSourceOutput(@RequestBody Source source) {
		String executionOutput = new SourceDataGetter(source).getExecutionOutput();
		return new ResponseOutput(escapeChars(executionOutput));
	}

	private String escapeChars(String string) {
		return HtmlUtils.htmlEscape(string.trim());
	}
}

class ResponseOutput {
	private String output;

	public ResponseOutput(String output) {
		this.output = output;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}