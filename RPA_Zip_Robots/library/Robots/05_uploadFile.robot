<?xml version="1.0" encoding="UTF-8" ?>
<object class="Robot" serializationversion="2">
  <prologue>
    <saved-by-versions>
      <version>10.4.0.0</version>
    </saved-by-versions>
    <referenced-types>
      <type name="param"/>
    </referenced-types>
    <referenced-snippets>
      <snippet name="json_status"/>
      <snippet name="config"/>
    </referenced-snippets>
    <triggers/>
    <typed-variables>
      <typed-variable name="input" type-name="param"/>
    </typed-variables>
    <global-variables/>
    <parameters>
      <parameter name="input" type-name="param"/>
    </parameters>
    <return-variables/>
    <store-in-database-variables/>
    <device-mappings/>
    <browser-engine>WEBKIT</browser-engine>
  </prologue>
  <property name="variables" class="Variables">
    <object class="Variable" serializationversion="1">
      <property name="name" class="String">input</property>
      <property name="parameter" class="Boolean">true</property>
      <property name="initialAssignment" class="InitialVariableAssignment">
        <property name="type" class="TypeReference" serializationversion="0">
          <property name="typeName" class="String">param</property>
        </property>
        <property name="assignments" class="AttributeAssignments">
          <property name="archive" class="AttributeAssignment">
            <property name="attributeValue" class="String">backup.zip</property>
            <property name="currentlyAssigned" class="Boolean">true</property>
            <property name="lastKnownAttributeType" class="java.lang.Class" id="0">kapow.robot.plugin.common.domain.StringAttributeType</property>
          </property>
          <property name="content" class="AttributeAssignment">
            <property name="attributeValue" class="String">new.json</property>
            <property name="currentlyAssigned" class="Boolean">true</property>
            <property name="lastKnownAttributeType" idref="0"/>
          </property>
          <property name="file" class="AttributeAssignment">
            <property name="attributeValue" class="String">7B 0D 0A 20 20 20 20 22 6F 72 64 65 72 73 22 20 3A 20 5B 0D 0A 20 20 20 20 20 20 20 20 7B 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 22 73 74 61 74 75 73 22 20 3A 20 22 6E 65 77 22 2C 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 22 64 61 74 65 22 20 3A 20 22 2D 31 22 2C 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 22 66 72 6F 6D 6E 61 6D 65 22 20 3A 20 22 54 31 20 45 6E 74 65 72 70 72 69 73 65 73 22 2C 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 22 66 72 6F 6D 65 6D 61 69 6C 22 20 3A 20 22 70 75 72 63 68 61 73 69 6E 67 40 74 31 65 6E 74 65 72 70 72 69 73 65 73 2E 63 6F 6D 22 2C 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 22 73 75 62 6A 65 63 74 22 20 3A 20 22 50 4F 20 4E 6F 74 69 66 69 63 61 74 69 6F 6E 20 2D 20 50 4F 2D 30 31 32 37 37 22 2C 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 22 6D 65 73 73 61 67 65 22 20 3A 20 22 3C 70 3E 3C 69 6D 67 20 73 72 63 3D 5C 22 69 6D 67 2F 74 31 6C 6F 67 6F 2E 70 6E 67 5C 22 20 2F 3E 3C 2F 70 3E 3C 68 32 3E 48 65 6C 6C 6F 21 3C 2F 68 32 3E 3C 70 3E 54 31 20 45 6E 74 65 72 70 72 69 73 65 73 20 68 61 73 20 69 73 73 75 65 64 20 61 20 4E 45 57 20 50 75 72 63 68 61 73 65 20 4F 72 64 65 72 21 3C 2F 70 3E 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 3C 70 3E 50 4F 20 52 65 66 65 72 65 6E 63 65 3A 20 50 4F 2D 30 31 32 37 37 3C 2F 70 3E 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 3C 70 3E 50 6C 65 61 73 65 20 6C 6F 67 69 6E 20 74 6F 20 74 68 65 20 3C 61 20 68 72 65 66 3D 5C 22 2F 54 31 56 65 6E 64 6F 72 50 6F 72 74 61 6C 5C 22 20 74 61 72 67 65 74 3D 5C 22 5F 62 6C 61 6E 6B 5C 22 3E 54 31 20 45 6E 74 65 72 70 72 69 73 65 73 20 56 65 6E 64 6F 72 20 50 6F 72 74 61 6C 3C 2F 61 3E 20 74 6F 20 72 65 74 72 65 69 76 65 20 74 68 65 20 6F 72 64 65 72 20 64 65 74 61 69 6C 73 20 61 6E 64 20 61 63 6B 6E 6F 77 6C 65 64 67 65 20 61 63 63 65 70 74 61 6E 63 65 20 6F 66 20 74 68 65 20 6F 72 64 65 72 2E 3C 2F 70 3E 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 3C 70 3E 50 6C 65 61 73 65 20 75 73 65 20 74 68 65 20 56 65 6E 64 6F 72 20 50 6F 72 74 61 6C 20 74 6F 20 6B 65 65 70 20 75 73 20 75 70 20 74 6F 20 64 61 74 65 20 77 69 74 68 20 74 68 65 20 70 72 6F 67 72 65 73 73 20 6F 66 20 74 68 69 73 20 6F 72 64 65 72 2E 3C 2F 70 3E 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 3C 70 3E 46 6F 72 20 56 65 6E 64 6F 72 20 50 6F 72 74 61 6C 20 75 73 61 67 65 20 71 75 65 72 69 65 73 2C 20 70 6C 65 61 73 65 20 65 2D 6D 61 69 6C 3A 20 70 75 72 63 68 61 73 69 6E 67 40 74 31 65 6E 74 65 72 70 72 69 73 65 73 2E 63 6F 6D 3C 2F 70 3E 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 3C 73 70 61 6E 3E 54 68 61 6E 6B 73 20 61 6E 64 20 52 65 67 61 72 64 73 3C 2F 73 70 61 6E 3E 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 3C 73 70 61 6E 3E 54 31 20 45 6E 74 65 72 70 72 69 73 65 73 20 50 75 72 63 61 68 73 69 6E 67 20 44 65 70 61 72 74 6D 65 6E 74 3C 2F 73 70 61 6E 3E 22 0D 0A 20 20 20 20 20 20 20 20 20 20 20 20 0D 0A 20 20 20 20 20 20 20 20 7D 0D 0A 20 20 20 20 5D 0D 0A 7D</property>
            <property name="currentlyAssigned" class="Boolean">true</property>
            <property name="lastKnownAttributeType" class="java.lang.Class">kapow.robot.plugin.common.domain.BinaryAttributeType</property>
          </property>
        </property>
      </property>
    </object>
  </property>
  <property name="proxyServerConfiguration" class="ProxyServerConfiguration" serializationversion="0"/>
  <property name="httpClientType" class="HttpClientType">
    <property name="enum-name" class="String">WEBKIT</property>
  </property>
  <property name="ntlmAuthentication" class="NTLMAuthenticationType">
    <property name="enum-name" class="String">STANDARD</property>
  </property>
  <property name="usePre96DefaultWaiting" class="Boolean" id="1">false</property>
  <property name="maxWaitForTimeout" class="Integer">10000</property>
  <property name="waitRealTime" idref="1"/>
  <property name="privateHTTPCacheEnabled" class="Boolean" id="2">true</property>
  <property name="privateHTTPCacheSize" class="Integer">2048</property>
  <property name="comment">
    <null/>
  </property>
  <property name="executionMode" class="ExecutionMode">
    <property name="enum-name" class="String">FULL</property>
  </property>
  <property name="avoidExternalReExecution" idref="1"/>
  <property name="transitionGraph" class="Body">
    <blockBeginStep class="BlockBeginStep" id="3"/>
    <steps class="ArrayList">
      <object class="SnippetStep" id="4">
        <name>
          <null/>
        </name>
        <snippetName class="String">config</snippetName>
        <snippetStepComment>
          <null/>
        </snippetStepComment>
      </object>
      <object class="Transition" serializationversion="3" id="5">
        <property name="name" class="String">Load Page</property>
        <property name="stepAction" class="LoadPage2">
          <property name="urlProvider" class="kapow.robot.plugin.common.stepaction.urlprovider2.ExpressionURLProvider2">
            <property name="expression" class="String">host + "/webzip/ref/uploadFile.html"</property>
          </property>
          <property name="browserConfigurationSpecification" class="BrowserConfigurationSpecificationWebKit" serializationversion="25">
            <property name="ancestorProvider" class="BrowserConfigurationSpecificationAncestorProviderForStep"/>
          </property>
        </property>
        <property name="elementFinders" class="ElementFinders"/>
        <property name="errorHandler" class="ErrorHandler" serializationversion="0"/>
        <property name="comment">
          <null/>
        </property>
        <property name="enabled" idref="2"/>
        <property name="changedProperties" class="java.util.HashSet"/>
      </object>
      <object class="Transition" serializationversion="3" id="6">
        <property name="name" class="String">CLICK HERE FIRST</property>
        <property name="stepAction" class="DoNothing"/>
        <property name="elementFinders" class="ElementFinders"/>
        <property name="errorHandler" class="ErrorHandler" serializationversion="0"/>
        <property name="comment" class="String">To show the help text inside Kapow browser</property>
        <property name="enabled" idref="2"/>
        <property name="changedProperties" class="java.util.HashSet">
          <element class="String">name</element>
        </property>
      </object>
      <object class="Transition" serializationversion="3" id="7">
        <property name="name" class="String">POST /webzip/rest/archive/backup.zip/content</property>
        <property name="stepAction" class="CallRESTWebService2" serializationversion="1">
          <property name="urlProvider" class="Expression" serializationversion="1">
            <property name="text" class="String">host + "/webzip/rest/archive/" + input.archive + "/content?file=" + urlEncode(input.content)</property>
          </property>
          <property name="request" class="kapow.robot.plugin.common.stateprocessor.rest.PostRestRequest">
            <property name="body" class="kapow.robot.plugin.common.stateprocessor.rest.RawBodyContent">
              <property name="data" class="kapow.robot.plugin.common.support.expression.multipletype.VariableExpression" serializationversion="2">
                <property name="variable" class="kapow.robot.plugin.common.support.AttributeName2">
                  <property name="name" class="String">input.file</property>
                </property>
              </property>
              <property name="contentType" class="kapow.robot.plugin.common.support.mimetype.MIMETypeValueStringExpression">
                <property name="value" class="String">application/octet-stream</property>
              </property>
            </property>
          </property>
          <property name="accept" class="kapow.robot.plugin.common.support.mimetype.AnyMIMETypeValueStringExpression">
            <property name="value" class="String">application/json</property>
          </property>
          <property name="output" class="kapow.robot.plugin.common.stateprocessor.rest.ToBrowserStateOutputSpecification"/>
          <property name="browserConfigurationSpecification" class="BrowserConfigurationSpecificationWebKit" serializationversion="25">
            <property name="headerProvider" class="kapow.robot.plugin.common.support.browser2.requestheaders.ListHeaderProvider">
              <property name="headers" class="BeanList">
                <object class="kapow.robot.plugin.common.support.browser2.requestheaders.RequestHeader">
                  <property name="header" class="kapow.robot.plugin.common.support.expression.multipletype.VariableExpression" serializationversion="2">
                    <property name="variable" class="kapow.robot.plugin.common.support.AttributeName2">
                      <property name="name" class="String">apikey</property>
                    </property>
                  </property>
                </object>
              </property>
            </property>
            <property name="ignoreLoadErrors" class="Boolean">true</property>
            <property name="ancestorProvider" class="BrowserConfigurationSpecificationAncestorProviderForStep"/>
            <property name="changedProperties" class="java.util.HashSet">
              <element class="String">ignoreLoadErrors</element>
              <element class="String">headerProvider</element>
            </property>
          </property>
        </property>
        <property name="elementFinders" class="ElementFinders"/>
        <property name="errorHandler" class="ErrorHandler" serializationversion="0"/>
        <property name="comment">
          <null/>
        </property>
        <property name="enabled" idref="2"/>
        <property name="changedProperties" class="java.util.HashSet">
          <element class="String">name</element>
        </property>
      </object>
      <object class="Transition" serializationversion="3" id="8">
        <property name="name" class="String">View as JSON</property>
        <property name="stepAction" class="ViewAsJSON"/>
        <property name="elementFinders" class="ElementFinders"/>
        <property name="errorHandler" class="ErrorHandler" serializationversion="0"/>
        <property name="comment">
          <null/>
        </property>
        <property name="enabled" idref="2"/>
        <property name="changedProperties" class="java.util.HashSet"/>
      </object>
      <object class="SnippetStep" id="9">
        <name>
          <null/>
        </name>
        <snippetName class="String">json_status</snippetName>
        <snippetStepComment>
          <null/>
        </snippetStepComment>
      </object>
      <object class="End" id="10"/>
    </steps>
    <blockEndStep class="BlockEndStep"/>
    <edges class="ArrayList">
      <object class="TransitionEdge">
        <from idref="3"/>
        <to idref="4"/>
      </object>
      <object class="TransitionEdge">
        <from idref="4"/>
        <to idref="5"/>
      </object>
      <object class="TransitionEdge">
        <from idref="5"/>
        <to idref="6"/>
      </object>
      <object class="TransitionEdge">
        <from idref="6"/>
        <to idref="7"/>
      </object>
      <object class="TransitionEdge">
        <from idref="7"/>
        <to idref="8"/>
      </object>
      <object class="TransitionEdge">
        <from idref="8"/>
        <to idref="9"/>
      </object>
      <object class="TransitionEdge">
        <from idref="9"/>
        <to idref="10"/>
      </object>
    </edges>
  </property>
  <property name="browserConfigurationSpecification" class="BrowserConfigurationSpecificationWebKit" serializationversion="25"/>
</object>
