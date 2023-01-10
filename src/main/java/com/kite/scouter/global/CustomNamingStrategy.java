package com.kite.scouter.global;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomNamingStrategy implements PhysicalNamingStrategy {

  @Override
  public Identifier toPhysicalCatalogName(final Identifier name,
                                          final JdbcEnvironment jdbcEnvironment) {
    return name;
  }

  @Override
  public Identifier toPhysicalSchemaName(final Identifier name,
                                         final JdbcEnvironment jdbcEnvironment) {
    return name;
  }

  @Override
  public Identifier toPhysicalTableName(final Identifier name,
                                        final JdbcEnvironment jdbcEnvironment) {
    return jdbcEnvironment.getIdentifierHelper().toIdentifier(join(splitAndReplace(name.getText())), true);
  }

  @Override
  public Identifier toPhysicalSequenceName(final Identifier name,
                                           final JdbcEnvironment jdbcEnvironment) {
    return jdbcEnvironment.getIdentifierHelper().toIdentifier(join(splitAndReplace(name.getText())), name.isQuoted());
  }

  @Override
  public Identifier toPhysicalColumnName(final Identifier name,
                                         final JdbcEnvironment jdbcEnvironment) {
    return jdbcEnvironment.getIdentifierHelper().toIdentifier(join( splitAndReplace(name.getText())), name.isQuoted());
  }

  private LinkedList<String> splitAndReplace(final String name) {

    LinkedList<String> result = new LinkedList<>();

    for (String part : StringUtils.splitByCharacterTypeCamelCase(name)) {
      if (StringUtils.isBlank(part)) {
        continue;
      }
      result.add(part.toUpperCase(Locale.ROOT));
    }
    return result;
  }

  private String join(final List<String> parts) {

    boolean firstPass = true;
    String separator = "";
    String passSeparator = "_";
    StringBuilder joined = new StringBuilder();

    for (String part : parts) {
      if (!part.equals(passSeparator)) {
        joined.append(separator).append(part);
      }

      if (firstPass) {
        firstPass = false;
        separator = passSeparator;
      }
    }
    return joined.toString();
  }

}
