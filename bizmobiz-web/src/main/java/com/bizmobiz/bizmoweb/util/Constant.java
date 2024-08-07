package com.bizmobiz.bizmoweb.util;

public final class Constant {

	public static final String EMPTY_STRING = "";
	public static final String SPACE = " ";
	public static final String TAB = "\t";
	public static final String SINGLE_QUOTE = "'";
	public static final String PERIOD = ".";
	public static final String DOUBLE_QUOTE = "\"";
	
	public static final String GOOGLE_GEO_API_KEY = "AIzaSyDiyYEoR2-hBSt5PjogADITuzCtG_bA07I";

	public static final Integer GEO_TRACKER_TYPE_REGISTER = 1;
	public static final Integer GEO_TRACKER_TYPE_TRACKING = 2;
	public static final Integer GEO_TRACKER_TYPE_LOGIN = 3;
	
	public static final Integer LEVEL_SERVICE_FIRST_LEVEL = 0;
	
	public static final Boolean COUNTRY_IS_ENABLED = Boolean.TRUE;
	public static final Boolean COUNTRY_IS_DISABLED = Boolean.FALSE;
	
	public static final Integer USER_RRSSUID_GOOGLE = 0;
	public static final Integer USER_RRSSUID_FACEBOOK = 1;
	
	public static final String SETUP_KEY_SEARCH_RADIUS = "search_radius";
	public static final String SETUP_KEY_GEO_FRECUENCY = "geo_frecuency";
	public static final String SETUP_KEY_OFFER_WORK_IMAGE = "offer_work_image";
	public static final int SETUP_KEY_OFFER_WORK_IMAGE_DEFAULT = 4;
	
	public static final Integer OFFER_WORK_INMEDIATA = 1;
	public static final Integer OFFER_WORK_PROGRAMADA = 2;
	public static final Integer OFFER_WORK_PROPOSAL_TIMEOUT = 10;
	
	public static final Integer OFFER_WORK_STATE_SOLICITADO = 1;
	public static final Integer OFFER_WORK_STATE_EN_PROPUESTA = 2;
	public static final Integer OFFER_WORK_STATE_EN_CONTACTO = 3;
	public static final Integer OFFER_WORK_STATE_ACEPTADO = 4;
	public static final Integer OFFER_WORK_STATE_CANCELADO = 5;
	public static final Integer OFFER_WORK_STATE_FINALIZADO = 6;
	
	public static final Integer PROPOSAL_COST_TYPE_UNICO = 0;
	public static final Integer PROPOSAL_COST_TYPE_RANGO = 1;
	
	public static final Integer PROPOSAL_STATE_ENVIADA = 1;
	public static final Integer PROPOSAL_STATE_CANCELADA = 2;

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Constant.EMPTY_STRING</tt>, and
	 * so on. Thus, the caller should be prevented from constructing objects of this
	 * class, by declaring this private constructor.
	 */
	private Constant() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
