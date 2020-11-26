package io.neocdtv;


import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.bmw.tam.ccr.datasource.SdwhDataSource;

@Path("tech")
public class TechBoundary {

  @Inject
  @SdwhDataSource
  private EntityManager entityManager;

  @Resource(lookup = "java:global/jdbc/ExampleDataSource")
  private DataSource dataSource;

  @GET
  public String getMetaData() throws SQLException {
    final DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
    final ResultSet schemas = metaData.getSchemas();
    StringBuffer response = new StringBuffer();
    metaData(new String[] { "TABLE" }, null, response);
    metaData(new String[] { "VIEW" }, null, response);
    return response.toString();
  }

  private void metaData(final String[] types, final String pattern, StringBuffer response) throws SQLException {
    final DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
    final ResultSet tables = metaData.getTables(null, null, pattern, types);
    while (tables.next()) {

      add(response,"==========================================");
      final String name = tables.getString("TABLE_NAME");
      add(response,name);
      ResultSet PK = metaData.getPrimaryKeys(null, null, name);
      keys(PK, response);
      ResultSet FK = metaData.getImportedKeys(null, null, name);
      foreignKeys(FK, response);
      ResultSet columns = metaData.getColumns(null, null, name, null);
      columns(columns, response);
      //Get Foreign Keys
    }
  }

  private void add(final StringBuffer res, final String toAdd) {
    res.append(toAdd).append("\n");
  }

  private void foreignKeys(ResultSet FK, StringBuffer response) throws SQLException {
    add(response, "------------FOREIGN KEYS-------------");
    while(FK.next())
    {
      add(response, FK.getString("PKTABLE_NAME") + "---" + FK.getString("PKCOLUMN_NAME") + "===" + FK.getString("FKTABLE_NAME") + "---" + FK.getString("FKCOLUMN_NAME"));
    }
  }

  private void keys(ResultSet PK, StringBuffer response) throws SQLException {
    add(response, "------------PRIMARY KEYS-------------");
    while (PK.next()) {
      add(response, "Name: " + PK.getString("COLUMN_NAME") + ", PrimaryKeyName: " + PK.getString("PK_NAME"));
    }
  }

  private void columns(ResultSet columns, StringBuffer response) throws SQLException {
    add(response, "------------COLUMNS-------------");
    while (columns.next()) {
      String columnName = columns.getString("COLUMN_NAME");
      //String datatype = columns.getString("DATA_TYPE");

      String datatype = columns.getString("TYPE_NAME");

      String columnsize = columns.getString("COLUMN_SIZE");
      String isNullable = columns.getString("IS_NULLABLE");
      // Printing results
      add(response, "Name: " + columnName + ", Type: " + datatype + ", ColumnSize: " + columnsize + ", Nullable: " + isNullable);
    }
  }

}
