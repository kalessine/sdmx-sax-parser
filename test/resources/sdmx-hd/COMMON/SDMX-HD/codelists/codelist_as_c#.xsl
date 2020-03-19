<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:message="http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message"
	xmlns:structure="http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure"
	>
	<xsl:output method="text" indent="yes"  />  
  <xsl:template match="/">
  /// <summary>
  /// This is a constants class which list all the COMMON Concepts
  /// </summary>
    public static partial class CommonCodeList
    {
    <xsl:for-each select="//message:CodeLists/structure:CodeList">
<xsl:sort select="@id" />     public const string <xsl:value-of select="translate(normalize-space(structure:Name[@xml:lang='en']),' ','')" /> = "<xsl:value-of select="@id" />";
</xsl:for-each>
  }
  </xsl:template>
</xsl:stylesheet>
