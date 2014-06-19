package ru.mmk.scriptmanager.server.util

import groovy.json.JsonBuilder
import groovy.sql.GroovyRowResult

import org.slf4j.Logger
import org.slf4j.LoggerFactory


/* Каждый источник сравнивается с мастером.
 * Вначале удаляются все совпадающие строки в мастере и источнике, 
 * затем оставшиеся строки сравниваются между собой по ключевым полям 
 * и находятся значения полей, в которых отличаются значения.
 */
class Comparator {
	final private static ResultLogger logger = new ResultLogger()

	public compareSources(List<String> idFields,
			List<GroovyRowResult> masterData, Map<String,
			List<GroovyRowResult> > dataOfSourcesToCompare,
			Integer nodeId) {

		validateDatasources(masterData, dataOfSourcesToCompare);
		Boolean noDifferenceBetweenSources = true;
		def result = [:]
		result.meta = [:]

		for(sourceName in dataOfSourcesToCompare.keySet()) {
			def differentRows = []
			def sourceRowsMap = [:]
			def masterRowsMap = [:]

			def dataOfSource = dataOfSourcesToCompare[sourceName]

			def masterRows = (masterData as HashSet) - (dataOfSource as HashSet)
			def sourceRows = (dataOfSource as HashSet) - (masterData as HashSet)

			noDifferenceBetweenSources = masterRows.empty && sourceRows.empty;

			if(idFields == null || idFields.size()==0) {
				result.meta.noDifference = noDifferenceBetweenSources;

				result[sourceName] = [
					'differentRows' : [],
					'missingRows': masterRows,
					'extraRows': sourceRows
				]
				continue
			}

			initMasterRowsMap(masterRows, idFields, masterRowsMap)
			initSourceRowsMap(sourceRows, idFields, sourceRowsMap)



			Map copyMasterRowsMap = masterRowsMap.clone();
			for(masterRowKey in masterRowsMap.keySet()){
				def masterRow = masterRowsMap[masterRowKey]

				if(sourceRowsMap.containsKey(masterRowKey)) {
					def sourceRow = sourceRowsMap[masterRowKey]
					def differentRow = sourceRow;

					for(fieldName in masterRow.keySet()) {
						def masterRowFieldValue = masterRow[fieldName]
						def sourceRowFieldValue = sourceRow[fieldName]

						if(!masterRowFieldValue.equals(sourceRowFieldValue)) {
							differentRow[fieldName] = ['masterValue' : masterRowFieldValue, 'sourceValue' : sourceRowFieldValue];
						}
					}
					differentRows << differentRow;
					sourceRowsMap.remove(masterRowKey);
					copyMasterRowsMap.remove(masterRowKey);
				}
			}


			result.meta.noDifference = noDifferenceBetweenSources;

			result[sourceName] = [
				'differentRows' : differentRows,
				'missingRows': masterRowsMap.values(),
				'extraRows': sourceRowsMap.values()
			]
		}
		if(noDifferenceBetweenSources)
			logger.info(new JsonBuilder(result).toPrettyString(), nodeId)
		else
			logger.warn(new JsonBuilder(result).toPrettyString(), nodeId)
		return result
	}

	private initMasterRowsMap(List masterData, List idFields, Map masterRowsMap) {
		for(masterRow in masterData) {
			def key=[]
			for(idField in idFields) {
				key<<masterRow[idField]
			}
			masterRowsMap[key]=masterRow;
		}
	}

	private initSourceRowsMap(Set extraRows, List idFields, Map extraRowsMap) {
		for(extraRow in extraRows) {
			def key=[]
			for(idField in idFields) {
				key<<extraRow[idField]
			}
			extraRowsMap[key]=extraRow;
		}
	}

	private initMasterRowsMap(Set missingRows, List idFields, Map missingRowsMap) {
		for(missingRow in missingRows) {
			def key=[]
			for(idField in idFields) {
				key<<missingRow[idField]
			}
			missingRowsMap[key]=missingRow;
		}
	}

	private void validateDatasources(List<GroovyRowResult> masterData, Map<String, List<GroovyRowResult> > sourcesDataMapToCompare) {
		def masterColumnSet =  masterData.first().keySet()
		for(sourcesDataMapEntry in sourcesDataMapToCompare) {
			def sourceName = sourcesDataMapEntry.key
			def sourceData = sourcesDataMapEntry.value
			def columnSet = sourceData.first().keySet()
			if(!masterColumnSet.equals(columnSet)) {
				throw new RuntimeException("Columns in master and in " + sourceName + " are different!");
			}
		}
	}
}
