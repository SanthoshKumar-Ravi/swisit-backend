package com.stackroute.swisit.documentparser.domain;

/*---------------Importing Libraries--------------*/
import java.util.HashMap;

/*--- Document Model Domain class has properties that are stored in mongo for retrieval when required --*/
public class DocumentModel {

    /*-------------Private variable of domain class------------*/

   private HashMap<String, HashMap<String, Integer>> webDocumentModel;

   /*-----------Default Constructor of Crawler Result Class------------*/
   public DocumentModel() { }

   /*----------Parameterized Constructor of Crawler Result Class---------*/
   public DocumentModel( HashMap<String, HashMap<String, Integer>> webDocumentModel ) {
       this.webDocumentModel = webDocumentModel;
   }

    /*------------- Getters and setters for fields -----------*/

   public HashMap<String, HashMap<String, Integer>> getWebDocumentModel() {
       return webDocumentModel;
   }

   public void setWebDocumentModel( HashMap<String, HashMap<String, Integer>> webDocumentModel ) {
       this.webDocumentModel = webDocumentModel;
   }
}