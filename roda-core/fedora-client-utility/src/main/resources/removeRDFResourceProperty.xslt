<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:roda="http://roda.dgarq.gov.pt/#"
	xmlns:roda-log="http://roda.dgarq.gov.pt/log#">

	<xsl:output encoding="UTF-8" method="xml" indent="yes" omit-xml-declaration="yes"/>

	<xsl:param name="predicate"/>
	<xsl:param name="object"/>

	<xsl:template match="rdf:RDF">

		<!-- If property doesn't exist, simply copy everything to output -->
		<xsl:if test="not(rdf:Description/*[name()=$predicate and @rdf:resource=$object])">
			<xsl:copy-of select="."/>
		</xsl:if>

		<!-- If property exists, copy everything except the property to remove -->
		<xsl:if test="rdf:Description/*[name()=$predicate and @rdf:resource=$object]">
			<rdf:RDF>

				<!-- Copy existing attributes -->
				<xsl:copy-of select="@*"/>

				<rdf:Description>

					<!-- Copy existing attributes -->
					<xsl:copy-of select="rdf:Description/@*"/>

					<!-- Copy all the other elements as they are -->
					<xsl:for-each
						select="rdf:Description/*[not(name()=$predicate and @rdf:resource=$object)]">
						<!-- Copy the element -->
						<xsl:copy-of select="."/>
					</xsl:for-each>

				</rdf:Description>
			</rdf:RDF>
		</xsl:if>

	</xsl:template>

</xsl:stylesheet>
