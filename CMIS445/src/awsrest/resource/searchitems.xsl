<?xml version="1.0" encoding="iso-8859-1"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:amazon="http://webservices.amazon.com/AWSECommerceService/2005-10-05">
	
	<xsl:strip-space elements="*"/>
	
	<xsl:output method="text"/>

   	<xsl:template match="/" >			      	      
          <xsl:for-each select="amazon:ItemSearchResponse/amazon:Items/amazon:Item/amazon:ItemAttributes" >               
                <xsl:text>
                </xsl:text> 
			    <xsl:copy-of select="amazon:Author" />	
               	<xsl:text>
               	</xsl:text> 
			    <xsl:copy-of select="amazon:Title" />																					
                <xsl:text>
                </xsl:text>                
          </xsl:for-each>													
	</xsl:template>	
</xsl:stylesheet>