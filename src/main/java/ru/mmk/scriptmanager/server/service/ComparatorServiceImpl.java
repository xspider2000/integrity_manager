package ru.mmk.scriptmanager.server.service;

import groovy.sql.GroovyRowResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.mmk.scriptmanager.server.domain.Node;
import ru.mmk.scriptmanager.server.domain.Source;
import ru.mmk.scriptmanager.server.util.Comparator;
import ru.mmk.scriptmanager.server.util.SourceDataGetter;

@Service
public class ComparatorServiceImpl implements ComparatorService {

	@Autowired
	TreeNodeService treeNodeService;

	@Override
	public Object compareSourcesData(Integer nodeId) {
		Node node = treeNodeService.getNodeById(nodeId);
		Set<Source> sources = node.getSources();
		Map<String, List<GroovyRowResult>> dataOfSources = new HashMap<String, List<GroovyRowResult>>();
		List<GroovyRowResult> masterData = null;
		List<String> idFields = new ArrayList<String>();
		for (Source source : sources) {
			SourceDataGetter sourceDataGetter = new SourceDataGetter(source);
			if (source.isMaster()) {
				masterData = sourceDataGetter.getExecutionResult();
				idFields = sourceDataGetter.getIdFields();
				continue;
			}
			dataOfSources.put(source.getName(), sourceDataGetter.getExecutionResult());
		}
		Comparator comparator = new Comparator();
		@SuppressWarnings("unchecked")
		Map<String, Map<String, Object>> result = (Map<String, Map<String, Object>>) comparator.compareSources(
				idFields, masterData, dataOfSources, nodeId);

		treeNodeService.setLastRunDate(nodeId);
		treeNodeService.incrementRunCount(nodeId);
		if (hasDifference(result)) {
			treeNodeService.incrementErrorCount(nodeId);
		}

		return result;
	}

	boolean hasDifference(Map<String, Map<String, Object>> result) {
		return !Boolean.valueOf(result.get("meta").get("noDifference").toString());
	}
}