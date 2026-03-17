package com.packd.server_api.utils;

public class Constants {
    public class Schema {
        public static final String SCHEMA_NAME = "packd";
    }

    public class DestinationConstants {
        public static final int MAX_DESTINATION_PER_TRIP = 10;
        public static final int MIN_DESTINATION_PER_TRIP = 1;
    }

    public class BudgetConstants {
        public static final int MIN_TRIP_BUDGET_AMOUNT = 0;
        public static final int MAX_TRIP_BUDGET_AMOUNT = 1000000;
    }
}
