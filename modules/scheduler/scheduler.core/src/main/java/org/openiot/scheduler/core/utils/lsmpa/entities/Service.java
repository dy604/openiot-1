package org.openiot.scheduler.core.utils.lsmpa.entities;

/**
 *    Copyright (c) 2011-2014, OpenIoT
 *    
 *    This file is part of OpenIoT.
 *
 *    OpenIoT is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, version 3 of the License.
 *
 *    OpenIoT is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with OpenIoT.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     Contact: OpenIoT mailto: info@openiot.eu
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openiot.commons.util.PropertyManagement;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openiot.lsm.schema.LSMSchema;
import org.openiot.lsm.server.LSMTripleStore;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class Service {
	
	public static class Queries {
		public static ArrayList<Service> parseService(TupleQueryResult qres) {
			ArrayList<Service> serviceList = new ArrayList<Service>();
			try {
				while (qres.hasNext()) {
					BindingSet b = qres.next();
					Set names = b.getBindingNames();
					Service srvc = new Service();

					for (Object n : names) {
						if (((String) n).equalsIgnoreCase("serviceID")) {
							String str = (b.getValue((String) n) == null) ? null : b.getValue((String) n)
									.stringValue();
							srvc.setId(str);
							logger.debug("srvc id: " + srvc.getId() + " ");
						} else if (((String) n).equalsIgnoreCase("srvcName")) {
							String str = (b.getValue((String) n) == null) ? null : b.getValue((String) n)
									.stringValue();
							srvc.setName(str);
							logger.debug("srvcName : " + srvc.getName() + " ");
						} else if (((String) n).equalsIgnoreCase("srvcDesc")) {
							String str = (b.getValue((String) n) == null) ? null : b.getValue((String) n)
									.stringValue();
							srvc.setDescription(str);
							logger.debug("srvcDesc : " + srvc.getDescription() + " ");
						} else if (((String) n).equalsIgnoreCase("srvcQstring")) {
							String str = (b.getValue((String) n) == null) ? null : b.getValue((String) n)
									.stringValue();
							Query qString = new Query();
							qString.setqString(str);
							srvc.addQueryString(qString);
							logger.debug("srvcQstring : " + srvc.getQueryString() + " ");
						} else if (((String) n).equalsIgnoreCase("oamo")) {
							String str = (b.getValue((String) n) == null) ? null : b.getValue((String) n)
									.stringValue();
							OAMO oamo = new OAMO();
							oamo.setId(str);
							srvc.setOAMO(oamo);
							logger.debug("oamo : " + srvc.getOAMO().getId() + " ");
						}
					}
					serviceList.add(srvc);
				}// while
				return serviceList;
			} catch (QueryEvaluationException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}


		private static String getNamespaceDeclarations() {
			StringBuilder declarations = new StringBuilder();
			declarations.append("PREFIX : <" + "http://openiot.eu/ontology/ns/" + "> \n");
			// declarations.append("PREFIX spt: <" +
			// "http://spitfire-project.eu/ontology/ns/" + "> \n");
			declarations.append("PREFIX rdf: <" + RDF.getURI() + "> \n");// http://www.w3.org/1999/02/22-rdf-syntax-ns#
			declarations.append("PREFIX rdfs: <" + RDFS.getURI() + "> \n");// http://www.w3.org/2000/01/rdf-schema#
			declarations.append("PREFIX xsd: <" + XSD.getURI() + "> \n");
			// declarations.append("PREFIX owl: <" + OWL.getURI() + "> \n");
			// declarations.append("PREFIX ssn: <" +
			// "http://purl.oclc.org/NET/ssnx/ssn#" + "> \n");
			// declarations.append("PREFIX dul: <" +
			// "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#" +
			// "> \n");
			// declarations.append("PREFIX oiot: <" +
			// "http://openiot.eu/ontology/ns/" + "> \n");
			// declarations.append("base oiot: <" +
			// "http://openiot.eu/ontology/ns/clouddb" + "> \n");
			declarations.append("\n");

			return declarations.toString();
		}

		// public static String selectAllServices()
		// {
		// StringBuilder update = new StringBuilder();
		// update.append(getNamespaceDeclarations());
		//
		// String str=("SELECT ?serviceID from <"+graph+"> "
		// +"WHERE "
		// +"{"
		//
		// +"?serviceID rdf:type <http://openiot.eu/ontology/ns/Service> . "
		// +"}");
		//
		// update.append(str);
		// return update.toString();
		// }
		// public static String selectServiceByName(String srvcName)
		// {
		// StringBuilder update = new StringBuilder();
		// update.append(getNamespaceDeclarations());
		//
		// String str=("SELECT ?serviceID from <"+graph+"> "
		// +"WHERE "
		// +"{"
		// +"?serviceID <http://openiot.eu/ontology/ns/serviceName> ?name FILTER regex(?name, \""
		// +srvcName+ "\" )  . "
		// +"}");
		//
		// update.append(str);
		// return update.toString();
		// }
		// public static String selectServiceByDescription(String desc)
		// {
		// StringBuilder update = new StringBuilder();
		// update.append(getNamespaceDeclarations());
		//
		// String str=("SELECT ?serviceID from <"+graph+"> "
		// +"WHERE "
		// +"{"
		// +"?serviceID <http://openiot.eu/ontology/ns/serviceDescription> ?desc FILTER regex(?desc, \""
		// +desc+ "\" )  . "
		// +"}");
		//
		// update.append(str);
		// return update.toString();
		// }
		// public static String selectSrvcByQString(String qString)
		// {
		// StringBuilder update = new StringBuilder();
		// update.append(getNamespaceDeclarations());
		//
		// String str=("SELECT ?serviceID from <"+graph+"> "
		// +"WHERE "
		// +"{"
		// +"?serviceID <http://openiot.eu/ontology/ns/queryString> ?email FILTER regex(?email, \""
		// +qString+ "\" )  . "
		// +"}");
		//
		// update.append(str);
		// return update.toString();
		// }
		public static String selectSrvcByUser(String graph,OAMO oamo) {
			StringBuilder update = new StringBuilder();
			update.append(getNamespaceDeclarations());

			String str = ("SELECT ?serviceID ?srvcName ?srvcDesc ?srvcQstring ?oamo from <" + graph + "> "
					+ "WHERE " + "{" + "?serviceID <http://openiot.eu/ontology/ns/oamo> ?oamo . "
					+ "?serviceID <http://openiot.eu/ontology/ns/serviceName> ?srvcName . "
					+ "?serviceID <http://openiot.eu/ontology/ns/serviceDescription> ?srvcDesc . "
					+ "?serviceID <http://openiot.eu/ontology/ns/query> ?srvcQstring . "
					+ "?serviceID <http://openiot.eu/ontology/ns/oamo> <" + oamo.getId() + "> . " + "}");

			update.append(str);
			return update.toString();
		}
		// public static String selectSrvcBySrvcStatus(ArrayList<ServiceStatus>
		// serviceStatusList)
		// {
		// StringBuilder update = new StringBuilder();
		// update.append(getNamespaceDeclarations());
		//
		// update.append("SELECT ?serviceID from <"+graph+"> "
		// +"WHERE "
		// +"{");
		// for(int i=0; i<serviceStatusList.size(); i++)
		// {
		// update.append("?serviceID <http://openiot.eu/ontology/ns/serviceStatus> <"+serviceStatusList.get(i).getId()+"> )  . ");
		// }
		// update.append("}");
		//
		// return update.toString();
		// }
	}// class

	final static Logger logger = LoggerFactory.getLogger(Service.class);
	
	private LSMSchema myOnt;
	private LSMSchema ontInstance;
	private String graph;
	private LSMTripleStore lsmStore;

	private Individual serviceClassIdv;

	private OntClass ontClsServiceClass;
	private OntProperty ontPserviceName;
	private OntProperty ontPserviceDescription;
	// private OntProperty lExecuted;
	private OntProperty ontPquery;
	private OntProperty ontPserviceStatus;
	private OntProperty ontPoamo;
	private OntProperty ontPwidgePres;

	private String id;
	private String name;
	private String description;
	// private String lastExecuted;
	private List<Query> queryString = new ArrayList<Query>();
	private List<ServiceStatus> serviceStatusList = new ArrayList<ServiceStatus>();
	private OAMO oamo;
	private List<WidgetPresentation> widgetPresList = new ArrayList<WidgetPresentation>();

	public Service() {
	}

	public Service(LSMSchema myOnt, LSMSchema ontInstance, String graph, LSMTripleStore lsmStore) {
		
		this.myOnt = myOnt;
		this.ontInstance = ontInstance;
		this.graph = graph;
		this.lsmStore = lsmStore;

		initOnt_Service();
		// createClassIdv();
	}

	public Service(String classIdvURL, LSMSchema myOnt, LSMSchema ontInstance, String graph,
			LSMTripleStore lsmStore) {
		
		this.myOnt = myOnt;
		this.ontInstance = ontInstance;
		this.graph = graph;
		this.lsmStore = lsmStore;

		this.id = classIdvURL;

		initOnt_Service();
		// createClassIdv();
	}

	private void initOnt_Service() {
		ontClsServiceClass = myOnt.getClass("http://openiot.eu/ontology/ns/Service");
		ontPserviceName = myOnt.createProperty("http://openiot.eu/ontology/ns/serviceName");
		ontPserviceDescription = myOnt.createProperty("http://openiot.eu/ontology/ns/serviceDescription");
		// lExecuted =
		// myOnt.getProperty("http://openiot.eu/ontology/ns/lastExecuted");
		ontPquery = myOnt.createProperty("http://openiot.eu/ontology/ns/query");
		ontPserviceStatus = myOnt.getProperty("http://openiot.eu/ontology/ns/serviceStatus");
		ontPoamo = myOnt.createProperty("http://openiot.eu/ontology/ns/oamo");
		ontPwidgePres = myOnt.createProperty("http://openiot.eu/ontology/ns/widgetPres");
	}

	public void createClassIdv() {
		if (id == null)
			serviceClassIdv = ontInstance.createIndividual(ontClsServiceClass);
		else
			serviceClassIdv = ontInstance.createIndividual(id, ontClsServiceClass);
	}

	public void createPserviceName() {
		if (name != null)
			serviceClassIdv.setPropertyValue(ontPserviceName, ontInstance.getBase().createTypedLiteral(name));
	}

	public void createPserviceDescription() {
		if (description != null)
			serviceClassIdv.setPropertyValue(ontPserviceDescription, ontInstance.getBase()
					.createTypedLiteral(description));
	}

	public void createPqString() {
		for (int i = 0; i < queryString.size(); i++) {
			// serviceClassIdv.setPropertyValue(ontPqString,
			// ontInstance.getBase().createTypedLiteral(queryString));
			serviceClassIdv.addProperty(ontPquery, queryString.get(i).getClassIndividual());
		}
	}

	public void createPOAMO() {
		if (oamo != null)
			serviceClassIdv.addProperty(ontPoamo, oamo.getClassIndividual());
	}

	public void createPserviceStatus() {
		for (int i = 0; i < serviceStatusList.size(); i++) {
			serviceClassIdv.addProperty(ontPserviceStatus, serviceStatusList.get(i).getClassIndividual());
		}
	}

	public void createPwidgetPres() {
		for (int i = 0; i < widgetPresList.size(); i++) {
			serviceClassIdv.addProperty(ontPwidgePres, widgetPresList.get(i).getClassIndividual());
		}
	}

	public void createOnt_Service() {
		createClassIdv();
		createPserviceName();
		createPserviceDescription();
		// serviceClassIdv.setPropertyValue(lExecuted,
		// ontInstance.getBase().createTypedLiteral(lastExecuted));
		createPqString();
		createPOAMO();
		createPserviceStatus();
		createPwidgetPres();
	}

	// //

	public LSMSchema getOnt() {
		return myOnt;
	}

	public LSMSchema getOntInstance() {
		return ontInstance;
	}

	public Individual getClassIndividual() {
		return serviceClassIdv;
	}

	// //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// public String getLastExecuted()
	// {
	// return lastExecuted;
	// }
	// public void setLastExecuted(String lastExecuted)
	// {
	// this.lastExecuted = lastExecuted;
	// }

	public List<Query> getQueryString() {
		return queryString;
	}

	public void setQueryString(List<Query> queryString) {
		this.queryString = queryString;
	}

	public void addQueryString(Query queryString) {
		this.queryString.add(queryString);
	}

	public List<ServiceStatus> getServiceStatusList() {
		return serviceStatusList;
	}

	public void setServiceStatus(List<ServiceStatus> serviceStatusList) {
		this.serviceStatusList = serviceStatusList;
	}

	public void addServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatusList.add(serviceStatus);
	}

	public OAMO getOAMO() {
		return oamo;
	}

	public void setOAMO(OAMO oamo) {
		this.oamo = oamo;
	}

	public List<WidgetPresentation> getWidgetPresList() {
		return widgetPresList;
	}

	public void setWidgetPresList(List<WidgetPresentation> widgetPresList) {
		this.widgetPresList = widgetPresList;
	}

	public void addWidgetPresentation(WidgetPresentation widgetPres) {
		this.widgetPresList.add(widgetPres);
	}
}// class
