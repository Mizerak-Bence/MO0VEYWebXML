<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html" encoding="UTF-8" indent="yes"/>

  <xsl:template match="/">
    <html>
      <head>
        <title>Hallgatok adatai - for-each, value-of</title>
        <meta charset="UTF-8"/>
        <style>
          table { border-collapse: collapse; }
          th, td { border: 1px solid #000; padding: 4px; }
          th { background-color: #c0e060; }
        </style>
      </head>
      <body>
        <h2>Hallgatok adatai - for-each, value-of</h2>
        <table>
          <tr>
            <th>ID</th>
            <th>Vezeteknev</th>
            <th>Keresztnev</th>
            <th>Becenev</th>
            <th>Kor</th>
            <th>Fizetes</th>
          </tr>

          <xsl:for-each select="class/student">
            <tr>
              <td><xsl:value-of select="@id"/></td>
              <td><xsl:value-of select="vezeteknev"/></td>
              <td><xsl:value-of select="keresztnev"/></td>
              <td><xsl:value-of select="becenev"/></td>
              <td><xsl:value-of select="kor"/></td>
              <td><xsl:value-of select="osztondij"/></td>
            </tr>
          </xsl:for-each>

        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
