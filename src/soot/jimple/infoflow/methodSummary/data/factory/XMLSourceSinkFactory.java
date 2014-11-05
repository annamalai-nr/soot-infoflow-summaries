package soot.jimple.infoflow.methodSummary.data.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import soot.jimple.infoflow.methodSummary.data.FlowSink;
import soot.jimple.infoflow.methodSummary.data.FlowSource;
import soot.jimple.infoflow.methodSummary.data.SourceSinkType;
import soot.jimple.infoflow.methodSummary.xml.XMLConstants;

public class XMLSourceSinkFactory {
	public static FlowSource createSource(Map<String, String> attributes){
		if(isField(attributes)){
			return new FlowSource(SourceSinkType.Field, -1, getFields(attributes));
		}else if(isParameter(attributes)){
			return new FlowSource(SourceSinkType.Parameter, paramterIdx(attributes), getFields(attributes));
		}
		return null;
	}
	
	public static FlowSink createSink(Map<String, String> attributes){
		if(isField(attributes)){
			return new FlowSink(SourceSinkType.Field, -1,getFields(attributes), taintSubFields(attributes));
		}else if(isParameter(attributes)){
			return new FlowSink(SourceSinkType.Parameter, paramterIdx(attributes),getFields(attributes),taintSubFields(attributes));
		}else if(isReturn(attributes)){
			return new FlowSink(SourceSinkType.Return, -1,getFields(attributes), taintSubFields(attributes));
		}
		
		return null;
	}
	
	private static boolean isReturn(Map<String, String> attributes){
		return attributes.get(XMLConstants.ATTRIBUTE_FLOWTYPE).equals(XMLConstants.VALUE_RETURN);
	}
	
	private static boolean isField(Map<String, String> attributes){
		return attributes.get(XMLConstants.ATTRIBUTE_FLOWTYPE).equals(XMLConstants.VALUE_FIELD);
	}
	private static List<String> getFields(Map<String, String> attributes){
		if(attributes.containsKey(XMLConstants.ATTRIBUTE_ACCESSPATH)){
			return Arrays.asList(attributes.get(XMLConstants.ATTRIBUTE_ACCESSPATH).split("."));
		}
		return null;
	}
	
	private static boolean isParameter(Map<String, String> attributes){
		return attributes.get(XMLConstants.ATTRIBUTE_FLOWTYPE).equals(XMLConstants.VALUE_PARAMETER);
	}
	
	private static int paramterIdx(Map<String, String> attributes){
		return  Integer.parseInt(attributes.get(XMLConstants.ATTRIBUTE_PARAMTER_INDEX));
	}
	private static boolean taintSubFields(Map<String, String> attributes){
		String val = attributes.get(XMLConstants.ATTRIBUTE_TAINT_SUB_FIELDS);
		return val == null || !val.equals("false");
	}
	
	
}
