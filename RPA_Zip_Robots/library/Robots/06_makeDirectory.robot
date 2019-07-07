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
            <property name="attributeValue" class="String">New Folder/Sub Folder</property>
            <property name="currentlyAssigned" class="Boolean">true</property>
            <property name="lastKnownAttributeType" idref="0"/>
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
            <property name="expression" class="String">host + "/webzip/ref/makeDirectory.html"</property>
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
        <property name="name" class="String">PUT /webzip/rest/archive/backup.zip/content</property>
        <property name="stepAction" class="CallRESTWebService2" serializationversion="1">
          <property name="urlProvider" class="Expression" serializationversion="1">
            <property name="text" class="String">host + "/webzip/rest/archive/" + input.archive + "/content?path=" + urlEncode(input.content)</property>
          </property>
          <property name="request" class="kapow.robot.plugin.common.stateprocessor.rest.PutRestRequest"/>
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
