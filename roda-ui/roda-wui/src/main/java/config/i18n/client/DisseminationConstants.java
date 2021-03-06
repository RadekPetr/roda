/**
 * 
 */
package config.i18n.client;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * @author Luis Faria
 * 
 */
public interface DisseminationConstants extends Constants, ConstantsWithLookup {
	
	// Description Metadata Panel
	@DefaultStringValue("The requested object does not exist in the repository")
	public String objectNotFoundAlert();

	// Element levels
	@DefaultStringValue("fonds")
	public String fonds();
	@DefaultStringValue("subfonds")
	public String subfonds();
	@DefaultStringValue("class")
	public String class_();
	@DefaultStringValue("subclass")
	public String subclass();
	@DefaultStringValue("series")
	public String series();
	@DefaultStringValue("subseries")
	public String subseries();
	@DefaultStringValue("file")
	public String file();
	@DefaultStringValue("item")
	public String item();
	@DefaultStringValue("all levels")
	public String allLevels();
	@DefaultStringValue("choose levels")
	public String chooseLevels();
	
	// Description Metadata Panel

	// * Identification
	@DefaultStringValue("Identification")
	public String identificationGroupLabel();
	@DefaultStringValue("Reference")
	public String reference();
	@DefaultStringValue("Complete reference")
	public String completeReference();
	@DefaultStringValue("Handle")
	public String handle();
	@DefaultStringValue("Title")
	public String title();
	@DefaultStringValue("Level")
	public String level();
	@DefaultStringValue("Initial date")
	public String dateInitial();
	@DefaultStringValue("Final date")
	public String dateFinal();
	@DefaultStringValue("Range dates")
	public String dateRange();
	@DefaultStringValue("Country code")
	public String countryCode();
	@DefaultStringValue("Repository code")
	public String repositoryCode();
	@DefaultStringValue("Origination")
	public String origination();
	@DefaultStringValue("Acquisition number")
	public String acqInfoNum();
	@DefaultStringValue("Acquisition date")
	public String acqInfoDate();
	@DefaultStringValue("Material specification")
	public String materialspec();
	@DefaultStringValue("Physical description")
	public String physDesc();
	@DefaultStringValue("Date of initial physical description")
	public String physDescDateInitial();
	@DefaultStringValue("Date of final physical description")
	public String physDescDateFinal();
	@DefaultStringValue("Dimensions")
	public String physDescDimensions();
	@DefaultStringValue("Facet or appearance")
	public String physDescPhysFacet();
	@DefaultStringValue("Extent")
	public String physDescExtent();
	@DefaultStringValue("Physical location")
	public String physLoc();
	@DefaultStringValue("Laguages")
	public String langMaterialLaguages();
	@DefaultStringValue("Quote")
	public String preferCite();

	// * Context
	@DefaultStringValue("Context")
	public String contextGroupLabel();
	@DefaultStringValue("Administrative and biographical history")
	public String bioghist();
	@DefaultStringValue("Chronological list of the administrative and biographical history")
	public String bioghistChronlist();
	@DefaultStringValue("Custodial history")
	public String custodhist();
	@DefaultStringValue("Acquisition information")
	public String acqinfo();

	// * Content
	@DefaultStringValue("Content and structure")
	public String contentGroupLabel();
	@DefaultStringValue("Scope and content")
	public String scopeContent();
	@DefaultStringValue("Organization and ordering")
	public String arrangement();
	@DefaultStringValue("Appraisal")
	public String appraisal();
	@DefaultStringValue("Accruals")
	public String accruals();

	// * Access
	@DefaultStringValue("Access and use restritions")
	public String accessGroupLabel();
	@DefaultStringValue("Physical characteristics and technical requirements")
	public String physTech();
	@DefaultStringValue("Access restritions")
	public String accessRestrict();
	@DefaultStringValue("Reproduction restritions")
	public String useRestrict();

	// * Related Materials
	@DefaultStringValue("Related Materials")
	public String relatedMaterialsGroupLabel();
	@DefaultStringValue("Other find aids")
	public String otherFindAid();

	// * Notes
	@DefaultStringValue("Notes")
	public String notesGroupLabel();
	@DefaultStringValue("Bibliography")
	public String bibliography();
	@DefaultStringValue("Note")
	public String note();
	
	// Languages codes
	@DefaultStringValue("Portuguese")
	public String lang_pt();
	@DefaultStringValue("English")
	public String lang_en();
	
	// Descriptive Metadata Edition
	@DefaultStringValue("The changes were successfully saved.")
	public String editSaveSuccess();




}
