package pt.gov.dgarq.roda.plugins.converters.video;

import org.apache.log4j.Logger;

import pt.gov.dgarq.roda.core.data.RepresentationObject;
import pt.gov.dgarq.roda.core.plugins.Plugin;
import pt.gov.dgarq.roda.plugins.converters.common.AbstractRodaMigratorPlugin;

/**
 * @author Rui Castro
 */
public class Avi2Mpeg2ConverterPlugin extends AbstractRodaMigratorPlugin {
	private static Logger logger = Logger
			.getLogger(Avi2Mpeg2ConverterPlugin.class);

	private final String name = "Converter/AVI to MPEG2";
	private final float version = 1.0f;
	private final String description = "Video AVI representations normalizer plugin. "
			+ "Convert Video representations from AVI to MPEG2 using RODA Migrator service Video2DVD.";

	/**
	 * 
	 * @see Plugin#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @see Plugin#getVersion()
	 */
	public float getVersion() {
		return version;
	}

	/**
	 * @see Plugin#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	@Override
	protected String getConfigurationFile() {
		return "avi2mpeg2-converter.properties";
	}

	/**
	 * 
	 * @see AbstractRodaMigratorPlugin#getRepresentationType ()
	 */
	@Override
	protected String getRepresentationType() {
		return RepresentationObject.VIDEO;
	}

	/**
	 * 
	 * @see AbstractRodaMigratorPlugin#isNormalization()
	 */
	@Override
	protected boolean isNormalization() {
		return true;
	}

}
