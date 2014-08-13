<?xml version="1.0" encoding="UTF-8"?>
<!-- 
  The main entry point for all user interface generated
  from XSLT. 
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output omit-xml-declaration="yes" method="html" doctype-public="html" indent="yes"
        encoding="UTF-8"/>

    <xsl:include href="../../common/base-variables.xsl"/>

    <xsl:include href="../../base-layout-cssjs-loader.xsl"/>

    <xsl:template match="/">
        <xsl:variable name="env" select="/root/gui/env"/>
        <html ng-app="{$angularApp}" lang="{$lang}" id="ng-app">
            <head>
                <title>
                    <xsl:value-of select="concat($env/site/name, ' - ', $env/site/organization)"
                        />
                </title>
                <meta charset="utf-8"/>
                <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
                <meta name="apple-mobile-web-app-capable" content="yes"/>

                <meta name="description" content=""/>
                <meta name="keywords" content=""/>


                <link rel="icon" type="image/gif" href="../../images/logos/favicon.gif"/>
                <!--<link href="rss.search?sortBy=changeDate" rel="alternate" type="application/rss+xml"-->
                    <!--title="{{concat($env/system/site/name, ' - ', $env/system/site/organization)}}"/>-->
                <!--<link href="portal.opensearch" rel="search" type="application/opensearchdescription+xml"-->
                    <!--title="concat($env/system/site/name, ' - ', $env/system/site/organization)"/>-->

                <xsl:call-template name="css-load"/>
            </head>


            <!-- The GnCatController takes care of
            loading site information, check user login state
            and a facet search to get main site information.
            -->
            <body data-ng-controller="GnInspireController">
                <!-- AngularJS application -->
                <div data-ng-cloak="" class="ng-cloak">
                    <div data-ng-include="'{$uiResourcesPath}templates/editor/inspire/metadata.html'"></div>

                    <xsl:if test="$isJsEnabled">
                        <xsl:call-template name="javascript-load"/>
                    </xsl:if>
                </div>
                <xsl:if test="$isJsEnabled">
                    <xsl:call-template name="no-js-alert"/>
                </xsl:if>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="no-js-alert">
        <noscript>
            <div class="alert" data-ng-hide="">
                <strong>
                    <xsl:value-of select="$i18n/warning"/>
                </strong>
                <xsl:text> </xsl:text>
                <xsl:copy-of select="$i18n/nojs"/>
            </div>
        </noscript>
    </xsl:template>

</xsl:stylesheet>