package com.seeq.jdbctester;

import java.sql.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class JdbcTester {

    public static void main(String... args) throws ClassNotFoundException, SQLException {
        Option option;
        Options optionSet = new Options();
        option = new Option(null, "url", true, "The full url of the database");
        option.setRequired(true);
        optionSet.addOption(option);

        option = new Option("u", "user", true, "The user to be used for connecting");
        option.setRequired(false);
        optionSet.addOption(option);

        option = new Option("p", "password", true, "The password to be used for connecting");
        option.setRequired(false);
        optionSet.addOption(option);

        option = new Option("d", "driver", true, "The jdbc driver to use");
        option.setRequired(false);
        optionSet.addOption(option);

        option = new Option(null, "sql", true, "The sql to get current timestamp " +
                "(e.g. SELECT CURRENT_TIMESTAMP FROM DUAL)");
        option.setRequired(true);
        optionSet.addOption(option);

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;

        try {
            commandLine = parser.parse(optionSet, args, true);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("TestJdbc", optionSet);
            throw new IllegalArgumentException(e.toString());
        }


        String driver = commandLine.getOptionValue("driver");
        String user = commandLine.getOptionValue("user");
        String password = commandLine.getOptionValue("password");
        String url = commandLine.getOptionValue("url");
        String sql = commandLine.getOptionValue("sql");

        System.out.println("Loading driver: " + driver);
        Class.forName(driver);

        Timestamp result = null;
        System.out.println("Connecting to: " + url);
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        result = rs.getTimestamp(1);
                    }
                }
            }
        }

        if (result != null) {
            System.out.println("Successfully connected and queried timestamp: " + result);
        } else {
            System.out.println("Cannot query timestamp.");
        }
    }
}