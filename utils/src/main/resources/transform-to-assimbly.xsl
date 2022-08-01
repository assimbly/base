<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                
  <xsl:output indent="yes"/>                

  <xsl:template match="/" >
  
    <xsl:choose>
        <xsl:when test="integrations">
            <xsl:copy-of select="/"/>            
        </xsl:when>
        <xsl:when test="flows">
            <integrations>
                <integration>
                    <id>1</id>
                    <name>default</name>
                    <type>FULL</type>
                    <environmentName>PRODUCTION</environmentName>
                    <stage>PRODUCTION</stage>
                    <xsl:copy-of select="/"/>
                </integration>
            </integrations>
        </xsl:when>
        <xsl:when test="flow">
            <integrations>
                <integration>
                    <id>1</id>
                    <name>default</name>
                    <type>FULL</type>
                    <environmentName>PRODUCTION</environmentName>
                    <stage>PRODUCTION</stage>
                    <flows>
                        <xsl:copy-of select="/"/>
                    </flows>
                </integration>
            </integrations>
        </xsl:when>
        <xsl:when test="//*:route">

            <integrations>
                <integration>
                  <id>1</id>
                  <name>default</name>
                  <type>FULL</type>
                  <environmentName>PRODUCTION</environmentName>
                  <stage>PRODUCTION</stage>
                  <flows>
                    <flow>
                      <id>
                        <xsl:choose>
                            <xsl:when test="//*:camelContext/@id">
            			        <xsl:value-of select="//*:camelContext/@id"/>
                            </xsl:when>
                            <xsl:when test="//*:routes/@id">
        				        <xsl:value-of select="//*:routes/@id"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of  select="current-dateTime()"/>
                            </xsl:otherwise>
                        </xsl:choose>    
					  </id>
                      <name>
                        <xsl:choose>
                            <xsl:when test="//*:camelContext/@id">
    					        <xsl:value-of select="//*:camelContext/@id"/>
                            </xsl:when>
                            <xsl:when test="//*:routes/@id">
        				        <xsl:value-of select="//*:routes/@id"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of  select="current-dateTime()"/>
                            </xsl:otherwise>
                        </xsl:choose>
					            </name>
                      <type>esb</type>
                      <components>
                         <xsl:for-each-group select="//*:from|//*:to|//*:toD" group-by="substring-before(@uri, ':' )" >
                            <component>
                              <xsl:value-of select="substring-before(@uri, ':' )"/>
                            </component>
                          </xsl:for-each-group>
                     </components>  					  
                      <steps>
                        <xsl:for-each select="//*:route">
                            <step>
                              <id>
                                 <xsl:choose>
                                    <xsl:when test="@id">
                        		        <xsl:value-of select="@id"/>                              
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of  select="generate-id(.)"/>
                                    </xsl:otherwise>                            
                                </xsl:choose>                               </id>
                              <type>route</type>
                              <route_id>
                                 <xsl:choose>
                                    <xsl:when test="@id">
                    			        <xsl:value-of select="@id"/>                              
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of  select="generate-id(.)"/>
                                    </xsl:otherwise>                            
                                </xsl:choose>                                  
                              </route_id>
                            </step>
                        </xsl:for-each>
                        <xsl:for-each select="//*:camelContext/*:onException">
                            <step>
                                <id>
                                    <xsl:choose>
                                        <xsl:when test="//*:camelContext/@id">
                                            <xsl:value-of select="//*:camelContext/@id"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of  select="generate-id(.)"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </id>
                                <type>error</type>
                                <routeconfiguration_id>
                                    <xsl:choose>
                                        <xsl:when test="//*:camelContext/@id">
                                            <xsl:value-of select="//*:camelContext/@id"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of  select="generate-id(.)"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </routeconfiguration_id>
                            </step>
                        </xsl:for-each>
                      </steps>
                    </flow>
                  </flows>
                  <routes>                    
                        <xsl:for-each select="//*:route">
                            <route>
                                 <xsl:choose>
                                    <xsl:when test="@id">
                                        <xsl:attribute name="id" select="@id"/> 
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:attribute name="id" select="generate-id(.)"/> 
                                    </xsl:otherwise>                            
                                </xsl:choose>
                                <xsl:if test="//*:camelContext/*:onException">
                                    <xsl:attribute name="routeConfigurationId">
                                        <xsl:choose>
                                            <xsl:when test="@id">
                                                <xsl:attribute name="id" select="//*:camelContext/@id"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:attribute name="id" select="generate-id(.)"/> 
                                            </xsl:otherwise>                            
                                        </xsl:choose>
                                    </xsl:attribute> 
                                </xsl:if>
                                <xsl:copy-of select="./*" copy-namespaces="no"/>
                            </route>
                        </xsl:for-each>
                    </routes>                    
                  <routeConfigurations>
                    <xsl:if test="//*:camelContext/*:onException">
                        <routeConfiguration>
                            <xsl:attribute name="id">
                                <xsl:choose>
                                    <xsl:when test="//*:camelContext/@id">
                                        <xsl:attribute name="id" select="//*:camelContext/@id"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:attribute name="id" select="generate-id(.)"/> 
                                    </xsl:otherwise>                            
                                </xsl:choose>
                            </xsl:attribute>
                            <xsl:apply-templates select="//*:camelContext/*:onException" mode="copy-no-namespaces"/>
                            <xsl:apply-templates select="//*:camelContext/*:dataFormats" mode="copy-no-namespaces"/>
                        </routeConfiguration>                                                
                    </xsl:if>
                    </routeConfigurations>                    
                </integration>
              </integrations>        
         </xsl:when>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="*" mode="copy-no-namespaces">
    <xsl:element name="{local-name()}">
        <xsl:copy-of select="@*"/>
        <xsl:apply-templates select="node()" mode="copy-no-namespaces"/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="comment()| processing-instruction()" mode="copy-no-namespaces">
    <xsl:copy/>
  </xsl:template>

</xsl:stylesheet>