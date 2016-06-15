package br.com.sgc.dao.generico.util;

public enum FilterOperator {
	
	EQ("=",1, false),
	NE("!=",1, false),
	GT(">",1, false),
	LT("<",1, false),
	GE(">=",1, false),
	LE("<=",1, false),
	IS_NOT_NULL("is not null",0, false),
	IS_NULL("is null",0, false),
	BETWEEN("between",2, false),
	LIKE("like",1, false),
	IN("in",1, true),
	NOT_IN("not in",1, true),
	AND("and",1, false),
	OR("or",1, false),
	NOT("not",1, false),
	PAR_IN("(",0, false),
	PAR_OUT(")",0,false),
	TO_CHAR("TO_CHAR(",0,false),
	TO_CHAR_DATE_OUT(",'DD/MM/YYYY')",0,false);
	
	public static final String REMOVE_ACENTOS_1 = "        REPLACE( \n" +
	"          REPLACE( \n" +
	"            REPLACE( \n" +
	"              REPLACE( \n" +
	"                REPLACE( \n" +
	"                  REPLACE( \n" +
	"                    REPLACE( \n" +
	"                      REPLACE( \n" +
	"                        REPLACE( \n" +
	"                          REPLACE( \n" +
	"                          REPLACE( \n" +
	"                            REPLACE( \n";
	
	 public static final String REMOVE_ACENTOS_2 = ",'Õ','O') \n" +
     "                          ,'Â','A') \n" +
     "                          ,'Ü','U') \n" +
     "                        ,'Ô','O') \n" +
     "                      ,'Ê','E') \n" +
     "                    ,'Ã','A') \n" +
     "                  ,'Ç','C') \n" +
     "                ,'É','E') \n" +
     "              ,'Í','I') \n" +
     "            ,'Ú','U') \n" +
     "          ,'Ó','O') \n" +
     "        ,'Á','A') \n";


	
	String stringValue;
	int numParams;
	boolean useParenthesis;
	
	FilterOperator(String stringValue, int numArgs, boolean useParenthesis) {
		this.stringValue = stringValue;
		this.numParams = numArgs;
		this.useParenthesis = useParenthesis;
	}

	public String getStringValue() {
		return stringValue;
	}

	public int getNumParams() {
		return numParams;
	}

	public boolean isUseParenthesis() {
		return useParenthesis;
	}

}

