<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                
  <xsl:output indent="yes"/>                

  <xsl:template match="/" >
  
    <xsl:choose>
        <xsl:when test="dil">
            <xsl:copy-of select="/"/>            
        </xsl:when>
        <xsl:when test="integrations">
            <dil>
               <xsl:copy-of select="/"/>
            </dil>
        </xsl:when>
        <xsl:when test="integration">
            <dil>
                <integrations>
                     <xsl:copy-of select="/"/>
                </integrations>
            </dil>
        </xsl:when>
        <xsl:when test="flows">
            <dil>
                <integrations>
                    <integration>
                        <id>1</id>
                        <name>default</name>
                        <type>FULL</type>
                        <options>
                            <environmentName>PRODUCTION</environmentName>
                            <stage>PRODUCTION</stage>
                        </options>
                        <xsl:copy-of select="/"/>
                    </integration>
                </integrations>
            </dil>
        </xsl:when>
        <xsl:when test="flow">
            <dil>
                <integrations>
                    <integration>
                        <id>1</id>
                        <name>default</name>
                        <type>FULL</type>
                        <options>
                            <environmentName>PRODUCTION</environmentName>
                            <stage>PRODUCTION</stage>
                        </options>
                        <flows>
                            <xsl:copy-of select="/"/>
                        </flows>
                    </integration>
                </integrations>
            </dil>
        </xsl:when>
        <xsl:when test="//*:route">
            <dil>
            <integrations>
                <integration>
                  <id>1</id>
                  <name>default</name>
                  <type>FULL</type>
                  <options>
                      <environmentName>PRODUCTION</environmentName>
                      <stage>PRODUCTION</stage>
                  </options>
                  <flows>
                    <flow>
                      <id>
                        <xsl:choose>
                            <xsl:when test="//*:camelContext/@id">
            			        <xsl:value-of select="//*:camelContext/@id"/>
                            </xsl:when>
                            <xsl:when test="//*:routes/@id">
        				        <xsl:value-of select="//*:route/@id"/>
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
                      <dependencies>
                         <xsl:for-each-group select="//*:from|//*:to|//*:toD" group-by="substring-before(@uri, ':' )" >
                            <dependency>
                              <xsl:value-of select="substring-before(@uri, ':' )"/>
                            </dependency>
                          </xsl:for-each-group>
                     </dependencies>
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
                                </xsl:choose>
                              </id>
                              <type>route</type>
                              <blocks>
                                    <block>
                                        <options>
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
                                        </options>
                                    </block>
                                </blocks>
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
                                <blocks>
                                    <block>
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
                                    </block>
                                </blocks>
                            </step>
                        </xsl:for-each>
                      </steps>
                    </flow>
                  </flows>
                </integration>
              </integrations>
              <core>
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
              </core>
            </dil>
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